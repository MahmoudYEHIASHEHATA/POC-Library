package com.cassbana.antifraud.workers.simInfo

import android.content.Context
import androidx.work.*
import com.cassbana.antifraud.workers.simInfo.data.remote.RSSIMInfoModel
import com.cassbana.antifraud.BuildConfig
import com.cassbana.antifraud.MySdkKoinComponent
import com.cassbana.antifraud.data.RSFraudRepository
import com.cassbana.antifraud.data.model.SyncListData
import com.cassbana.antifraud.workers.constants.RSWorkerConstants
import com.cassbana.antifraud.workers.simInfo.data.local.RSSIMInfoDao
import com.cassbana.antifraud.workers.simInfo.data.mapper.RSSIMInformationMapper
import com.cassbana.antifraud.workers.utils.RSCalculatingDifferenceInChunks
import com.cassbana.antifraud.workers.utils.SimSubscriptionManager
import com.cassbana.antifraud.workers.utils.RSSyncingStrategy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.component.inject
import timber.log.Timber
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class RSSIMInfoCollectWorker(
    val context: Context, workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), MySdkKoinComponent {

    companion object {
        private const val TAG = "SIMInfoCollectWorker"
        private const val PERIOD = 24.toLong()
        private val PERIOD_UNIT = TimeUnit.HOURS
        private const val READING_PAGE_LIMIT = 1
        private const val WRITING_PAGE_LIMIT = 10
        private const val RETRY_PERIOD = 2.toLong()
        private val RETRY_PERIOD_UNIT = TimeUnit.MINUTES

        fun start(context: Context, initialDelay: Long) {
            if (BuildConfig.DEBUG)
                startNow(context, initialDelay)
            else
                startPeriodically(context, initialDelay)
        }

        private fun startPeriodically(context: Context, initialDelay: Long) {
            val simInfoCollectWorker = PeriodicWorkRequest
                .Builder(RSSIMInfoCollectWorker::class.java, PERIOD, PERIOD_UNIT)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    RETRY_PERIOD,
                    RETRY_PERIOD_UNIT
                )
                .setConstraints(RSWorkerConstants.NETWORK_CONSTRAINT)
                .setInitialDelay(initialDelay, TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                simInfoCollectWorker
            )
        }

        private fun startNow(
            context: Context,
            initialDelay: Long
        ) {
            val simInfoCollectWorker = OneTimeWorkRequest.Builder(RSSIMInfoCollectWorker::class.java)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    RETRY_PERIOD,
                    RETRY_PERIOD_UNIT
                )
                .setConstraints(RSWorkerConstants.NETWORK_CONSTRAINT)
                .setInitialDelay(initialDelay, TimeUnit.SECONDS)
                .build()
            WorkManager.getInstance(context).enqueueUniqueWork(
                TAG,
                ExistingWorkPolicy.REPLACE,
                simInfoCollectWorker
            )
        }
    }

    private val fraudRepository: RSFraudRepository by inject()
    private val simInfoDao: RSSIMInfoDao by inject()
    private val simInformationMapper: RSSIMInformationMapper by inject()
    private val simSubscriptionManager: SimSubscriptionManager by lazy {
        SimSubscriptionManager()
    }

    private val syncingStrategy by lazy {
        object : RSSyncingStrategy<RSSIMInfoModel>(
            READING_PAGE_LIMIT,
            WRITING_PAGE_LIMIT,
            RSCalculatingDifferenceInChunks(TAG) { it.id },
            TAG
        ) {
            override suspend fun getDevicePage(pageLimit: Int, offset: Int): List<RSSIMInfoModel> {
                return if (offset == 0)
                    getSIMInformation()
                else listOf()
            }

            override suspend fun getDatabasePage(pageLimit: Int, offset: Int): List<RSSIMInfoModel> {

                return if (offset == 0)
                    simInfoDao.getSIMsInformation()
                else listOf()
            }

            override suspend fun syncObjectWithTheServer(
                insertedObject: List<RSSIMInfoModel>,
                updatedObject: List<RSSIMInfoModel>,
                deletedObject: List<RSSIMInfoModel>
            ) {

                fraudRepository.submitSIMInformation(
                    SyncListData(
                        insertedObject,
                        listOf(),
                        listOf(),
                    )
                )
            }

            override suspend fun insertIntoDB(objects: List<RSSIMInfoModel>) {
                simInfoDao.insertSIMsInformation(objects)
            }

            override suspend fun deleteFromDB(objects: List<RSSIMInfoModel>) {

                // No need to delete as SIM we just need mark inserted one and we don't calculate delete
            }

            override suspend fun updateIntoDB(objects: List<RSSIMInfoModel>) {

                // No need to update as SIM never changes and we don't calculate updates
            }
        }
    }


    override suspend fun doWork(): Result {
        try {

            syncingStrategy.doWork()
        } catch (e: Exception) {
            Timber.tag(TAG).d("retry: ${e.message}")
            return Result.failure()

        }
        return Result.success()
    }

    private fun getSIMInformation(): List<RSSIMInfoModel> {
        return simInformationMapper.map(simSubscriptionManager.getSubscriptionId(context))
    }
}
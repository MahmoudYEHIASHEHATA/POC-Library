package com.cassbana.antifraud.workers.utils

import com.cassbana.antifraud.workers.constants.RSWorkerConstants
import kotlinx.coroutines.delay
import timber.log.Timber

/**
 * A strategy that
 * - reading pages from device
 * - reading pages from stored database
 * - calculate last changes
 * - sync the changes with the server into chunks
 * - store changes into database
 */
abstract class RSSyncingStrategy<T>(
    private val readingPageLimit: Int,
    private val writingPageLimit: Int,
    private val calculatingDifferenceContract: RSCalculatingDifferenceContract<T>,
    private val tag: String = RSWorkerConstants.SYNCING_TAG
) {

    private var offset = 0

    suspend fun doWork() {
        calculatingDifference()
        syncingChunks()
    }

    private suspend fun calculatingDifference() {
        var isEmpty = false
        while (!isEmpty) {
            Timber.tag(tag).d("Page $offset")

            val devicePage : List<T> = getDevicePage(readingPageLimit, offset)
            Timber.tag(tag).d("Device Page $offset: (${devicePage.size} items) / $devicePage")

            val dbPage = getDatabasePage(readingPageLimit, offset)
            Timber.tag(tag).d("DB Page $offset: (${dbPage.size} items) / $dbPage")

            calculatingDifferenceContract.calculateChunk(
                dbPage,
                devicePage
            )

            isEmpty = devicePage.size < readingPageLimit && dbPage.size <  readingPageLimit
            offset++

            Timber.tag(tag).d("-------------------------------------------------")
        }
    }

    private suspend fun syncingChunks() {
        val differenceList = calculatingDifferenceContract.getFinalResult()

        val insertedChunks = differenceList.insertedList.chunked(writingPageLimit)
        val updatesChunks = differenceList.updatedList.chunked(writingPageLimit)
        val deletedChunks = differenceList.deletedList.chunked(writingPageLimit)


        for (i in 0 until maxOf(insertedChunks.size, deletedChunks.size, updatesChunks.size)) {
            val insertedObjects = insertedChunks.getOrNull(i) ?: listOf()

            val updatedObjects = updatesChunks.getOrNull(i) ?: listOf()

            val deletedObjects = deletedChunks.getOrNull(i) ?: listOf()

            syncObjectWithTheServer(insertedObjects, updatedObjects, deletedObjects)
            Timber.tag(tag).d("Sync with server chunk $i")
            Timber.tag(tag).d("inserted: (${insertedObjects.size}  items) / $insertedObjects")
            Timber.tag(tag).d("updated: (${updatedObjects.size} items) / $updatedObjects")
            Timber.tag(tag).d("deleted: (${deletedObjects.size} items) / $deletedObjects")

            insertIntoDB(insertedObjects)
            Timber.tag(tag).d("insert in DB $insertedObjects")

            updateIntoDB(updatedObjects)
            Timber.tag(tag).d("update from DB $updatedObjects")

            deleteFromDB(deletedObjects)
            Timber.tag(tag).d("delete from DB $deletedObjects")

            Timber.tag(tag).d("----------------------------------------------------------------")

            delay(5000)
        }
    }

    abstract suspend fun getDevicePage(pageLimit: Int, offset: Int) : List<T>

    abstract suspend fun getDatabasePage(pageLimit: Int, offset: Int) : List<T>

    abstract suspend fun syncObjectWithTheServer(
        insertedObject: List<T>,
        updatedObject: List<T>,
        deletedObject: List<T>
    )

    abstract suspend fun insertIntoDB(objects: List<T>)

    abstract suspend fun deleteFromDB(objects: List<T>)

    abstract suspend fun updateIntoDB(objects: List<T>)
}
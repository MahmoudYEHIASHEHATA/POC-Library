package com.cassbana.antifraud

import android.content.Context
import android.util.Log
import com.cassbana.antifraud.workers.di.networkModuleRS
import com.cassbana.antifraud.workers.di.workersModuleRS
import com.cassbana.antifraud.workers.simInfo.RSSIMInfoCollectWorker
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent

object RiskDataCollection {


    internal val koinApplication = KoinApplication.init()

    fun init() {
        koinApplication.apply {
            modules(listOf(workersModuleRS, networkModuleRS))
        }
    }

    fun scheduleSIMInfoCollectWorkManager(context: Context) =
        RSSIMInfoCollectWorker.start(context, 1)

    fun logInstallationSuccess() =
        Log.d("install anti fraud :", "installation data-collection success")


}

internal interface MySdkKoinComponent : KoinComponent {
    override fun getKoin(): Koin {
        return RiskDataCollection.koinApplication.koin
    }

}
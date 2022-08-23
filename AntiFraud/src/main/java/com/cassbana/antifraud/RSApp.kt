package com.cassbana.antifraud

import android.app.Application
import android.content.Context
import com.cassbana.antifraud.workers.di.workersModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class RSApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        RSDomain.integrateWith(instance)
        startDI()
    }
    private fun startDI() {
        startKoin {
            androidContext(this@RSApp)
            modules(
                listOf(
                    workersModule
                )
            )
        }
    }
    companion object {
        lateinit var instance: RSApp
            private set
        lateinit var context: Context
            private set
    }
}

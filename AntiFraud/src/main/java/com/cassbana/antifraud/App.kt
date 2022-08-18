package com.cassbana.antifraud

import android.app.Application
import android.content.Context
import com.cassbana.antifraud.workers.di.workersModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Domain.integrateWith(instance)
        startDI()
    }
    private fun startDI() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    workersModule
                )
            )
        }
    }
    companion object {
        lateinit var instance: App
            private set
        lateinit var context: Context
            private set
    }
}

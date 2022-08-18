package com.cassbana.antifraud.database

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            DatabaseConstants.databaseName
        )
            .build()
    }
    single { provideDatabase(androidApplication()) }
}

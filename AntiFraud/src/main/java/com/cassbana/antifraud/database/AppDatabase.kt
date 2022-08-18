package com.cassbana.antifraud.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bluecrunch.microfinance.workers.simInfo.data.remote.SIMInfoModel
import com.cassbana.antifraud.workers.simInfo.data.local.SIMInfoDao

@Database(
    entities = [
        SIMInfoModel::class,
    ],
    autoMigrations = [],
    version = DatabaseConstants.databaseVersion,
    // Enable export database schema to allow $[androidx.room.AutoMigration] in the next database versions
    exportSchema = true
)
@TypeConverters(ContactPhoneConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun simInformationDAO(): SIMInfoDao
}
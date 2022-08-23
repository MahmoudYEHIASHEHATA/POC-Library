package com.cassbana.antifraud.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cassbana.antifraud.workers.simInfo.data.remote.RSSIMInfoModel
import com.cassbana.antifraud.workers.simInfo.data.local.RSSIMInfoDao

@Database(
    entities = [
        RSSIMInfoModel::class,
    ],
    autoMigrations = [],
    version = RSDatabaseConstants.databaseVersion,
    // Enable export database schema to allow $[androidx.room.AutoMigration] in the next database versions
    exportSchema = true
)
@TypeConverters(RSContactPhoneConverter::class)
abstract class RSAppDatabase : RoomDatabase() {

    abstract fun simInformationDAO(): RSSIMInfoDao
}
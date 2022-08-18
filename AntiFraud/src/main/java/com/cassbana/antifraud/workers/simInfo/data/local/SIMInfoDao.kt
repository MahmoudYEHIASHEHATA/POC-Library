package com.cassbana.antifraud.workers.simInfo.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bluecrunch.microfinance.workers.simInfo.data.remote.SIMInfoModel

@Dao
interface SIMInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSIMsInformation(list: List<SIMInfoModel>)

    @Query("Select * FROM sim_information")
    suspend fun getSIMsInformation(): List<SIMInfoModel>
}

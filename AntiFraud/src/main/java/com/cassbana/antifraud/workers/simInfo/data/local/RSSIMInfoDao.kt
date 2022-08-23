package com.cassbana.antifraud.workers.simInfo.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cassbana.antifraud.workers.simInfo.data.remote.RSSIMInfoModel

@Dao
interface RSSIMInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSIMsInformation(list: List<RSSIMInfoModel>)

    @Query("Select * FROM sim_information")
    fun getSIMsInformation(): List<RSSIMInfoModel>
}

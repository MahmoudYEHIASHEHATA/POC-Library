package com.bluecrunch.microfinance.workers.simInfo.data.remote

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "sim_information")
@Keep
data class SIMInfoModel (
    @PrimaryKey
    val id : Int,
    @SerializedName("iccId")
    val iccId : String,
    @SerializedName("simSlotIndex")
    val simSlotIndex : Int,
    @SerializedName("displayName")
    val displayName : String,
    @SerializedName("carrierName")
    val carrierName : String,
)

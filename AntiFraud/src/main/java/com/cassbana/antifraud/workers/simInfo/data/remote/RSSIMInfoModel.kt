package com.cassbana.antifraud.workers.simInfo.data.remote

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "sim_information")
@Keep
data class RSSIMInfoModel (
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

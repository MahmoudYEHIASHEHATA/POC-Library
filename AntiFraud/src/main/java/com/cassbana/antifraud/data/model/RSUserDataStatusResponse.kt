package com.cassbana.antifraud.data.model
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class RSUserDataStatusResponse {
    @SerializedName("contacts")
    var isUserContactsExist : Boolean = false
    @SerializedName("locationHistory")
    var isUserLocationExist : Boolean = false
    @SerializedName("installedApps")
    var isUserInstalledAppsExist : Boolean = false
    @SerializedName("bluetoothHistory")
    var isBluetoothHistoryExist : Boolean = false
}
package com.cassbana.antifraud.data.model

import androidx.annotation.Keep
import com.cassbana.antifraud.utils.Utils

@Keep
data class SyncObjectRequest<T> (
    val deviceId: String = Utils.getDeviceID(),
    val data: T
)
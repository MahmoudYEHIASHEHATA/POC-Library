package com.cassbana.antifraud.data.model

import androidx.annotation.Keep
import com.cassbana.antifraud.utils.RSUtils

@Keep
data class RSSyncObjectRequest<T> (
    val deviceId: String = RSUtils.getDeviceID(),
    val data: T
)
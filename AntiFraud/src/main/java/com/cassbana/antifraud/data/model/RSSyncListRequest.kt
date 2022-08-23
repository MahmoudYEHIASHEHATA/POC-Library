package com.cassbana.antifraud.data.model

import androidx.annotation.Keep
import com.cassbana.antifraud.utils.RSUtils

@Keep
abstract class BaseSyncListRequest {
    abstract val deviceId: String
}

@Keep
data class SyncListRequest<ID, T>(
    override val deviceId: String = RSUtils.getDeviceID(),
    val data: SyncListData<ID, T>
) : BaseSyncListRequest()

@Keep
data class SyncListRequestWithTotal<ID, T>(
    override val deviceId: String = RSUtils.getDeviceID(),
    val data: SyncListDataWithTotal<ID, T>
) : BaseSyncListRequest()
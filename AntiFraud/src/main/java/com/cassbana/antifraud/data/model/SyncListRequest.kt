package com.cassbana.antifraud.data.model

import androidx.annotation.Keep
import com.cassbana.antifraud.utils.Utils

@Keep
abstract class BaseSyncListRequest {
    abstract val deviceId: String
}

@Keep
data class SyncListRequest<ID, T>(
    override val deviceId: String = Utils.getDeviceID(),
    val data: SyncListData<ID, T>
) : BaseSyncListRequest()

@Keep
data class SyncListRequestWithTotal<ID, T>(
    override val deviceId: String = Utils.getDeviceID(),
    val data: SyncListDataWithTotal<ID, T>
) : BaseSyncListRequest()
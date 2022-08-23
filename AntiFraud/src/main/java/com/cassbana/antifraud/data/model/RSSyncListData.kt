package com.cassbana.antifraud.data.model

import androidx.annotation.Keep

@Keep
abstract class BaseSyncListData<ID, T> {
    abstract val inserted: List<T>
    abstract val updated: List<T>
    abstract val deleted: List<ID>
}

@Keep
data class SyncListData<ID, T>(
    override val inserted: List<T>,
    override val updated: List<T>,
    override val deleted: List<ID>,
) : BaseSyncListData<ID, T>()

@Keep
data class SyncListDataWithTotal<ID, T>(
    override val inserted: List<T>,
    override val updated: List<T>,
    override val deleted: List<ID>,
    val total: Int
) : BaseSyncListData<ID, T>()
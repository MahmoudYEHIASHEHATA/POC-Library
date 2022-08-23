package com.cassbana.antifraud.workers.utils

import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first

interface RSUniqueIDGeneratorWrapper {
    fun getUUID(): String

    suspend fun getFingerPrinterId(): String
}

@ExperimentalCoroutinesApi
class UniqueIDGeneratorWrapperImpl(
    private val context: Context
): RSUniqueIDGeneratorWrapper {
    override fun getUUID(): String = RSUniqueIDGenerator.getUUID(context)

    override suspend fun getFingerPrinterId(): String =
        RSUniqueIDGenerator.getFingerPrinterIdFlow(context).first()
}

@ExperimentalCoroutinesApi
class RSUniqueIDGeneratorUseCase(
    private val uniqueIDGeneratorWrapper: RSUniqueIDGeneratorWrapper
) {
    fun getUUID() = uniqueIDGeneratorWrapper.getUUID()

    suspend fun getFingerPrinterId() = uniqueIDGeneratorWrapper.getFingerPrinterId()
}

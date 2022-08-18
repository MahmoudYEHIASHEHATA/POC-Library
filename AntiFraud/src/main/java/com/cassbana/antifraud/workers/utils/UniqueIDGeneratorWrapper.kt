package com.bluecrunch.microfinance.workers.utils

import android.content.Context
import com.cassbana.antifraud.workers.utils.UniqueIDGenerator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first

interface UniqueIDGeneratorWrapper {
    fun getUUID(): String

    suspend fun getFingerPrinterId(): String
}

@ExperimentalCoroutinesApi
class UniqueIDGeneratorWrapperImpl(
    private val context: Context
): UniqueIDGeneratorWrapper {
    override fun getUUID(): String = UniqueIDGenerator.getUUID(context)

    override suspend fun getFingerPrinterId(): String =
        UniqueIDGenerator.getFingerPrinterIdFlow(context).first()
}

@ExperimentalCoroutinesApi
class UniqueIDGeneratorUseCase(
    private val uniqueIDGeneratorWrapper: UniqueIDGeneratorWrapper
) {
    fun getUUID() = uniqueIDGeneratorWrapper.getUUID()

    suspend fun getFingerPrinterId() = uniqueIDGeneratorWrapper.getFingerPrinterId()
}

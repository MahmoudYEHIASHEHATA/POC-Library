package com.cassbana.antifraud.data

import com.bluecrunch.microfinance.workers.simInfo.data.remote.SIMInfoModel
import com.bluecrunch.microfinance.workers.utils.createSyncListRequest
import com.cassbana.antifraud.data.model.SyncListData

class FraudRepository(
    private val fraudRemoteDataSource: FraudRemoteDataSource,
    private val fraudLocalDataSource: FraudLocalDataSource
) {

    suspend fun submitSIMInformation(simInfoRequest: SyncListData<String, SIMInfoModel>): Any {
        return fraudRemoteDataSource.submitSIMInformation(
            createSyncListRequest(simInfoRequest)
        )
    }
}

package com.cassbana.antifraud.data

import com.cassbana.antifraud.workers.simInfo.data.remote.RSSIMInfoModel
import com.bluecrunch.microfinance.workers.utils.createSyncListRequest
import com.cassbana.antifraud.data.model.SyncListData

class RSFraudRepository(
    private val fraudRemoteDataSource: RSFraudRemoteDataSource,
    private val fraudLocalDataSource: RSFraudLocalDataSource
) {

    suspend fun submitSIMInformation(simInfoRequest: SyncListData<String, RSSIMInfoModel>): Any {
        return fraudRemoteDataSource.submitSIMInformation(
            createSyncListRequest(simInfoRequest)
        )
    }
}

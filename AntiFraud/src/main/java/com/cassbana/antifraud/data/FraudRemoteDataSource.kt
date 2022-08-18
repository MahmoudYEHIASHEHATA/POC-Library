package com.cassbana.antifraud.data

import com.bluecrunch.microfinance.workers.simInfo.data.remote.SIMInfoModel
import com.cassbana.antifraud.data.model.SyncListRequest

interface FraudRemoteDataSource {

    suspend fun submitSIMInformation(simInfoRequest : SyncListRequest<String, SIMInfoModel>) : Any

}

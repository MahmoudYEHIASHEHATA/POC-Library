package com.cassbana.antifraud.data

import com.cassbana.antifraud.workers.simInfo.data.remote.RSSIMInfoModel
import com.cassbana.antifraud.data.model.SyncListRequest

interface RSFraudRemoteDataSource {

    suspend fun submitSIMInformation(simInfoRequest : SyncListRequest<String, RSSIMInfoModel>) : Any

}

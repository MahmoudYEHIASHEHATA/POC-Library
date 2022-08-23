package com.cassbana.antifraud.data

import com.cassbana.antifraud.data.model.SyncListRequest
import com.cassbana.antifraud.workers.simInfo.data.remote.RSSIMInfoModel
import retrofit2.http.Body
import retrofit2.http.POST


interface RSFraudApis {



    @POST("sim-cards")
    suspend fun submitSIMInformation(
        @Body simInformationList: SyncListRequest<String, RSSIMInfoModel>
    ): Any


}
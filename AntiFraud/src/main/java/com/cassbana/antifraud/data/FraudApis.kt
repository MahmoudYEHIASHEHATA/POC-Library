package com.cassbana.antifraud.data

import com.cassbana.antifraud.data.model.SyncListRequest
import com.bluecrunch.microfinance.workers.simInfo.data.remote.SIMInfoModel
import retrofit2.http.Body
import retrofit2.http.POST


interface FraudApis {



    @POST("sim-cards")
    suspend fun submitSIMInformation(
        @Body simInformationList: SyncListRequest<String, SIMInfoModel>
    ): Any


}
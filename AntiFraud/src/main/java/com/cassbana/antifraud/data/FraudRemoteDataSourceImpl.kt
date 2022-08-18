package com.cassbana.antifraud.data


import com.bluecrunch.microfinance.workers.simInfo.data.remote.SIMInfoModel
import com.cassbana.antifraud.ICoroutineScopeDispatchers
import com.cassbana.antifraud.data.model.SyncListRequest
import kotlinx.coroutines.withContext

class FraudRemoteDataSourceImpl(
    private val fraudApis: FraudApis,
    private val coroutineScopeDispatchers: ICoroutineScopeDispatchers
) : FraudRemoteDataSource {

    override suspend fun submitSIMInformation(simInfoRequest: SyncListRequest<String, SIMInfoModel>): Any {
        return withContext(coroutineScopeDispatchers.IO) {
            try {

                fraudApis.submitSIMInformation(simInfoRequest)
            } catch (e: Exception) {
                throw e
            }
        }
    }


}

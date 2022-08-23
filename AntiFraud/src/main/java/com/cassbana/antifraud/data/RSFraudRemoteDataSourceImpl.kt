package com.cassbana.antifraud.data


import com.cassbana.antifraud.workers.simInfo.data.remote.RSSIMInfoModel
import com.cassbana.antifraud.ICoroutineScopeDispatchers
import com.cassbana.antifraud.data.model.SyncListRequest
import kotlinx.coroutines.withContext

class RSFraudRemoteDataSourceImpl(
    private val fraudApis: RSFraudApis,
    private val coroutineScopeDispatchers: ICoroutineScopeDispatchers
) : RSFraudRemoteDataSource {

    override suspend fun submitSIMInformation(simInfoRequest: SyncListRequest<String, RSSIMInfoModel>): Any {
        return withContext(coroutineScopeDispatchers.IO) {
            try {

                fraudApis.submitSIMInformation(simInfoRequest)
            } catch (e: Exception) {
                throw e
            }
        }
    }


}

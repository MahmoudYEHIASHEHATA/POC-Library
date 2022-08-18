package com.cassbana.antifraud.workers.simInfo.data.mapper

import android.os.Build
import android.telephony.SubscriptionInfo
import com.bluecrunch.microfinance.workers.simInfo.data.remote.SIMInfoModel
import com.cassbana.antifraud.mapper.IMapper

class SIMInformationMapper : IMapper<List<SubscriptionInfo>, List<SIMInfoModel>> {

    override fun map(inputFormat: List<SubscriptionInfo>): List<SIMInfoModel> {
        return inputFormat.map {
                SIMInfoModel(
                    id =   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)  it.cardId else -1,
                    iccId = it.iccId,
                    simSlotIndex = it.simSlotIndex,
                    displayName = it.displayName.toString(),
                    carrierName = it.carrierName.toString()
                )

        }
    }
}
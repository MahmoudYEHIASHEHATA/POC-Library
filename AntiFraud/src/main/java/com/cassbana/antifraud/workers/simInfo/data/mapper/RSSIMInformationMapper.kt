package com.cassbana.antifraud.workers.simInfo.data.mapper

import android.os.Build
import android.telephony.SubscriptionInfo
import com.cassbana.antifraud.workers.simInfo.data.remote.RSSIMInfoModel
import com.cassbana.antifraud.mapper.RSIMapper

class RSSIMInformationMapper : RSIMapper<List<SubscriptionInfo>, List<RSSIMInfoModel>> {

    override fun map(inputFormat: List<SubscriptionInfo>): List<RSSIMInfoModel> {
        return inputFormat.map {
                RSSIMInfoModel(
                    id =   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)  it.cardId else -1,
                    iccId = it.iccId,
                    simSlotIndex = it.simSlotIndex,
                    displayName = it.displayName.toString(),
                    carrierName = it.carrierName.toString()
                )

        }
    }
}
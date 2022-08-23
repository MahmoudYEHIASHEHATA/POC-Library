package com.cassbana.antifraud.workers.utils

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager

class SimSubscriptionManager {

    @SuppressLint("MissingPermission")
    fun getSubscriptionId(context: Context): List<SubscriptionInfo> {

        val subscriptionManager = context.getSystemService(SubscriptionManager::class.java)

        return subscriptionManager.activeSubscriptionInfoList
    }
}
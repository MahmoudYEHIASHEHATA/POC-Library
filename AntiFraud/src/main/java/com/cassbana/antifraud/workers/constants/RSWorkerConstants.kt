package com.cassbana.antifraud.workers.constants

import androidx.work.Constraints
import androidx.work.NetworkType

object RSWorkerConstants {
    val NETWORK_CONSTRAINT =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
    const val  SYNCING_TAG = "syncing_tag"
    const val MAX_WIFI_SIGNAL_LEVEL = 5
}

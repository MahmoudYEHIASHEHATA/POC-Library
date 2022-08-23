package com.cassbana.antifraud

import android.content.Context
import android.util.Log
import com.cassbana.antifraud.workers.simInfo.RSSIMInfoCollectWorker

fun logInstallationSuccess() {
    Log.d("install anti fraud :" , "installation data-collection success")
}

fun scheduleSIMInfoCollectWorkManager(context: Context) =
//    context.runWithPermissions(Manifest.permission.READ_PHONE_STATE) {
        RSSIMInfoCollectWorker.start(context, 1)
  //  }
package com.cassbana.antifraud

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.cassbana.antifraud.workers.simInfo.SIMInfoCollectWorker

fun logInstallationSuccess() {
    Log.d("install anti fraud :" , "installation data-collection success")
}

fun scheduleSIMInfoCollectWorkManager(context: Context) =
//    context.runWithPermissions(Manifest.permission.READ_PHONE_STATE) {
        SIMInfoCollectWorker.start(context, 1)
  //  }
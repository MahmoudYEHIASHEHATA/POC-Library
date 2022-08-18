package com.cassbana.antifraud

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.cassbana.antifraud.workers.simInfo.SIMInfoCollectWorker
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions

fun logInstallationSuccess() {
    Log.d("install anti fraud :" , "installation data-collection success")
}

private fun scheduleSIMInfoCollectWorkManager(context: Context) =
    context.runWithPermissions(Manifest.permission.READ_PHONE_STATE) {
        SIMInfoCollectWorker.start(context, 1)
    }
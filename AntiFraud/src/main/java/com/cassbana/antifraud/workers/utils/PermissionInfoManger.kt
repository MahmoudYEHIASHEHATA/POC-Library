package com.bluecrunch.microfinance.workers.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

class PermissionInfoManger {
    @Throws(PackageManager.NameNotFoundException::class)
    fun getPackageInfo(context: Context): PackageInfo {
        return context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_PERMISSIONS
        )
    }
}
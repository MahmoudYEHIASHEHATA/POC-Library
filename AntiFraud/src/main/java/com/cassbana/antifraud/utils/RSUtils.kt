package com.cassbana.antifraud.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import com.cassbana.antifraud.RSDomain

object RSUtils {

    fun isConnectingToInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        if (activeNetwork != null) {
            val nc: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(activeNetwork)
            return (nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
        }
        return false
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    fun getDeviceID(): String {
        return try {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    val telephonyManager =
                        RSDomain.application.getSystemService(AppCompatActivity.TELEPHONY_SERVICE) as TelephonyManager
                    telephonyManager.imei
                } catch (e: Exception) {
                    Settings.Secure.getString(
                        RSDomain.application.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                }
            } else {
                Settings.Secure.getString(
                    RSDomain.application.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            }
        } catch (e: Exception) {
            ""
        }
    }
}
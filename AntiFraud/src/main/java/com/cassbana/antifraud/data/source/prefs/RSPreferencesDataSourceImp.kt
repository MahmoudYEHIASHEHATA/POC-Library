package com.cassbana.antifraud.data.source.prefs

import com.cassbana.antifraud.RSConstants
import com.cassbana.antifraud.data.model.RSUser
import com.google.gson.Gson

class RSPreferencesDataSourceImp(private val prefs: Preferences) : RSPreferencesDataSource {

    override var rsUser: RSUser?
        get() = Gson().fromJson(prefs.load(RSConstants.USER, ""), RSUser::class.java)
        set(value) = prefs.save(RSConstants.USER, Gson().toJson(value))

    override var rsFcmToken: String
        get() = prefs.load(RSConstants.FCM_TOKEN, "")
        set(value) = prefs.save(RSConstants.FCM_TOKEN, value)

    override var rsLocationPermissionGranted: Boolean
        get() = prefs.load(RSConstants.HAS_GRANTED_LOCATION_PERMISSION, false)
        set(value) = prefs.save(RSConstants.HAS_GRANTED_LOCATION_PERMISSION, value)

    override var rsOtherPermissionsGranted: Boolean
        get() = prefs.load(RSConstants.HAS_GRANTED_OTHER_PERMISSIONS, false)
        set(value) = prefs.save(RSConstants.HAS_GRANTED_OTHER_PERMISSIONS, value)

    override var rsIsInsuranceDialogShown: Boolean
        get() = prefs.load(RSConstants.INSURANCE_DIALOG_SHOWN, false)
        set(value) = prefs.save(RSConstants.INSURANCE_DIALOG_SHOWN, value)
}

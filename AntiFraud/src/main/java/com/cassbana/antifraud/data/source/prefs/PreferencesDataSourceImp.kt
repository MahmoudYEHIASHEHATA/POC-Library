package com.cassbana.antifraud.data.source.prefs

import com.cassbana.antifraud.Constants
import com.cassbana.antifraud.data.model.User
import com.google.gson.Gson

class PreferencesDataSourceImp(private val prefs: Preferences) : PreferencesDataSource {

    override var user: User?
        get() = Gson().fromJson(prefs.load(Constants.USER, ""), User::class.java)
        set(value) = prefs.save(Constants.USER, Gson().toJson(value))

    override var fcmToken: String
        get() = prefs.load(Constants.FCM_TOKEN, "")
        set(value) = prefs.save(Constants.FCM_TOKEN, value)

    override var locationPermissionGranted: Boolean
        get() = prefs.load(Constants.HAS_GRANTED_LOCATION_PERMISSION, false)
        set(value) = prefs.save(Constants.HAS_GRANTED_LOCATION_PERMISSION, value)

    override var otherPermissionsGranted: Boolean
        get() = prefs.load(Constants.HAS_GRANTED_OTHER_PERMISSIONS, false)
        set(value) = prefs.save(Constants.HAS_GRANTED_OTHER_PERMISSIONS, value)

    override var isInsuranceDialogShown: Boolean
        get() = prefs.load(Constants.INSURANCE_DIALOG_SHOWN, false)
        set(value) = prefs.save(Constants.INSURANCE_DIALOG_SHOWN, value)
}

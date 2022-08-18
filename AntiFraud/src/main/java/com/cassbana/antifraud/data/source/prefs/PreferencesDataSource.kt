package com.cassbana.antifraud.data.source.prefs

import com.cassbana.antifraud.data.model.User


interface PreferencesDataSource {

    var user: User?

    var fcmToken: String

    var locationPermissionGranted: Boolean

    var otherPermissionsGranted: Boolean

    var isInsuranceDialogShown: Boolean
}

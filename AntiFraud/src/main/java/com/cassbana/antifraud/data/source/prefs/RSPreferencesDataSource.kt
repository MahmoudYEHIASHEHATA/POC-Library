package com.cassbana.antifraud.data.source.prefs

import com.cassbana.antifraud.data.model.RSUser


interface RSPreferencesDataSource {

    var rsUser: RSUser?

    var rsFcmToken: String

    var rsLocationPermissionGranted: Boolean

    var rsOtherPermissionsGranted: Boolean

    var rsIsInsuranceDialogShown: Boolean
}

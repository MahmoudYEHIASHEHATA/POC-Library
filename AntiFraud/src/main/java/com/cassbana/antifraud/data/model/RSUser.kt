package com.cassbana.antifraud.data.model

import androidx.annotation.Keep
import com.cassbana.antifraud.RSDomain
import com.cassbana.antifraud.R
import com.google.gson.annotations.SerializedName

@Keep
class RSUser {
    var image: String? = ""
    var id: String? = ""
    @SerializedName("phone")
    var phoneWithCode: String? = ""
    @SerializedName("phone_no_code")
    var phone: String? = ""
    @SerializedName("first_name")
    var firstName: String? = ""
    @SerializedName("last_name")
    var lastName: String? = ""
    @SerializedName("date_of_birth")
    var dateOfBirth: String? = ""
    var RSGender: RSGender? = null
    @SerializedName("average_income")
    var averageIncome: Double? = 0.0
    var education: String? = ""
    @SerializedName("national_id")
    var nationalID: String? = ""
    @SerializedName("national_id_photo")
    var nationalIDPhoto: String? = ""
    var type: RSUserType? = null
    var address: String? = ""
    var token: String? = ""
    @SerializedName("client_id")
    var clientID: String? = ""
    @SerializedName("merchant_id")
    var merchantID: String? = ""
    var RSCountry: RSCountry? = null
    @SerializedName("area_id")
    var areaId: Int? = 0
    @SerializedName("city_id")
    var cityId: Int? = 0
    @SerializedName("is_blocked")
    var isBlocked: Boolean = false
    @SerializedName("become_merchant")
    var RSBecomeMerchantStatus: RSBecomeMerchantStatus? = null
    @SerializedName("got_extra_loan")
    var rsextraLoanStatus: RSExtraLoanStatus? = null

    val isAgent: Boolean
        get() = type == RSUserType.AGENT

    val isMerchant: Boolean
        get() = type == RSUserType.MERCHANT

    val isClient: Boolean
        get() = type == RSUserType.CLIENT

    fun formatWelcomeMessage(): String {
        return RSDomain.application.getString(R.string.welcome) + " " + firstName
    }

    fun formatFullName(): String {
        return "$firstName $lastName"
    }

    fun showExtraLoan(): Boolean {
        return rsextraLoanStatus != null &&
                (rsextraLoanStatus == RSExtraLoanStatus.NORMAL_STATE ||
                        rsextraLoanStatus == RSExtraLoanStatus.REJECTED ||
                        rsextraLoanStatus == RSExtraLoanStatus.PENDING)

    }
}
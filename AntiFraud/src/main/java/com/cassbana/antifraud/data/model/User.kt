package com.cassbana.antifraud.data.model

import androidx.annotation.Keep
import com.cassbana.antifraud.Domain
import com.cassbana.antifraud.R
import com.google.gson.annotations.SerializedName

@Keep
class User {
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
    var gender: Gender? = null
    @SerializedName("average_income")
    var averageIncome: Double? = 0.0
    var education: String? = ""
    @SerializedName("national_id")
    var nationalID: String? = ""
    @SerializedName("national_id_photo")
    var nationalIDPhoto: String? = ""
    var type: UserType? = null
    var address: String? = ""
    var token: String? = ""
    @SerializedName("client_id")
    var clientID: String? = ""
    @SerializedName("merchant_id")
    var merchantID: String? = ""
    var country: Country? = null
    @SerializedName("area_id")
    var areaId: Int? = 0
    @SerializedName("city_id")
    var cityId: Int? = 0
    @SerializedName("is_blocked")
    var isBlocked: Boolean = false
    @SerializedName("become_merchant")
    var becomeMerchantStatus: BecomeMerchantStatus? = null
    @SerializedName("got_extra_loan")
    var extraLoanStatus: ExtraLoanStatus? = null

    val isAgent: Boolean
        get() = type == UserType.AGENT

    val isMerchant: Boolean
        get() = type == UserType.MERCHANT

    val isClient: Boolean
        get() = type == UserType.CLIENT

    fun formatWelcomeMessage(): String {
        return Domain.application.getString(R.string.welcome) + " " + firstName
    }

    fun formatFullName(): String {
        return "$firstName $lastName"
    }

    fun showExtraLoan(): Boolean {
        return extraLoanStatus != null &&
                (extraLoanStatus == ExtraLoanStatus.NORMAL_STATE ||
                        extraLoanStatus == ExtraLoanStatus.REJECTED ||
                        extraLoanStatus == ExtraLoanStatus.PENDING)

    }
}
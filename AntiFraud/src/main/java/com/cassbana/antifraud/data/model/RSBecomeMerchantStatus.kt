package com.cassbana.antifraud.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class RSBecomeMerchantStatus(val value : Int) {

    @SerializedName("-2")
    THIS_CLIENT_DONOT_REQUEST_TO_RS_BECOME_MERCHANT(-2),
    @SerializedName("-1")
    REJECTED(-1),
    @SerializedName("0")
    NO_RESPONSE_YET(0),
    @SerializedName("1")
    ACCEPTED(1);

    companion object {
        operator fun invoke(rawValue: Int) = values()
            .find { it.value == rawValue }
    }
}
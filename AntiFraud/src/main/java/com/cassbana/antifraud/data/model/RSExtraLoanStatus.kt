package com.cassbana.antifraud.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class RSExtraLoanStatus(val value : Int) {

    @SerializedName("-2")
    NORMAL_STATE(-2),
    @SerializedName("-1")
    REJECTED(-1),
    @SerializedName("0")
    PENDING(0),
    @SerializedName("1")
    ACCEPTED(1);



    companion object {
        operator fun invoke(rawValue: Int) = values()
            .find { it.value == rawValue }
    }
}
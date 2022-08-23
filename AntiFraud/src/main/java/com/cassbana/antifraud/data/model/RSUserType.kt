package com.cassbana.antifraud.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class RSUserType(val value: Int) {
    @SerializedName("1")
    MERCHANT(1),
    @SerializedName("2")
    CLIENT(2),
    @SerializedName("3")
    AGENT(3),
    @SerializedName("4")
    UNKNOWN(4);

    companion object {
        operator fun invoke(rawValue: Int) = values()
            .find { it.value == rawValue }
    }
}
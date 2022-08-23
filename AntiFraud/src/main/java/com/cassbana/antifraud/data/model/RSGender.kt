package com.cassbana.antifraud.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class RSGender(val value: Int) {
    @SerializedName("1")
    MALE(1),
    @SerializedName("2")
    FEMALE(2);

    companion object {
        operator fun invoke(rawValue: Int) = values()
            .find { it.value == rawValue }
    }
}
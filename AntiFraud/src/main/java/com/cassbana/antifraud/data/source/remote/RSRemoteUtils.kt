package com.cassbana.antifraud.data.source.remote

import androidx.annotation.NonNull
import okhttp3.RequestBody

object RSRemoteUtils {

    @NonNull
    fun createPartFromString(contentString: String): RequestBody {
        return RequestBody.create(
            okhttp3.MultipartBody.FORM, contentString
        )
    }
}
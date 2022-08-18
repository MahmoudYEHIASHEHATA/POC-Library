package com.cassbana.antifraud.data.source.local


import com.cassbana.antifraud.data.source.prefs.preferencesGateway

import com.cassbana.antifraud.Constants.USER
import com.cassbana.antifraud.data.model.User
import com.cassbana.antifraud.data.model.UserType
import com.google.gson.Gson

object UserDataSource {

    fun saveUser(user: User?) {
        preferencesGateway.save(USER, Gson().toJson(user))
    }

    fun getUser(): User? {
        return Gson().fromJson(
            preferencesGateway.load(USER, ""), User::class.java
        )
    }

    fun getToken(): String {
        return if (hasUser()) "Bearer " + getUser()?.token else ""
    }

    fun hasUser(): Boolean {
        val user = Gson().fromJson(
            preferencesGateway.load(USER, ""),
            User::class.java
        )

        if (user?.token != null) {
            return user.token.toString().isNotEmpty()
        }

        return false
    }

    fun isAgent(): Boolean {
        val user = Gson().fromJson(
            preferencesGateway.load(USER, ""),
            User::class.java
        )

        if (user?.type != null) {
            return user.type == UserType.AGENT
        }
        return false
    }


    fun isMerchant(): Boolean {
        val user = Gson().fromJson(
            preferencesGateway.load(USER, ""),
            User::class.java
        )

        if (user?.type != null) {
            return user.type == UserType.MERCHANT
        }
        return false
    }


    fun isClient(): Boolean {
        val user = Gson().fromJson(
            preferencesGateway.load(USER, ""),
            User::class.java
        )

        if (user?.type != null) {
            return user.type == UserType.CLIENT
        }
        return false
    }
}
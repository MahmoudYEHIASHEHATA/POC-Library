package com.cassbana.antifraud



object RSDomain {

    lateinit var application: RSApp private set


    fun integrateWith(application: RSApp) {
        RSDomain.application = application
    }
}
package com.cassbana.antifraud



object Domain {

    lateinit var application: App private set


    fun integrateWith(application: App) {
        Domain.application = application
    }
}
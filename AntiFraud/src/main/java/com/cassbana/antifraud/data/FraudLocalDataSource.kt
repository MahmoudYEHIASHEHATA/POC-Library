package com.cassbana.antifraud.data

interface FraudLocalDataSource {

    suspend fun deleteContacts ()
    suspend fun deleteBluetooth ()
    suspend fun deleteLocation ()
    suspend fun deleteAppHistory ()

}
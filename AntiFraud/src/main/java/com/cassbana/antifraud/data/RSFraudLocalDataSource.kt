package com.cassbana.antifraud.data

interface RSFraudLocalDataSource {

    suspend fun deleteContacts ()
    suspend fun deleteBluetooth ()
    suspend fun deleteLocation ()
    suspend fun deleteAppHistory ()

}
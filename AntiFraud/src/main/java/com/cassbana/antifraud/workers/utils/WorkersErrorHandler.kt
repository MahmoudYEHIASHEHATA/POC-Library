package com.cassbana.antifraud.workers.utils

import android.content.Context
import androidx.annotation.Keep
import androidx.work.ListenableWorker



@Keep
class DataCollectionWorkerException(message: String?) : Exception(message)
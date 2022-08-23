package com.cassbana.antifraud.workers.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object RSWorkerUtils {
    /**
     * get flow that emits the broadcast receiver emits on specific intent filter actions for
     * specific period.
     *
     * @param context
     * @param intentFilterActions the intent action filters that the broadcast receiver will listen
     * for.
     * @param onBroadcastReceived the function that will be called to convert the intent received by the
     * broadcast receiver to List<T> that will be emitted through the flow.
     * @param waitingPeriod the waiting period that will be delayed by the flow to listen for the
     * broadcast receiver emits.
     */
    @ExperimentalCoroutinesApi
    fun <T> getFlowFromBroadcast(
        context: Context,
        intentFilterActions: List<String>,
        onBroadcastRegistered: ()-> Unit,
        onBroadcastReceived: (intent: Intent, producerScope: ProducerScope<T>) -> Unit,
        waitingPeriod: Long,
    ): Flow<T> = callbackFlow {
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                onBroadcastReceived(intent, this@callbackFlow)
            }
        }
        context.registerReceiver(
            broadcastReceiver,
            IntentFilter().apply {
                intentFilterActions.forEach { action ->
                    addAction(action)
                }
            }
        )
        onBroadcastRegistered()

        delay(waitingPeriod)
        close()
        awaitClose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }


    @ExperimentalCoroutinesApi
    fun <T> getFlowFromBroadcast(
        context: Context,
        intentFilterActions: List<String>,
        onBroadcastRegistered: (() -> Unit)? = null,
        onBroadcastReceived: (intent: Intent, producerScope: ProducerScope<T>) -> Unit
    ): Flow<T> = callbackFlow {
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                onBroadcastReceived(intent, this@callbackFlow)
            }
        }
        context.registerReceiver(
            broadcastReceiver,
            IntentFilter().apply {
                intentFilterActions.forEach { action ->
                    addAction(action)
                }
            }
        )
        onBroadcastRegistered?.invoke()
        awaitClose{
            context.unregisterReceiver(broadcastReceiver)
        }
    }
}
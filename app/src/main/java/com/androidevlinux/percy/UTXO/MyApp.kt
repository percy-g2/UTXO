package com.androidevlinux.percy.UTXO

import android.app.Application

import com.androidevlinux.percy.UTXO.utils.ConnectionReceiver

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun setConnectionListener(listener: ConnectionReceiver.ConnectionReceiverListener) {
        ConnectionReceiver.connectionReceiverListener = listener
    }

    companion object {

        @get:Synchronized
        var instance: MyApp? = null
            private set
    }
}

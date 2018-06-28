package com.androidevlinux.percy.UTXO;

import android.app.Application;

import com.androidevlinux.percy.UTXO.utils.ConnectionReceiver;

public class MyApp extends Application {

    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApp getInstance() {
        return mInstance;
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener) {
        ConnectionReceiver.connectionReceiverListener = listener;
    }
}

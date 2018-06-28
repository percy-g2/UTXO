package com.androidevlinux.percy.UTXO.utils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.androidevlinux.percy.UTXO.MyApp;

import java.util.Objects;

public class ConnectionReceiver extends BroadcastReceiver {

    public static ConnectionReceiverListener connectionReceiverListener;

    public ConnectionReceiver() {
        super();
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent arg1) {
        if (arg1.getExtras() != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null
                    && activeNetwork.isConnectedOrConnecting();

            if (connectionReceiverListener != null) {
                connectionReceiverListener.onNetworkConnectionChanged(isConnected);
            }
        }
    }

    public static boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) MyApp.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }


    public interface ConnectionReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}


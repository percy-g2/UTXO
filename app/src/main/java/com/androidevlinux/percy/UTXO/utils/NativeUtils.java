package com.androidevlinux.percy.UTXO.utils;

/**
 * Created by percy on 22/11/17.
 */

public class NativeUtils {

    static {
        System.loadLibrary("utxo-jni");
    }
    public static native String getBitfinexBaseUrl();
    public static native String getBlocktrailBaseUrl();
    public static native String getbitStampBaseUrl();
    public static native String getBlocktrailApiKey();
    public static native String getChangellyApiKey();
    public static native String getChangellySecretKey();
    public static native String getChangellyBaseUrl();
    public static native String getZebpayBaseUrl();
    public static native String getPocketbitsBaseUrl();
    public static native String getCoinMarketCapBaseUrl();
    public static native String getGdaxBaseUrl();
}

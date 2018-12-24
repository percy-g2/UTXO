package com.androidevlinux.percy.UTXO.utils

/**
 * Created by percy on 22/11/17.
 */

object NativeUtils {
    val bitfinexBaseUrl: String
        @JvmStatic
        external get
    val bitmexBaseUrl: String
        @JvmStatic
        external get
    val blocktrailBaseUrl: String
        @JvmStatic
        external get
    val bitStampBaseUrl: String
        @JvmStatic
        external get
    val blocktrailApiKey: String
        @JvmStatic
        external get
    val changellyApiKey: String
        @JvmStatic
        external get
    val changellySecretKey: String
        @JvmStatic
        external get
    val changellyBaseUrl: String
        @JvmStatic
        external get
    val binanceBaseUrl: String
        @JvmStatic
        external get
    val okexBaseUrl: String
        @JvmStatic
        external get
    val pocketbitsBaseUrl: String
        @JvmStatic
        external get
    val coinMarketCapBaseUrl: String
        @JvmStatic
        external get
    val gdaxBaseUrl: String
        @JvmStatic
        external get
    val newsApiBaseUrl: String
        @JvmStatic
        external get
    val newsApiKey: String
        @JvmStatic
        external get

    init {
        System.loadLibrary("utxo-jni")
    }
}

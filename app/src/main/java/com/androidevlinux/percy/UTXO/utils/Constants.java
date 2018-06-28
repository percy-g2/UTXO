package com.androidevlinux.percy.UTXO.utils;

import java.util.List;

/**
 * Created by percy on 18/11/17
 */

public class Constants {
    public static String api_key = NativeUtils.getChangellyApiKey();
    public static String secret_key = NativeUtils.getChangellySecretKey();
    public static String btc_price = "0.00";
    public static String btc_price_low = "0.00";
    public static String btc_price_high = "0.00";
    public static String currentCurrency = "BTC";
    public static List<String> currenciesStringList;
}

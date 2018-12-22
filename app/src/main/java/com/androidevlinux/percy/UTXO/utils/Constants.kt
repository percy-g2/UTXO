package com.androidevlinux.percy.UTXO.utils

/**
 * Created by percy on 18/11/17
 */

object Constants {
    var api_key = NativeUtils.changellyApiKey
    var secret_key = NativeUtils.changellySecretKey
    var btc_price = "0.00"
    var btc_price_low = "0.00"
    var btc_price_high = "0.00"
    var currentCurrency = "BTC"
    var currenciesStringList: List<String>? = null
}

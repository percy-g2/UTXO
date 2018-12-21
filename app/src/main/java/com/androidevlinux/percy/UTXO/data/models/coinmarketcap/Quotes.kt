package com.androidevlinux.percy.UTXO.data.models.coinmarketcap

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Quotes {

    @SerializedName("USD")
    @Expose
    var usd: USD? = null

}
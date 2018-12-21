package com.androidevlinux.percy.UTXO.data.models.coinmarketcap

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class CoinMarketCapCoin : Serializable {
    @SerializedName("data")
    @Expose
    var data: Data? = null
    @SerializedName("metadata")
    @Expose
    var metadata: Metadata? = null

    var isExpanded: Boolean = false

}
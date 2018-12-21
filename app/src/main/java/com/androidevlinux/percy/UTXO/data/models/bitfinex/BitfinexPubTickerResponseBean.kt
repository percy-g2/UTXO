package com.androidevlinux.percy.UTXO.data.models.bitfinex

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by percy on 18/11/17.
 */

class BitfinexPubTickerResponseBean : Serializable {

    @SerializedName("mid")
    @Expose
    var mid: String? = null
    @SerializedName("bid")
    @Expose
    var bid: String? = null
    @SerializedName("ask")
    @Expose
    var ask: String? = null
    @SerializedName("last_price")
    @Expose
    var lastPrice: String? = null
    @SerializedName("low")
    @Expose
    var low: String? = null
    @SerializedName("high")
    @Expose
    var high: String? = null
    @SerializedName("volume")
    @Expose
    var volume: String? = null
    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null

    companion object {
        private const val serialVersionUID = -5611742423241891237L
    }

}

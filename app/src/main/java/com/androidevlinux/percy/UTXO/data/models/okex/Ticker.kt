package com.androidevlinux.percy.UTXO.data.models.okex

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Ticker : Serializable {
    @SerializedName("high")
    @Expose
    var high: Double? = null
    @SerializedName("vol")
    @Expose
    var vol: Double? = null
    @SerializedName("last")
    @Expose
    var last: Double? = null
    @SerializedName("low")
    @Expose
    var low: Double? = null
    @SerializedName("buy")
    @Expose
    var buy: Double? = null
    @SerializedName("sell")
    @Expose
    var sell: Double? = null

}

package com.androidevlinux.percy.UTXO.data.models.okex

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class OkexTickerBean : Serializable {
    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("ticker")
    @Expose
    var ticker: Ticker? = null
}

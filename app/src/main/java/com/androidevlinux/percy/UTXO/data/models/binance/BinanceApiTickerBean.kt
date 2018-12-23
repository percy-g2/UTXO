package com.androidevlinux.percy.UTXO.data.models.binance

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BinanceApiTickerBean : Serializable {

    @SerializedName("symbol")
    @Expose
    var symbol: String? = null
    @SerializedName("priceChange")
    @Expose
    var priceChange: String? = null
    @SerializedName("priceChangePercent")
    @Expose
    var priceChangePercent: String? = null
    @SerializedName("weightedAvgPrice")
    @Expose
    var weightedAvgPrice: String? = null
    @SerializedName("prevClosePrice")
    @Expose
    var prevClosePrice: String? = null
    @SerializedName("lastPrice")
    @Expose
    var lastPrice: Double? = null
    @SerializedName("lastQty")
    @Expose
    var lastQty: String? = null
    @SerializedName("bidPrice")
    @Expose
    var bidPrice: String? = null
    @SerializedName("bidQty")
    @Expose
    var bidQty: String? = null
    @SerializedName("askPrice")
    @Expose
    var askPrice: String? = null
    @SerializedName("askQty")
    @Expose
    var askQty: String? = null
    @SerializedName("openPrice")
    @Expose
    var openPrice: String? = null
    @SerializedName("highPrice")
    @Expose
    var highPrice: Double? = null
    @SerializedName("lowPrice")
    @Expose
    var lowPrice: Double? = null
    @SerializedName("volume")
    @Expose
    var volume: String? = null
    @SerializedName("quoteVolume")
    @Expose
    var quoteVolume: String? = null
    @SerializedName("openTime")
    @Expose
    var openTime: Double? = null
    @SerializedName("closeTime")
    @Expose
    var closeTime: Double? = null
    @SerializedName("firstId")
    @Expose
    var firstId: Int? = null
    @SerializedName("lastId")
    @Expose
    var lastId: Int? = null
    @SerializedName("count")
    @Expose
    var count: Int? = null


}

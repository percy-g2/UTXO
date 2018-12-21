package com.androidevlinux.percy.UTXO.data.models.bitstamp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class BitstampBean : Serializable {

    @SerializedName("high")
    @Expose
    var high: String? = null
    @SerializedName("last")
    @Expose
    var last: String? = null
    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null
    @SerializedName("bid")
    @Expose
    var bid: String? = null
    @SerializedName("vwap")
    @Expose
    var vwap: String? = null
    @SerializedName("volume")
    @Expose
    var volume: String? = null
    @SerializedName("low")
    @Expose
    var low: String? = null
    @SerializedName("ask")
    @Expose
    var ask: String? = null
    @SerializedName("open")
    @Expose
    var open: String? = null

    fun withHigh(high: String): BitstampBean {
        this.high = high
        return this
    }

    fun withLast(last: String): BitstampBean {
        this.last = last
        return this
    }

    fun withTimestamp(timestamp: String): BitstampBean {
        this.timestamp = timestamp
        return this
    }

    fun withBid(bid: String): BitstampBean {
        this.bid = bid
        return this
    }

    fun withVwap(vwap: String): BitstampBean {
        this.vwap = vwap
        return this
    }

    fun withVolume(volume: String): BitstampBean {
        this.volume = volume
        return this
    }

    fun withLow(low: String): BitstampBean {
        this.low = low
        return this
    }

    fun withAsk(ask: String): BitstampBean {
        this.ask = ask
        return this
    }

    fun withOpen(open: String): BitstampBean {
        this.open = open
        return this
    }

    companion object {
        private const val serialVersionUID = -5266039118653418081L
    }

}

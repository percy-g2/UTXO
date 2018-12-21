package com.androidevlinux.percy.UTXO.data.models.gdax

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class GDAX : Serializable {

    @SerializedName("trade_id")
    @Expose
    var tradeId: Int = 0
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("size")
    @Expose
    var size: String? = null
    @SerializedName("bid")
    @Expose
    var bid: String? = null
    @SerializedName("ask")
    @Expose
    var ask: String? = null
    @SerializedName("volume")
    @Expose
    var volume: String? = null
    @SerializedName("time")
    @Expose
    var time: String? = null

    fun withTradeId(tradeId: Int): GDAX {
        this.tradeId = tradeId
        return this
    }

    fun withPrice(price: String): GDAX {
        this.price = price
        return this
    }

    fun withSize(size: String): GDAX {
        this.size = size
        return this
    }

    fun withBid(bid: String): GDAX {
        this.bid = bid
        return this
    }

    fun withAsk(ask: String): GDAX {
        this.ask = ask
        return this
    }

    fun withVolume(volume: String): GDAX {
        this.volume = volume
        return this
    }

    fun withTime(time: String): GDAX {
        this.time = time
        return this
    }

    companion object {
        private const val serialVersionUID = 4130765474459101813L
    }

}

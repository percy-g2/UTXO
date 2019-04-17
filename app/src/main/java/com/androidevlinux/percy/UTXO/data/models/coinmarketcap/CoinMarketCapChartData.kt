package com.androidevlinux.percy.UTXO.data.models.coinmarketcap

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class CoinMarketCapChartData : Serializable {

    @SerializedName("market_cap_by_available_supply")
    @Expose
    var marketCapByAvailableSupply: List<List<Float>>? = null
    @SerializedName("price_btc")
    @Expose
    var priceBtc: List<List<Float>>? = null
    @SerializedName("price_usd")
    @Expose
    var priceUsd: List<List<Float>>? = null
    @SerializedName("volume_usd")
    @Expose
    var volumeUsd: List<List<Float>>? = null

    fun withMarketCapByAvailableSupply(marketCapByAvailableSupply: List<List<Float>>): CoinMarketCapChartData {
        this.marketCapByAvailableSupply = marketCapByAvailableSupply
        return this
    }

    fun withPriceBtc(priceBtc: List<List<Float>>): CoinMarketCapChartData {
        this.priceBtc = priceBtc
        return this
    }

    fun withPriceUsd(priceUsd: List<List<Float>>): CoinMarketCapChartData {
        this.priceUsd = priceUsd
        return this
    }

    fun withVolumeUsd(volumeUsd: List<List<Float>>): CoinMarketCapChartData {
        this.volumeUsd = volumeUsd
        return this
    }

    companion object {
        private const val serialVersionUID = 4430369643668729100L
    }

}


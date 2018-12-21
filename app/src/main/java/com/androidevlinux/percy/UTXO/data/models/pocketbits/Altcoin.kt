package com.androidevlinux.percy.UTXO.data.models.pocketbits

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Altcoin : Serializable {

    @SerializedName("AltName")
    @Expose
    var altName: String? = null
    @SerializedName("AltBuyPrice")
    @Expose
    var altBuyPrice: Double = 0.toDouble()
    @SerializedName("AltSellPrice")
    @Expose
    var altSellPrice: Double = 0.toDouble()

    fun withAltName(altName: String): Altcoin {
        this.altName = altName
        return this
    }

    fun withAltBuyPrice(altBuyPrice: Double): Altcoin {
        this.altBuyPrice = altBuyPrice
        return this
    }

    fun withAltSellPrice(altSellPrice: Double): Altcoin {
        this.altSellPrice = altSellPrice
        return this
    }

    companion object {
        private const val serialVersionUID = 1969951576474414747L
    }

}

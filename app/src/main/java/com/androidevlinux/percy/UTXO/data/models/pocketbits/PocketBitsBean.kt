package com.androidevlinux.percy.UTXO.data.models.pocketbits

/**
 * Created by percy on 20/1/18.
 */


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class PocketBitsBean : Serializable {

    @SerializedName("Altcoins")
    @Expose
    var altcoins: List<Altcoin>? = null

    fun withAltcoins(altcoins: List<Altcoin>): PocketBitsBean {
        this.altcoins = altcoins
        return this
    }

    companion object {
        private const val serialVersionUID = 1967121703865114065L
    }

}
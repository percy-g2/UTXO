package com.androidevlinux.percy.UTXO.data.models.coinmarketcap


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Metadata {

    @SerializedName("timestamp")
    @Expose
    var timestamp: Int? = null
    @SerializedName("error")
    @Expose
    var error: Any? = null

}

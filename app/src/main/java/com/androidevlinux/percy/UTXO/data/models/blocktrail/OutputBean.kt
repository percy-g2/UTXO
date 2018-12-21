package com.androidevlinux.percy.UTXO.data.models.blocktrail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class OutputBean : Serializable {

    @SerializedName("index")
    @Expose
    var index: Int? = null
    @SerializedName("value")
    @Expose
    var value: Int? = null
    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("multisig")
    @Expose
    var multisig: Any? = null
    @SerializedName("script")
    @Expose
    var script: String? = null
    @SerializedName("script_hex")
    @Expose
    var scriptHex: String? = null
    @SerializedName("spent_hash")
    @Expose
    var spentHash: String? = null
    @SerializedName("spent_index")
    @Expose
    var spentIndex: Int? = null

    companion object {
        private const val serialVersionUID = -3389673860598109179L
    }

}
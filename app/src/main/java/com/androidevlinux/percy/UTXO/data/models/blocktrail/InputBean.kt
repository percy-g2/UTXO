package com.androidevlinux.percy.UTXO.data.models.blocktrail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class InputBean : Serializable {

    @SerializedName("index")
    @Expose
    var index: Int? = null
    @SerializedName("output_hash")
    @Expose
    var outputHash: String? = null
    @SerializedName("output_index")
    @Expose
    var outputIndex: Int? = null
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
    @SerializedName("script_signature")
    @Expose
    var scriptSignature: String? = null

    companion object {
        private const val serialVersionUID = 2294763987680187324L
    }

}
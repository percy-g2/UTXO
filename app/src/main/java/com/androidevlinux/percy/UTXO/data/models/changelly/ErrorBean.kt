package com.androidevlinux.percy.UTXO.data.models.changelly

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by percy on 19/11/17.
 */

class ErrorBean : Serializable {

    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("message")
    @Expose
    var message: String? = null

    companion object {
        private const val serialVersionUID = 6794346013045989207L
    }
}

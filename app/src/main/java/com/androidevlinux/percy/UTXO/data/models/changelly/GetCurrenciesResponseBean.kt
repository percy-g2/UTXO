package com.androidevlinux.percy.UTXO.data.models.changelly

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by percy on 15/11/2017.
 */

class GetCurrenciesResponseBean {

    @SerializedName("jsonrpc")
    @Expose
    var jsonrpc: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("result")
    @Expose
    var result: List<String>? = null
}

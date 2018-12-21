package com.androidevlinux.percy.UTXO.data.models.changelly

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by percy on 15/11/2017.
 */

class MainBodyBean {
    @SerializedName("jsonrpc")
    @Expose
    var jsonrpc: String? = null
    @SerializedName("method")
    @Expose
    var method: String? = null
    @SerializedName("params")
    @Expose
    var params: ParamsBean? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null

}

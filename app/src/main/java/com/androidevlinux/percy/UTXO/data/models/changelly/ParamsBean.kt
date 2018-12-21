package com.androidevlinux.percy.UTXO.data.models.changelly

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by percy on 15/11/2017.
 */

class ParamsBean {

    @SerializedName("from")
    @Expose
    var from: String? = null
    @SerializedName("to")
    @Expose
    var to: String? = null
    @SerializedName("amount")
    @Expose
    var amount: String? = null
    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null
}

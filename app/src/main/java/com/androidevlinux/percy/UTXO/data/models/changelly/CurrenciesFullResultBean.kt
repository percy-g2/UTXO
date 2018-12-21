package com.androidevlinux.percy.UTXO.data.models.changelly

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by percy on 15/11/2017.
 */

class CurrenciesFullResultBean {
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("fullName")
    @Expose
    var fullName: String? = null
    @SerializedName("enabled")
    @Expose
    var enabled: Boolean? = null

}

package com.androidevlinux.percy.UTXO.data.models.changelly

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by percy on 15/11/2017.
 */

class TransactionResultBean {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("apiExtraFee")
    @Expose
    var apiExtraFee: String? = null
    @SerializedName("changellyFee")
    @Expose
    var changellyFee: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("currencyFrom")
    @Expose
    var currencyFrom: String? = null
    @SerializedName("currencyTo")
    @Expose
    var currencyTo: String? = null
    @SerializedName("amountTo")
    @Expose
    var amountTo: Int? = null
    @SerializedName("payinAddress")
    @Expose
    var payinAddress: String? = null
    @SerializedName("payoutAddress")
    @Expose
    var payoutAddress: String? = null
    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null
}

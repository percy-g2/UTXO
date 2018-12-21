package com.androidevlinux.percy.UTXO.data.models.blocktrail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class AddressBean : Serializable {

    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("hash160")
    @Expose
    var hash160: String? = null
    @SerializedName("balance")
    @Expose
    var balance: Int? = null
    @SerializedName("received")
    @Expose
    var received: Int? = null
    @SerializedName("sent")
    @Expose
    var sent: Int? = null
    @SerializedName("transactions")
    @Expose
    var transactions: Int? = null
    @SerializedName("utxos")
    @Expose
    var utxos: Int? = null
    @SerializedName("unconfirmed_received")
    @Expose
    var unconfirmedReceived: Int? = null
    @SerializedName("unconfirmed_sent")
    @Expose
    var unconfirmedSent: Int? = null
    @SerializedName("unconfirmed_transactions")
    @Expose
    var unconfirmedTransactions: Int? = null
    @SerializedName("unconfirmed_utxos")
    @Expose
    var unconfirmedUtxos: Int? = null
    @SerializedName("total_transactions_in")
    @Expose
    var totalTransactionsIn: Int? = null
    @SerializedName("total_transactions_out")
    @Expose
    var totalTransactionsOut: Int? = null
    @SerializedName("category")
    @Expose
    var category: Any? = null
    @SerializedName("tag")
    @Expose
    var tag: Any? = null
    @SerializedName("first_seen")
    @Expose
    var firstSeen: Any? = null
    @SerializedName("last_seen")
    @Expose
    var lastSeen: Any? = null

    companion object {
        private const val serialVersionUID = 1204703229998514129L
    }

}

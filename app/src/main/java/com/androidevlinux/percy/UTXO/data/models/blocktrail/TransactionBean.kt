package com.androidevlinux.percy.UTXO.data.models.blocktrail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class TransactionBean : Serializable {

    @SerializedName("hash")
    @Expose
    var hash: String? = null
    @SerializedName("first_seen_at")
    @Expose
    var firstSeenAt: String? = null
    @SerializedName("last_seen_at")
    @Expose
    var lastSeenAt: String? = null
    @SerializedName("block_height")
    @Expose
    var blockHeight: Int? = null
    @SerializedName("block_time")
    @Expose
    var blockTime: String? = null
    @SerializedName("block_hash")
    @Expose
    var blockHash: String? = null
    @SerializedName("confirmations")
    @Expose
    var confirmations: Int? = null
    @SerializedName("is_coinbase")
    @Expose
    var isCoinbase: Boolean? = null
    @SerializedName("estimated_value")
    @Expose
    var estimatedValue: Int? = null
    @SerializedName("total_input_value")
    @Expose
    var totalInputValue: Int? = null
    @SerializedName("total_output_value")
    @Expose
    var totalOutputValue: Int? = null
    @SerializedName("total_fee")
    @Expose
    var totalFee: Int? = null
    @SerializedName("estimated_change")
    @Expose
    var estimatedChange: Int? = null
    @SerializedName("estimated_change_address")
    @Expose
    var estimatedChangeAddress: String? = null
    @SerializedName("high_priority")
    @Expose
    var highPriority: Boolean? = null
    @SerializedName("enough_fee")
    @Expose
    var enoughFee: Boolean? = null
    @SerializedName("contains_dust")
    @Expose
    var containsDust: Boolean? = null
    @SerializedName("inputs")
    @Expose
    var inputs: List<InputBean>? = null
    @SerializedName("outputs")
    @Expose
    var outputs: List<OutputBean>? = null

    companion object {
        private const val serialVersionUID = -3994899921959599678L
    }

}
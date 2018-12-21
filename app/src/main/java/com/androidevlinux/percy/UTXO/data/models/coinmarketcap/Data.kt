package com.androidevlinux.percy.UTXO.data.models.coinmarketcap

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("symbol")
    @Expose
    var symbol: String? = null
    @SerializedName("website_slug")
    @Expose
    var websiteSlug: String? = null
    @SerializedName("rank")
    @Expose
    var rank: Int? = null
    @SerializedName("circulating_supply")
    @Expose
    var circulatingSupply: Double? = null
    @SerializedName("total_supply")
    @Expose
    var totalSupply: Double? = null
    @SerializedName("max_supply")
    @Expose
    var maxSupply: Double? = null
    @SerializedName("quotes")
    @Expose
    var quotes: Quotes? = null
    @SerializedName("last_updated")
    @Expose
    var lastUpdated: Int? = null

}

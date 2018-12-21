package com.androidevlinux.percy.UTXO.data.models.newsapi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsBean {

    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null
    @SerializedName("articles")
    @Expose
    var articles: List<Article>? = null

}

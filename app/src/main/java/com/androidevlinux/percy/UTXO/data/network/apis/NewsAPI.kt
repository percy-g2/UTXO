package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.newsapi.NewsBean

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("/v2/everything?q=cryptocurrency&language=en&sortBy=publishedAt")
    fun getNewsData(@Query(value = "apiKey", encoded = true) apiKey: String): Observable<NewsBean>
}

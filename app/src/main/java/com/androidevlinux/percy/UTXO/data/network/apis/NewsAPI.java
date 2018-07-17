package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.newsapi.NewsBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {
    @GET("/v2/everything?q=cryptocurrency&sortBy=publishedAt")
    Observable<NewsBean> getNewsData(@Query(value = "apiKey", encoded = true) String apiKey);
}

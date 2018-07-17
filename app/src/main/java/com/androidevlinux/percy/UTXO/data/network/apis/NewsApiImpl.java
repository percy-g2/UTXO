package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.newsapi.NewsBean;
import com.androidevlinux.percy.UTXO.utils.NativeUtils;

import io.reactivex.Observable;

public class NewsApiImpl extends AbstractBaseApi<NewsAPI> {

    private static NewsApiImpl newsApiManager;
    private NewsAPI newsAPI;

    private NewsApiImpl() {
        setBaseUrl(NativeUtils.getNewsApiBaseUrl());
        newsAPI = getClient(NewsAPI.class);

    }

    public static NewsApiImpl getInstance() {
        if (newsApiManager == null)
            newsApiManager = new NewsApiImpl();
        return newsApiManager;
    }

    public Observable<NewsBean> getNewsData(String apiKey) {
        return newsAPI.getNewsData(apiKey);
    }
}


package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.newsapi.NewsBean
import com.androidevlinux.percy.UTXO.utils.NativeUtils

import io.reactivex.Observable

class NewsApiImpl private constructor() : AbstractBaseApi<NewsAPI>() {
    private val newsAPI: NewsAPI

    init {
        setBaseUrl(NativeUtils.getNewsApiBaseUrl())
        newsAPI = getClient(NewsAPI::class.java)

    }

    fun getNewsData(apiKey: String): Observable<NewsBean> {
        return newsAPI.getNewsData(apiKey)
    }

    companion object {

        private var newsApiManager: NewsApiImpl? = null

        val instance: NewsApiImpl
            get() {
                if (newsApiManager == null)
                    newsApiManager = NewsApiImpl()
                return newsApiManager!!
            }
    }
}


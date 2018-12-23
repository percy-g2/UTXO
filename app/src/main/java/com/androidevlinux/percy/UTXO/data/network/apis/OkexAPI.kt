package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.okex.OkexTickerBean
import retrofit2.http.GET
import retrofit2.http.Query

interface OkexAPI {
    @GET("/api/v1/ticker.do")
    fun getOkexTicker(@Query(value = "symbol", encoded = true) symbol: String): io.reactivex.Observable<OkexTickerBean>
}
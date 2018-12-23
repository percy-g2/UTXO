package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.binance.BinanceApiTickerBean
import retrofit2.http.GET
import retrofit2.http.Query

interface BinanceAPI {
    @GET("/api/v1/ticker/24hr")
    fun getBinanceTicker(@Query(value = "symbol", encoded = true) symbol: String): io.reactivex.Observable<BinanceApiTickerBean>
}

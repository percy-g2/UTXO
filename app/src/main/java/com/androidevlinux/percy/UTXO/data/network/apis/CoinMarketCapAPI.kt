package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapChartData
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapCoin

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface CoinMarketCapAPI {
    @GET
    fun getChartData(@Url url: String): Observable<CoinMarketCapChartData>

    @GET("/v2/ticker/{id}")
    fun getChartFooterData(@Path(value = "id", encoded = true) currency: String): Observable<CoinMarketCapCoin>
}

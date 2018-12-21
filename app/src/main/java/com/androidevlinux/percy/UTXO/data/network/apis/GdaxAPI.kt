package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.gdax.GDAX

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GdaxAPI {
    @GET("/products/{symbol}/ticker")
    fun getData(@Path(value = "symbol", encoded = true) symbol: String): Observable<GDAX>
}


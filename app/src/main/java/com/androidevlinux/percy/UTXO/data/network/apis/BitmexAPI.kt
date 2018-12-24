package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.bitmex.BitMEXTickerBean
import io.reactivex.Observable
import retrofit2.http.GET

interface BitmexAPI {
    @GET("/api/v1/instrument/active")
    fun getBitmexTicker(): Observable<List<BitMEXTickerBean>>
}
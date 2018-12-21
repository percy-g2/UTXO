package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.bitfinex.BitfinexPubTickerResponseBean

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by percy on 22/11/17.
 */

interface BitfinexAPI {
    @GET("/v1/pubticker/{symbol}/")
    fun getBitfinexTicker(@Path(value = "symbol", encoded = true) symbol: String): io.reactivex.Observable<BitfinexPubTickerResponseBean>

    @GET("/v2/candles/trade:{time}:{symbol}/hist/")
    fun getBitfinexData(@Path(value = "time", encoded = true) time: String, @Path(value = "symbol", encoded = true) symbol: String): Call<ResponseBody>
}

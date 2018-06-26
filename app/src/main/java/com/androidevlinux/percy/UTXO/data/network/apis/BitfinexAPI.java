package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.bitfinex.BitfinexPubTickerResponseBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by percy on 22/11/17.
 */

public interface BitfinexAPI {
    @GET("/v1/pubticker/{symbol}/")
    io.reactivex.Observable<BitfinexPubTickerResponseBean> getBitfinexTicker(@Path(value = "symbol", encoded = true) String symbol);

    @GET("/v2/candles/trade:{time}:{symbol}/hist/")
    Call<ResponseBody> getBitfinexData(@Path(value = "time", encoded = true) String time, @Path(value = "symbol", encoded = true) String symbol);
}

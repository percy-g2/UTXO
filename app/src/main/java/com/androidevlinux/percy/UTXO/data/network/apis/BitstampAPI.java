package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.bitstamp.BitstampBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by percy on 5/12/17.
 */

public interface BitstampAPI {

    @GET("/api/v2/ticker/{symbol}/")
    Observable<BitstampBean> getBitstampTicker(@Path(value = "symbol", encoded = true) String symbol);
}

package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.gdax.GDAX;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GdaxAPI {
    @GET("/products/{symbol}/ticker")
    Observable<GDAX> getData(@Path(value = "symbol", encoded = true) String symbol);
}


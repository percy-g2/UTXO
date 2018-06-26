package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.zebpay.ZebPayBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by percy on 3/12/17.
 */

public interface ZebpayAPI {
    @GET("/api/v1/market/ticker-new/{symbol}/INR")
    Observable<ZebPayBean> getZebpayTicker(@Path(value = "symbol", encoded = true) String symbol);
}

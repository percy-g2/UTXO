package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapChartData;
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapCoin;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface CoinMarketCapAPI {
    @GET
    Observable<CoinMarketCapChartData> getChartData(@Url String url);

    @GET("/v2/ticker/{id}")
    Observable<CoinMarketCapCoin> getChartFooterData(@Path(value = "id", encoded = true) String currency);
}

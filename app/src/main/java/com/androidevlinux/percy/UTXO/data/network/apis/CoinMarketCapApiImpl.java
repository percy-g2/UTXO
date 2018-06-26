package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapChartData;
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapCoin;
import com.androidevlinux.percy.UTXO.utils.NativeUtils;

import io.reactivex.Observable;

public class CoinMarketCapApiImpl extends AbstractBaseApi<CoinMarketCapAPI> {
    private CoinMarketCapAPI coinMarketCapAPI;
    private static CoinMarketCapApiImpl coinMarketCapApiImpl;

    private CoinMarketCapApiImpl() {
        setBaseUrl(NativeUtils.getCoinMarketCapBaseUrl());
        coinMarketCapAPI = getClient(CoinMarketCapAPI.class);
    }

    public static CoinMarketCapApiImpl getInstance() {
        if (coinMarketCapApiImpl == null) {
            coinMarketCapApiImpl = new CoinMarketCapApiImpl();
        }
        return coinMarketCapApiImpl;
    }

    public Observable<CoinMarketCapChartData> getChartData(String url){
        return coinMarketCapAPI.getChartData(url);
    }

    public Observable<CoinMarketCapCoin> getChartFooterData(String currency){
        return coinMarketCapAPI.getChartFooterData(currency);
    }
}

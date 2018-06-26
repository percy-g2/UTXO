package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.bitstamp.BitstampBean;
import com.androidevlinux.percy.UTXO.utils.NativeUtils;

import io.reactivex.Observable;

/**
 * Created by percy on 5/12/17.
 */

public class BitstampApiImpl extends AbstractBaseApi<BitstampAPI> {

    private static BitstampApiImpl bitstampApiManager;
    private BitstampAPI bitstampAPI;
    private BitstampApiImpl(){
        setBaseUrl(NativeUtils.getbitStampBaseUrl());
        bitstampAPI = getClient(BitstampAPI.class);

    }

    public static BitstampApiImpl getInstance(){
        if(bitstampApiManager==null)
            bitstampApiManager = new BitstampApiImpl();
        return bitstampApiManager;
    }

    public Observable<BitstampBean> getBitstampTicker(String symbol){
        return bitstampAPI.getBitstampTicker(symbol);
    }
}

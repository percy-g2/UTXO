package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.gdax.GDAX;
import com.androidevlinux.percy.UTXO.utils.NativeUtils;

import io.reactivex.Observable;

public class GdaxApiImpl extends AbstractBaseApi<GdaxAPI> {

    private static GdaxApiImpl gdaxApiManager;
    private GdaxAPI gdaxAPI;
    private GdaxApiImpl(){
        setBaseUrl(NativeUtils.getGdaxBaseUrl());
        gdaxAPI = getClient(GdaxAPI.class);

    }

    public static GdaxApiImpl getInstance(){
        if(gdaxApiManager==null)
            gdaxApiManager = new GdaxApiImpl();
        return gdaxApiManager;
    }
    public Observable<GDAX> getData(String symbol){
        return gdaxAPI.getData(symbol);
    }
}

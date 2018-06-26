package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.zebpay.ZebPayBean;
import com.androidevlinux.percy.UTXO.utils.NativeUtils;

import io.reactivex.Observable;

/**
 * Created by percy on 3/12/17.
 */

public class ZebpayApiImpl extends AbstractBaseApi<ZebpayAPI> {

    private static ZebpayApiImpl zebpayApiManager;
    private ZebpayAPI zebpayAPI;
    private ZebpayApiImpl(){
        setBaseUrl(NativeUtils.getZebpayBaseUrl());
        zebpayAPI = getClient(ZebpayAPI.class);

    }

    public static ZebpayApiImpl getInstance(){
        if(zebpayApiManager==null)
            zebpayApiManager = new ZebpayApiImpl();
        return zebpayApiManager;
    }
    public Observable<ZebPayBean> getZebpayTicker(String symbol){
        return zebpayAPI.getZebpayTicker(symbol);
    }
}
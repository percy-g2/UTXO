package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.pocketbits.PocketBitsBean;
import com.androidevlinux.percy.UTXO.utils.NativeUtils;

import io.reactivex.Observable;

/**
 * Created by percy on 20/1/18.
 */

public class PocketbitsApiImpl extends AbstractBaseApi<PocketbitsAPI> {

    private static PocketbitsApiImpl pocketbitsApiManager;
    private PocketbitsAPI pocketbitsAPI;
    private PocketbitsApiImpl(){
        setBaseUrl(NativeUtils.getPocketbitsBaseUrl());
        pocketbitsAPI = getClient(PocketbitsAPI.class);

    }

    public static PocketbitsApiImpl getInstance(){
        if(pocketbitsApiManager==null)
            pocketbitsApiManager = new PocketbitsApiImpl();
        return pocketbitsApiManager;
    }
    public Observable<PocketBitsBean> getPocketbitsTicker(){
        return pocketbitsAPI.getPocketbitsTicker();
    }
}
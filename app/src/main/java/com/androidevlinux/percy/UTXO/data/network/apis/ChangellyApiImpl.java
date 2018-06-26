package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.changelly.GetCurrenciesResponseBean;
import com.androidevlinux.percy.UTXO.data.models.changelly.GetMinAmountReponseBean;
import com.androidevlinux.percy.UTXO.data.models.changelly.MainBodyBean;
import com.androidevlinux.percy.UTXO.data.models.changelly.TransactionBean;
import com.androidevlinux.percy.UTXO.utils.Constants;
import com.androidevlinux.percy.UTXO.utils.NativeUtils;

import retrofit2.Callback;

/**
 * Created by percy on 14/11/2017.
 */

public class ChangellyApiImpl extends AbstractBaseApi<ChangellyAPI> {
    private ChangellyAPI changellyAPI;
    private static ChangellyApiImpl changellyApiImpl;
    private ChangellyApiImpl(){
        setBaseUrl(NativeUtils.getChangellyBaseUrl());
        changellyAPI= getClient(ChangellyAPI.class);
    }

    public static ChangellyApiImpl getInstance(){
        if(changellyApiImpl ==null){
            changellyApiImpl = new ChangellyApiImpl();
        }
        return changellyApiImpl;
    }

    public void getCurrencies(String sign, MainBodyBean body, Callback<GetCurrenciesResponseBean> callback){
        changellyAPI.getCurrencies(CONTENT_TYPE, Constants.api_key,sign,body).enqueue(callback);
    }
    public void getMinAmount(String sign, MainBodyBean body, Callback<GetMinAmountReponseBean> callback){
        changellyAPI.getMinAmount(CONTENT_TYPE,Constants.api_key,sign,body).enqueue(callback);
    }

    public void createTransaction(String sign, MainBodyBean body, Callback<TransactionBean> callback){
        changellyAPI.createTransaction(CONTENT_TYPE,Constants.api_key,sign,body).enqueue(callback);
    }
}


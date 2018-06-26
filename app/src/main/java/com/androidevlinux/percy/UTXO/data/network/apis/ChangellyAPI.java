package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.changelly.GetCurrenciesResponseBean;
import com.androidevlinux.percy.UTXO.data.models.changelly.GetMinAmountReponseBean;
import com.androidevlinux.percy.UTXO.data.models.changelly.MainBodyBean;
import com.androidevlinux.percy.UTXO.data.models.changelly.TransactionBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by percy on 22/11/17.
 */

public interface ChangellyAPI {

    @POST("/")
    Call<GetCurrenciesResponseBean> getCurrencies(@Header("Content-Type") String content_type, @Header("api-key") String api_key, @Header("sign") String sign, @Body MainBodyBean p);

    @POST("/")
    Call<GetMinAmountReponseBean> getMinAmount(@Header("Content-Type") String content_type, @Header("api-key") String api_key, @Header("sign") String sign, @Body MainBodyBean p);

    @POST("/")
    Call<TransactionBean> createTransaction(@Header("Content-Type") String content_type, @Header("api-key") String api_key, @Header("sign") String sign, @Body MainBodyBean p);
}


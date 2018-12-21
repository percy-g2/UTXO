package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.changelly.GetCurrenciesResponseBean
import com.androidevlinux.percy.UTXO.data.models.changelly.GetMinAmountReponseBean
import com.androidevlinux.percy.UTXO.data.models.changelly.MainBodyBean
import com.androidevlinux.percy.UTXO.data.models.changelly.TransactionBean

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by percy on 22/11/17.
 */

interface ChangellyAPI {

    @POST("/")
    fun getCurrencies(@Header("Content-Type") content_type: String, @Header("api-key") api_key: String, @Header("sign") sign: String, @Body p: MainBodyBean): Call<GetCurrenciesResponseBean>

    @POST("/")
    fun getMinAmount(@Header("Content-Type") content_type: String, @Header("api-key") api_key: String, @Header("sign") sign: String, @Body p: MainBodyBean): Call<GetMinAmountReponseBean>

    @POST("/")
    fun createTransaction(@Header("Content-Type") content_type: String, @Header("api-key") api_key: String, @Header("sign") sign: String, @Body p: MainBodyBean): Call<TransactionBean>
}


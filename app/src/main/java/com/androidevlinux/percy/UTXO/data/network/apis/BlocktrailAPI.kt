package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.blocktrail.AddressBean
import com.androidevlinux.percy.UTXO.data.models.blocktrail.TransactionBean
import com.google.gson.JsonObject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by percy on 27/11/17.
 */

interface BlocktrailAPI {
    @GET("/v1/btc/{query}/{data}")
    fun getBlockTrailAddressData(@Path(value = "query", encoded = true) query: String, @Path(value = "data", encoded = true) data: String, @Query("api_key") api_key: String): Call<AddressBean>

    @GET("/v1/btc/{query}/{data}")
    fun getBlockTrailBlockData(@Path(value = "query", encoded = true) query: String, @Path(value = "data", encoded = true) data: String, @Query("api_key") api_key: String): Call<JsonObject>

    @GET("/v1/btc/{query}/{data}")
    fun getBlockTrailTransactionData(@Path(value = "query", encoded = true) query: String, @Path(value = "data", encoded = true) data: String, @Query("api_key") api_key: String): Call<TransactionBean>
}

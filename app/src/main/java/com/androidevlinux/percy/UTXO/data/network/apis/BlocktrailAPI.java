package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.blocktrail.AddressBean;
import com.androidevlinux.percy.UTXO.data.models.blocktrail.TransactionBean;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by percy on 27/11/17.
 */

public interface BlocktrailAPI {
    @GET("/v1/btc/{query}/{data}")
    Call<AddressBean> getBlockTrailAddressData(@Path(value = "query", encoded = true) String query, @Path(value = "data", encoded = true) String data, @Query("api_key") String api_key);

    @GET("/v1/btc/{query}/{data}")
    Call<JsonObject> getBlockTrailBlockData(@Path(value = "query", encoded = true) String query, @Path(value = "data", encoded = true) String data, @Query("api_key") String api_key);

    @GET("/v1/btc/{query}/{data}")
    Call<TransactionBean> getBlockTrailTransactionData(@Path(value = "query", encoded = true) String query, @Path(value = "data", encoded = true) String data, @Query("api_key") String api_key);
}

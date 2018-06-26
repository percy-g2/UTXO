package com.androidevlinux.percy.UTXO.data.network.apis;

import com.androidevlinux.percy.UTXO.data.models.blocktrail.AddressBean;
import com.androidevlinux.percy.UTXO.data.models.blocktrail.TransactionBean;
import com.androidevlinux.percy.UTXO.utils.NativeUtils;
import com.google.gson.JsonObject;

import retrofit2.Callback;

/**
 * Created by percy on 27/11/17.
 */

public class BlocktrailApiImpl extends AbstractBaseApi<BlocktrailAPI> {
    private BlocktrailAPI blocktrailAPI;
    private static BlocktrailApiImpl blocktrailApiImpl;
    private BlocktrailApiImpl(){
        setBaseUrl(NativeUtils.getBlocktrailBaseUrl());
        blocktrailAPI = getClient(BlocktrailAPI.class);
    }

    public static BlocktrailApiImpl getInstance(){
        if(blocktrailApiImpl ==null){
            blocktrailApiImpl = new BlocktrailApiImpl();
        }
        return blocktrailApiImpl;
    }

    public void getBlockTrailAddressData(String query, String data, Callback<AddressBean> callback){
        blocktrailAPI.getBlockTrailAddressData(query, data, NativeUtils.getBlocktrailApiKey()).enqueue(callback);
    }

    public void getBlockTrailBlockData(String query, String data, Callback<JsonObject> callback){
        blocktrailAPI.getBlockTrailBlockData(query, data, NativeUtils.getBlocktrailApiKey()).enqueue(callback);
    }

    public void getBlockTrailTransactionData(String query, String data, Callback<TransactionBean> callback){
        blocktrailAPI.getBlockTrailTransactionData(query, data, NativeUtils.getBlocktrailApiKey()).enqueue(callback);
    }
}

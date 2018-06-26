package com.androidevlinux.percy.UTXO.data.models.coinmarketcap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ryan on 1/16/2018.
 */

public class CoinMarketCapCoin implements Serializable {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

}
package com.androidevlinux.percy.UTXO.data.models.changelly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by percy on 15/11/2017.
 */

public class GetCurrenciesFullResponseBean {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("result")
    @Expose
    private List<CurrenciesFullResultBean> result = null;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CurrenciesFullResultBean> getResult() {
        return result;
    }

    public void setResult(List<CurrenciesFullResultBean> result) {
        this.result = result;
    }
}

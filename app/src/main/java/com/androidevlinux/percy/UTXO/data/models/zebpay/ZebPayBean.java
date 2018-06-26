package com.androidevlinux.percy.UTXO.data.models.zebpay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by percy on 3/12/17.
 */

public class ZebPayBean implements Serializable
{

    @SerializedName("market")
    @Expose
    private Double market;
    @SerializedName("buy")
    @Expose
    private Double buy;
    @SerializedName("sell")
    @Expose
    private Double sell;
    @SerializedName("volume")
    @Expose
    private Double volume;
    @SerializedName("virtualCurrency")
    @Expose
    private String virtualCurrency;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("pair")
    @Expose
    private String pair;
    private final static long serialVersionUID = -4766353430131075747L;

    public Double getMarket() {
        return market;
    }

    public void setMarket(Double market) {
        this.market = market;
    }

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getVirtualCurrency() {
        return virtualCurrency;
    }

    public void setVirtualCurrency(String virtualCurrency) {
        this.virtualCurrency = virtualCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

}
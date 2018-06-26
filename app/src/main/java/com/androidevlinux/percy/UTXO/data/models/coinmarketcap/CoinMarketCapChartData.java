package com.androidevlinux.percy.UTXO.data.models.coinmarketcap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ryan on 2/3/2018.
 */

public class CoinMarketCapChartData implements Serializable
{

    @SerializedName("market_cap_by_available_supply")
    @Expose
    private List<List<Float>> marketCapByAvailableSupply = null;
    @SerializedName("price_btc")
    @Expose
    private List<List<Float>> priceBtc = null;
    @SerializedName("price_usd")
    @Expose
    private List<List<Float>> priceUsd = null;
    @SerializedName("volume_usd")
    @Expose
    private List<List<Float>> volumeUsd = null;
    private final static long serialVersionUID = 4430369643668729100L;

    public List<List<Float>> getMarketCapByAvailableSupply() {
        return marketCapByAvailableSupply;
    }

    public void setMarketCapByAvailableSupply(List<List<Float>> marketCapByAvailableSupply) {
        this.marketCapByAvailableSupply = marketCapByAvailableSupply;
    }

    public CoinMarketCapChartData withMarketCapByAvailableSupply(List<List<Float>> marketCapByAvailableSupply) {
        this.marketCapByAvailableSupply = marketCapByAvailableSupply;
        return this;
    }

    public List<List<Float>> getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(List<List<Float>> priceBtc) {
        this.priceBtc = priceBtc;
    }

    public CoinMarketCapChartData withPriceBtc(List<List<Float>> priceBtc) {
        this.priceBtc = priceBtc;
        return this;
    }

    public List<List<Float>> getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(List<List<Float>> priceUsd) {
        this.priceUsd = priceUsd;
    }

    public CoinMarketCapChartData withPriceUsd(List<List<Float>> priceUsd) {
        this.priceUsd = priceUsd;
        return this;
    }

    public List<List<Float>> getVolumeUsd() {
        return volumeUsd;
    }

    public void setVolumeUsd(List<List<Float>> volumeUsd) {
        this.volumeUsd = volumeUsd;
    }

    public CoinMarketCapChartData withVolumeUsd(List<List<Float>> volumeUsd) {
        this.volumeUsd = volumeUsd;
        return this;
    }

}


package com.androidevlinux.percy.UTXO.data.models.gdax;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GDAX implements Serializable
{

    @SerializedName("trade_id")
    @Expose
    private int tradeId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("bid")
    @Expose
    private String bid;
    @SerializedName("ask")
    @Expose
    private String ask;
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("time")
    @Expose
    private String time;
    private final static long serialVersionUID = 4130765474459101813L;

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public GDAX withTradeId(int tradeId) {
        this.tradeId = tradeId;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public GDAX withPrice(String price) {
        this.price = price;
        return this;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public GDAX withSize(String size) {
        this.size = size;
        return this;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public GDAX withBid(String bid) {
        this.bid = bid;
        return this;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public GDAX withAsk(String ask) {
        this.ask = ask;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public GDAX withVolume(String volume) {
        this.volume = volume;
        return this;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public GDAX withTime(String time) {
        this.time = time;
        return this;
    }

}

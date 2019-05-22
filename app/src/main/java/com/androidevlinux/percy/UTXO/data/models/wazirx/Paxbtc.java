
package com.androidevlinux.percy.UTXO.data.models.wazirx;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paxbtc {

    @SerializedName("base_unit")
    @Expose
    private String baseUnit;
    @SerializedName("quote_unit")
    @Expose
    private String quoteUnit;
    @SerializedName("low")
    @Expose
    private String low;
    @SerializedName("high")
    @Expose
    private String high;
    @SerializedName("last")
    @Expose
    private String last;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("open")
    @Expose
    private String open;
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("sell")
    @Expose
    private String sell;
    @SerializedName("buy")
    @Expose
    private String buy;
    @SerializedName("at")
    @Expose
    private int at;
    @SerializedName("name")
    @Expose
    private String name;

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public Paxbtc withBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
        return this;
    }

    public String getQuoteUnit() {
        return quoteUnit;
    }

    public void setQuoteUnit(String quoteUnit) {
        this.quoteUnit = quoteUnit;
    }

    public Paxbtc withQuoteUnit(String quoteUnit) {
        this.quoteUnit = quoteUnit;
        return this;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public Paxbtc withLow(String low) {
        this.low = low;
        return this;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public Paxbtc withHigh(String high) {
        this.high = high;
        return this;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Paxbtc withLast(String last) {
        this.last = last;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Paxbtc withType(String type) {
        this.type = type;
        return this;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public Paxbtc withOpen(String open) {
        this.open = open;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Paxbtc withVolume(String volume) {
        this.volume = volume;
        return this;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public Paxbtc withSell(String sell) {
        this.sell = sell;
        return this;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public Paxbtc withBuy(String buy) {
        this.buy = buy;
        return this;
    }

    public int getAt() {
        return at;
    }

    public void setAt(int at) {
        this.at = at;
    }

    public Paxbtc withAt(int at) {
        this.at = at;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Paxbtc withName(String name) {
        this.name = name;
        return this;
    }

}

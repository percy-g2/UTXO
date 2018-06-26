package com.androidevlinux.percy.UTXO.data.models.pocketbits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Altcoin implements Serializable
{

    @SerializedName("AltName")
    @Expose
    private String altName;
    @SerializedName("AltBuyPrice")
    @Expose
    private double altBuyPrice;
    @SerializedName("AltSellPrice")
    @Expose
    private double altSellPrice;
    private final static long serialVersionUID = 1969951576474414747L;

    public String getAltName() {
        return altName;
    }

    public void setAltName(String altName) {
        this.altName = altName;
    }

    public Altcoin withAltName(String altName) {
        this.altName = altName;
        return this;
    }

    public double getAltBuyPrice() {
        return altBuyPrice;
    }

    public void setAltBuyPrice(double altBuyPrice) {
        this.altBuyPrice = altBuyPrice;
    }

    public Altcoin withAltBuyPrice(double altBuyPrice) {
        this.altBuyPrice = altBuyPrice;
        return this;
    }

    public double getAltSellPrice() {
        return altSellPrice;
    }

    public void setAltSellPrice(double altSellPrice) {
        this.altSellPrice = altSellPrice;
    }

    public Altcoin withAltSellPrice(double altSellPrice) {
        this.altSellPrice = altSellPrice;
        return this;
    }

}

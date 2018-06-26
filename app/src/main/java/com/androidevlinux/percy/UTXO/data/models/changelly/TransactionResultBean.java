package com.androidevlinux.percy.UTXO.data.models.changelly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by percy on 15/11/2017.
 */

public class TransactionResultBean {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("apiExtraFee")
    @Expose
    private String apiExtraFee;
    @SerializedName("changellyFee")
    @Expose
    private String changellyFee;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("currencyFrom")
    @Expose
    private String currencyFrom;
    @SerializedName("currencyTo")
    @Expose
    private String currencyTo;
    @SerializedName("amountTo")
    @Expose
    private Integer amountTo;
    @SerializedName("payinAddress")
    @Expose
    private String payinAddress;
    @SerializedName("payoutAddress")
    @Expose
    private String payoutAddress;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiExtraFee() {
        return apiExtraFee;
    }

    public void setApiExtraFee(String apiExtraFee) {
        this.apiExtraFee = apiExtraFee;
    }

    public String getChangellyFee() {
        return changellyFee;
    }

    public void setChangellyFee(String changellyFee) {
        this.changellyFee = changellyFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public Integer getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(Integer amountTo) {
        this.amountTo = amountTo;
    }

    public String getPayinAddress() {
        return payinAddress;
    }

    public void setPayinAddress(String payinAddress) {
        this.payinAddress = payinAddress;
    }

    public String getPayoutAddress() {
        return payoutAddress;
    }

    public void setPayoutAddress(String payoutAddress) {
        this.payoutAddress = payoutAddress;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

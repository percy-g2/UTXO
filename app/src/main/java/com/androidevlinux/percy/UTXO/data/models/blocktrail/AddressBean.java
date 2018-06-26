package com.androidevlinux.percy.UTXO.data.models.blocktrail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressBean implements Serializable
{

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("hash160")
    @Expose
    private String hash160;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("received")
    @Expose
    private Integer received;
    @SerializedName("sent")
    @Expose
    private Integer sent;
    @SerializedName("transactions")
    @Expose
    private Integer transactions;
    @SerializedName("utxos")
    @Expose
    private Integer utxos;
    @SerializedName("unconfirmed_received")
    @Expose
    private Integer unconfirmedReceived;
    @SerializedName("unconfirmed_sent")
    @Expose
    private Integer unconfirmedSent;
    @SerializedName("unconfirmed_transactions")
    @Expose
    private Integer unconfirmedTransactions;
    @SerializedName("unconfirmed_utxos")
    @Expose
    private Integer unconfirmedUtxos;
    @SerializedName("total_transactions_in")
    @Expose
    private Integer totalTransactionsIn;
    @SerializedName("total_transactions_out")
    @Expose
    private Integer totalTransactionsOut;
    @SerializedName("category")
    @Expose
    private Object category;
    @SerializedName("tag")
    @Expose
    private Object tag;
    @SerializedName("first_seen")
    @Expose
    private Object firstSeen;
    @SerializedName("last_seen")
    @Expose
    private Object lastSeen;
    private final static long serialVersionUID = 1204703229998514129L;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHash160() {
        return hash160;
    }

    public void setHash160(String hash160) {
        this.hash160 = hash160;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getReceived() {
        return received;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }

    public Integer getSent() {
        return sent;
    }

    public void setSent(Integer sent) {
        this.sent = sent;
    }

    public Integer getTransactions() {
        return transactions;
    }

    public void setTransactions(Integer transactions) {
        this.transactions = transactions;
    }

    public Integer getUtxos() {
        return utxos;
    }

    public void setUtxos(Integer utxos) {
        this.utxos = utxos;
    }

    public Integer getUnconfirmedReceived() {
        return unconfirmedReceived;
    }

    public void setUnconfirmedReceived(Integer unconfirmedReceived) {
        this.unconfirmedReceived = unconfirmedReceived;
    }

    public Integer getUnconfirmedSent() {
        return unconfirmedSent;
    }

    public void setUnconfirmedSent(Integer unconfirmedSent) {
        this.unconfirmedSent = unconfirmedSent;
    }

    public Integer getUnconfirmedTransactions() {
        return unconfirmedTransactions;
    }

    public void setUnconfirmedTransactions(Integer unconfirmedTransactions) {
        this.unconfirmedTransactions = unconfirmedTransactions;
    }

    public Integer getUnconfirmedUtxos() {
        return unconfirmedUtxos;
    }

    public void setUnconfirmedUtxos(Integer unconfirmedUtxos) {
        this.unconfirmedUtxos = unconfirmedUtxos;
    }

    public Integer getTotalTransactionsIn() {
        return totalTransactionsIn;
    }

    public void setTotalTransactionsIn(Integer totalTransactionsIn) {
        this.totalTransactionsIn = totalTransactionsIn;
    }

    public Integer getTotalTransactionsOut() {
        return totalTransactionsOut;
    }

    public void setTotalTransactionsOut(Integer totalTransactionsOut) {
        this.totalTransactionsOut = totalTransactionsOut;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Object getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(Object firstSeen) {
        this.firstSeen = firstSeen;
    }

    public Object getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Object lastSeen) {
        this.lastSeen = lastSeen;
    }

}

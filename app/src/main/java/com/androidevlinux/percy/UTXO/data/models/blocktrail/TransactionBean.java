package com.androidevlinux.percy.UTXO.data.models.blocktrail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TransactionBean implements Serializable
{

    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("first_seen_at")
    @Expose
    private String firstSeenAt;
    @SerializedName("last_seen_at")
    @Expose
    private String lastSeenAt;
    @SerializedName("block_height")
    @Expose
    private Integer blockHeight;
    @SerializedName("block_time")
    @Expose
    private String blockTime;
    @SerializedName("block_hash")
    @Expose
    private String blockHash;
    @SerializedName("confirmations")
    @Expose
    private Integer confirmations;
    @SerializedName("is_coinbase")
    @Expose
    private Boolean isCoinbase;
    @SerializedName("estimated_value")
    @Expose
    private Integer estimatedValue;
    @SerializedName("total_input_value")
    @Expose
    private Integer totalInputValue;
    @SerializedName("total_output_value")
    @Expose
    private Integer totalOutputValue;
    @SerializedName("total_fee")
    @Expose
    private Integer totalFee;
    @SerializedName("estimated_change")
    @Expose
    private Integer estimatedChange;
    @SerializedName("estimated_change_address")
    @Expose
    private String estimatedChangeAddress;
    @SerializedName("high_priority")
    @Expose
    private Boolean highPriority;
    @SerializedName("enough_fee")
    @Expose
    private Boolean enoughFee;
    @SerializedName("contains_dust")
    @Expose
    private Boolean containsDust;
    @SerializedName("inputs")
    @Expose
    private List<InputBean> inputs = null;
    @SerializedName("outputs")
    @Expose
    private List<OutputBean> outputs = null;
    private final static long serialVersionUID = -3994899921959599678L;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getFirstSeenAt() {
        return firstSeenAt;
    }

    public void setFirstSeenAt(String firstSeenAt) {
        this.firstSeenAt = firstSeenAt;
    }

    public String getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(String lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    public Integer getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Integer blockHeight) {
        this.blockHeight = blockHeight;
    }

    public String getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(String blockTime) {
        this.blockTime = blockTime;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public Boolean getIsCoinbase() {
        return isCoinbase;
    }

    public void setIsCoinbase(Boolean isCoinbase) {
        this.isCoinbase = isCoinbase;
    }

    public Integer getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(Integer estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public Integer getTotalInputValue() {
        return totalInputValue;
    }

    public void setTotalInputValue(Integer totalInputValue) {
        this.totalInputValue = totalInputValue;
    }

    public Integer getTotalOutputValue() {
        return totalOutputValue;
    }

    public void setTotalOutputValue(Integer totalOutputValue) {
        this.totalOutputValue = totalOutputValue;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getEstimatedChange() {
        return estimatedChange;
    }

    public void setEstimatedChange(Integer estimatedChange) {
        this.estimatedChange = estimatedChange;
    }

    public String getEstimatedChangeAddress() {
        return estimatedChangeAddress;
    }

    public void setEstimatedChangeAddress(String estimatedChangeAddress) {
        this.estimatedChangeAddress = estimatedChangeAddress;
    }

    public Boolean getHighPriority() {
        return highPriority;
    }

    public void setHighPriority(Boolean highPriority) {
        this.highPriority = highPriority;
    }

    public Boolean getEnoughFee() {
        return enoughFee;
    }

    public void setEnoughFee(Boolean enoughFee) {
        this.enoughFee = enoughFee;
    }

    public Boolean getContainsDust() {
        return containsDust;
    }

    public void setContainsDust(Boolean containsDust) {
        this.containsDust = containsDust;
    }

    public List<InputBean> getInputs() {
        return inputs;
    }

    public void setInputs(List<InputBean> inputs) {
        this.inputs = inputs;
    }

    public List<OutputBean> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<OutputBean> outputs) {
        this.outputs = outputs;
    }

}
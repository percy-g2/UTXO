package com.androidevlinux.percy.UTXO.data.models.blocktrail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InputBean implements Serializable
{

    @SerializedName("index")
    @Expose
    private Integer index;
    @SerializedName("output_hash")
    @Expose
    private String outputHash;
    @SerializedName("output_index")
    @Expose
    private Integer outputIndex;
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("multisig")
    @Expose
    private Object multisig;
    @SerializedName("script_signature")
    @Expose
    private String scriptSignature;
    private final static long serialVersionUID = 2294763987680187324L;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getOutputHash() {
        return outputHash;
    }

    public void setOutputHash(String outputHash) {
        this.outputHash = outputHash;
    }

    public Integer getOutputIndex() {
        return outputIndex;
    }

    public void setOutputIndex(Integer outputIndex) {
        this.outputIndex = outputIndex;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getMultisig() {
        return multisig;
    }

    public void setMultisig(Object multisig) {
        this.multisig = multisig;
    }

    public String getScriptSignature() {
        return scriptSignature;
    }

    public void setScriptSignature(String scriptSignature) {
        this.scriptSignature = scriptSignature;
    }

}
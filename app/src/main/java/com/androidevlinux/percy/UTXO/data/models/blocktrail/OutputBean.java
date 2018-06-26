package com.androidevlinux.percy.UTXO.data.models.blocktrail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OutputBean implements Serializable
{

    @SerializedName("index")
    @Expose
    private Integer index;
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
    @SerializedName("script")
    @Expose
    private String script;
    @SerializedName("script_hex")
    @Expose
    private String scriptHex;
    @SerializedName("spent_hash")
    @Expose
    private String spentHash;
    @SerializedName("spent_index")
    @Expose
    private Integer spentIndex;
    private final static long serialVersionUID = -3389673860598109179L;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScriptHex() {
        return scriptHex;
    }

    public void setScriptHex(String scriptHex) {
        this.scriptHex = scriptHex;
    }

    public String getSpentHash() {
        return spentHash;
    }

    public void setSpentHash(String spentHash) {
        this.spentHash = spentHash;
    }

    public Integer getSpentIndex() {
        return spentIndex;
    }

    public void setSpentIndex(Integer spentIndex) {
        this.spentIndex = spentIndex;
    }

}
package com.androidevlinux.percy.UTXO.data.models.pocketbits;

/**
 * Created by percy on 20/1/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PocketBitsBean implements Serializable
{

    @SerializedName("Altcoins")
    @Expose
    private List<Altcoin> altcoins = null;
    private final static long serialVersionUID = 1967121703865114065L;

    public List<Altcoin> getAltcoins() {
        return altcoins;
    }

    public void setAltcoins(List<Altcoin> altcoins) {
        this.altcoins = altcoins;
    }

    public PocketBitsBean withAltcoins(List<Altcoin> altcoins) {
        this.altcoins = altcoins;
        return this;
    }

}
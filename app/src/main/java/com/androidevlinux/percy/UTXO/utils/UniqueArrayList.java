package com.androidevlinux.percy.UTXO.utils;


import com.androidevlinux.percy.UTXO.data.models.price.PriceBean;

import java.util.ArrayList;

public class UniqueArrayList extends ArrayList<PriceBean> {
    /**
     * Only add the object if there is not
     * another copy of it in the list
     */
    @Override
    public boolean add(PriceBean priceBean) {
        return priceBean.getTitle().matches(".*" + Constants.currentCurrency + ".*") && super.add(priceBean);
    }
}

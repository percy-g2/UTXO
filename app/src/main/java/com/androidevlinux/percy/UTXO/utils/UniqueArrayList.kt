package com.androidevlinux.percy.UTXO.utils


import com.androidevlinux.percy.UTXO.data.models.price.PriceBean
import java.util.*

class UniqueArrayList : ArrayList<PriceBean>() {
    /**
     * Only add the object if there is not
     * another copy of it in the list
     */
    override fun add(element: PriceBean): Boolean {
        return element.title.matches((".*" + Constants.currentCurrency + ".*").toRegex()) && super.add(element)
    }
}

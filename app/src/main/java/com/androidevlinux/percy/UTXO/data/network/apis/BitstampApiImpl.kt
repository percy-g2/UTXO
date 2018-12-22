package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.bitstamp.BitstampBean
import com.androidevlinux.percy.UTXO.utils.NativeUtils

import io.reactivex.Observable

/**
 * Created by percy on 5/12/17.
 */

class BitstampApiImpl private constructor() : AbstractBaseApi<BitstampAPI>() {
    private val bitstampAPI: BitstampAPI

    init {
        setBaseUrl(NativeUtils.bitStampBaseUrl)
        bitstampAPI = getClient(BitstampAPI::class.java)

    }

    fun getBitstampTicker(symbol: String): Observable<BitstampBean> {
        return bitstampAPI.getBitstampTicker(symbol)
    }

    companion object {

        private var bitstampApiManager: BitstampApiImpl? = null

        val instance: BitstampApiImpl
            get() {
                if (bitstampApiManager == null)
                    bitstampApiManager = BitstampApiImpl()
                return bitstampApiManager!!
            }
    }
}

package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.bitmex.BitMEXTickerBean
import com.androidevlinux.percy.UTXO.utils.NativeUtils
import io.reactivex.Observable

class BitmexApiImpl private constructor() : AbstractBaseApi<BitmexAPI>() {
    private val bitmexAPI: BitmexAPI

    init {
        setBaseUrl(NativeUtils.bitmexBaseUrl)
        bitmexAPI = getClient(BitmexAPI::class.java)

    }

    fun getBitmexTicker(): Observable<List<BitMEXTickerBean>> {
        return bitmexAPI.getBitmexTicker()
    }

    companion object {

        private var bitmexAPIApiManager: BitmexApiImpl? = null

        val instance: BitmexApiImpl
            get() {
                if (bitmexAPIApiManager == null)
                    bitmexAPIApiManager = BitmexApiImpl()
                return bitmexAPIApiManager!!
            }
    }
}
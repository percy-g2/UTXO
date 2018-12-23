package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.okex.OkexTickerBean
import com.androidevlinux.percy.UTXO.utils.NativeUtils
import io.reactivex.Observable

class OkexApiImpl private constructor() : AbstractBaseApi<OkexAPI>() {
    private val okexAPI: OkexAPI

    init {
        setBaseUrl(NativeUtils.okexBaseUrl)
        okexAPI = getClient(OkexAPI::class.java)

    }

    fun getOkexTicker(symbol: String): Observable<OkexTickerBean> {
        return okexAPI.getOkexTicker(symbol)
    }

    companion object {

        private var okexAPIApiManager: OkexApiImpl? = null

        val instance: OkexApiImpl
            get() {
                if (okexAPIApiManager == null)
                    okexAPIApiManager = OkexApiImpl()
                return okexAPIApiManager!!
            }
    }
}
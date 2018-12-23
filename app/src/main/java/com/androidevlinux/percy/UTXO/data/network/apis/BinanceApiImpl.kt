package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.binance.BinanceApiTickerBean
import com.androidevlinux.percy.UTXO.utils.NativeUtils
import io.reactivex.Observable

class BinanceApiImpl private constructor() : AbstractBaseApi<BinanceAPI>() {
    private val binanceAPI: BinanceAPI

    init {
        setBaseUrl(NativeUtils.binanceBaseUrl)
        binanceAPI = getClient(BinanceAPI::class.java)

    }

    fun getBinanceTicker(symbol: String): Observable<BinanceApiTickerBean> {
        return binanceAPI.getBinanceTicker(symbol)
    }

    companion object {

        private var binanceAPIApiManager: BinanceApiImpl? = null

        val instance: BinanceApiImpl
            get() {
                if (binanceAPIApiManager == null)
                    binanceAPIApiManager = BinanceApiImpl()
                return binanceAPIApiManager!!
            }
    }
}
package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapChartData
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapCoin
import com.androidevlinux.percy.UTXO.utils.NativeUtils

import io.reactivex.Observable

class CoinMarketCapApiImpl private constructor() : AbstractBaseApi<CoinMarketCapAPI>() {
    private val coinMarketCapAPI: CoinMarketCapAPI

    init {
        setBaseUrl(NativeUtils.getCoinMarketCapBaseUrl())
        coinMarketCapAPI = getClient(CoinMarketCapAPI::class.java)
    }

    fun getChartData(url: String): Observable<CoinMarketCapChartData> {
        return coinMarketCapAPI.getChartData(url)
    }

    fun getChartFooterData(currency: String): Observable<CoinMarketCapCoin> {
        return coinMarketCapAPI.getChartFooterData(currency)
    }

    companion object {
        private var coinMarketCapApiImpl: CoinMarketCapApiImpl? = null

        val instance: CoinMarketCapApiImpl
            get() {
                if (coinMarketCapApiImpl == null) {
                    coinMarketCapApiImpl = CoinMarketCapApiImpl()
                }
                return coinMarketCapApiImpl!!
            }
    }
}

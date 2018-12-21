package com.androidevlinux.percy.UTXO.data.network

import com.androidevlinux.percy.UTXO.data.models.bitfinex.BitfinexPubTickerResponseBean
import com.androidevlinux.percy.UTXO.data.models.bitstamp.BitstampBean
import com.androidevlinux.percy.UTXO.data.models.blocktrail.AddressBean
import com.androidevlinux.percy.UTXO.data.models.blocktrail.TransactionBean
import com.androidevlinux.percy.UTXO.data.models.changelly.GetCurrenciesResponseBean
import com.androidevlinux.percy.UTXO.data.models.changelly.GetMinAmountReponseBean
import com.androidevlinux.percy.UTXO.data.models.changelly.MainBodyBean
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapChartData
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapCoin
import com.androidevlinux.percy.UTXO.data.models.gdax.GDAX
import com.androidevlinux.percy.UTXO.data.models.newsapi.NewsBean
import com.androidevlinux.percy.UTXO.data.models.pocketbits.PocketBitsBean
import com.androidevlinux.percy.UTXO.data.network.apis.*
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Callback

class ApiManager private constructor() {
    private val bitfinexApiImpl: BitfinexApiImpl = BitfinexApiImpl.instance
    private val bitstampApiImpl: BitstampApiImpl = BitstampApiImpl.instance
    private val blocktrailApiImpl: BlocktrailApiImpl = BlocktrailApiImpl.instance
    private val changellyApiImpl: ChangellyApiImpl = ChangellyApiImpl.instance
    private val pocketbitsApiImpl: PocketbitsApiImpl = PocketbitsApiImpl.instance
    private val gdaxApiImpl: GdaxApiImpl = GdaxApiImpl.instance
    private val coinMarketCapApiImpl: CoinMarketCapApiImpl = CoinMarketCapApiImpl.instance
    private val newsApiImpl: NewsApiImpl = NewsApiImpl.instance

    val pocketbitsTicker: Observable<PocketBitsBean>
        get() = pocketbitsApiImpl.pocketbitsTicker

    fun getChartData(url: String): Observable<CoinMarketCapChartData> {
        return coinMarketCapApiImpl.getChartData(url)
    }

    fun getChartFooterData(currency: String): Observable<CoinMarketCapCoin> {
        return coinMarketCapApiImpl.getChartFooterData(currency)
    }

    fun getData(symbol: String): Observable<GDAX> {
        return gdaxApiImpl.getData(symbol)
    }

    fun getBitfinexTicker(symbol: String): Observable<BitfinexPubTickerResponseBean> {
        return bitfinexApiImpl.getBitfinexTicker(symbol)
    }

    fun getBitfinexData(time: String, symbol: String, callback: Callback<ResponseBody>) {
        bitfinexApiImpl.getBitfinexData(time, symbol, callback)
    }

    fun getBitstampTicker(symbol: String): Observable<BitstampBean> {
        return bitstampApiImpl.getBitstampTicker(symbol)
    }

    fun getBlockTrailAddressData(query: String, data: String, callback: Callback<AddressBean>) {
        blocktrailApiImpl.getBlockTrailAddressData(query, data, callback)
    }

    fun getBlockTrailBlockData(query: String, data: String, callback: Callback<JsonObject>) {
        blocktrailApiImpl.getBlockTrailBlockData(query, data, callback)
    }

    fun getBlockTrailTransactionData(query: String, data: String, callback: Callback<TransactionBean>) {
        blocktrailApiImpl.getBlockTrailTransactionData(query, data, callback)
    }

    fun getCurrencies(sign: String, body: MainBodyBean, callback: Callback<GetCurrenciesResponseBean>) {
        changellyApiImpl.getCurrencies(sign, body, callback)
    }

    fun getMinAmount(sign: String, body: MainBodyBean, callback: Callback<GetMinAmountReponseBean>) {
        changellyApiImpl.getMinAmount(sign, body, callback)
    }

    fun createTransaction(sign: String, body: MainBodyBean, callback: Callback<com.androidevlinux.percy.UTXO.data.models.changelly.TransactionBean>) {
        changellyApiImpl.createTransaction(sign, body, callback)
    }

    fun getNewsData(apiKey: String): Observable<NewsBean> {
        return newsApiImpl.getNewsData(apiKey)
    }

    companion object {

        private var apiManager: ApiManager? = null

        val instance: ApiManager
            get() {
                if (apiManager == null) {
                    apiManager = ApiManager()
                }
                return apiManager!!
            }
    }
}

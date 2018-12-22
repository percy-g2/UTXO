package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.bitfinex.BitfinexPubTickerResponseBean
import com.androidevlinux.percy.UTXO.utils.NativeUtils

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Callback

/**
 * Created by percy on 18/11/17.
 */

class BitfinexApiImpl private constructor() : AbstractBaseApi<BitfinexAPI>() {
    private val bitfinexAPI: BitfinexAPI

    init {
        setBaseUrl(NativeUtils.bitfinexBaseUrl)
        bitfinexAPI = getClient(BitfinexAPI::class.java)

    }

    fun getBitfinexTicker(symbol: String): Observable<BitfinexPubTickerResponseBean> {
        return bitfinexAPI.getBitfinexTicker(symbol)
    }

    fun getBitfinexData(time: String, symbol: String, callback: Callback<ResponseBody>) {
        bitfinexAPI.getBitfinexData(time, symbol).enqueue(callback)
    }

    companion object {

        private var bitfinexApiManager: BitfinexApiImpl? = null

        val instance: BitfinexApiImpl
            get() {
                if (bitfinexApiManager == null)
                    bitfinexApiManager = BitfinexApiImpl()
                return bitfinexApiManager!!
            }
    }
}

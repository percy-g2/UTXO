package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.gdax.GDAX
import com.androidevlinux.percy.UTXO.utils.NativeUtils

import io.reactivex.Observable

class GdaxApiImpl private constructor() : AbstractBaseApi<GdaxAPI>() {
    private val gdaxAPI: GdaxAPI

    init {
        setBaseUrl(NativeUtils.getGdaxBaseUrl())
        gdaxAPI = getClient(GdaxAPI::class.java)

    }

    fun getData(symbol: String): Observable<GDAX> {
        return gdaxAPI.getData(symbol)
    }

    companion object {

        private var gdaxApiManager: GdaxApiImpl? = null

        val instance: GdaxApiImpl
            get() {
                if (gdaxApiManager == null)
                    gdaxApiManager = GdaxApiImpl()
                return gdaxApiManager!!
            }
    }
}

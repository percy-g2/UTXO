package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.changelly.GetCurrenciesResponseBean
import com.androidevlinux.percy.UTXO.data.models.changelly.GetMinAmountReponseBean
import com.androidevlinux.percy.UTXO.data.models.changelly.MainBodyBean
import com.androidevlinux.percy.UTXO.data.models.changelly.TransactionBean
import com.androidevlinux.percy.UTXO.utils.NativeUtils
import retrofit2.Callback

/**
 * Created by percy on 14/11/2017.
 */

class ChangellyApiImpl private constructor() : AbstractBaseApi<ChangellyAPI>() {
    private val changellyAPI: ChangellyAPI

    init {
        setBaseUrl(NativeUtils.changellyBaseUrl)
        changellyAPI = getClient(ChangellyAPI::class.java)
    }

    fun getCurrencies(sign: String, body: MainBodyBean, callback: Callback<GetCurrenciesResponseBean>) {
        changellyAPI.getCurrencies(contentType, NativeUtils.changellyApiKey, sign, body).enqueue(callback)
    }

    fun getMinAmount(sign: String, body: MainBodyBean, callback: Callback<GetMinAmountReponseBean>) {
        changellyAPI.getMinAmount(contentType, NativeUtils.changellyApiKey, sign, body).enqueue(callback)
    }

    fun createTransaction(sign: String, body: MainBodyBean, callback: Callback<TransactionBean>) {
        changellyAPI.createTransaction(contentType, NativeUtils.changellyApiKey, sign, body).enqueue(callback)
    }

    companion object {
        private var changellyApiImpl: ChangellyApiImpl? = null

        val instance: ChangellyApiImpl
            get() {
                if (changellyApiImpl == null) {
                    changellyApiImpl = ChangellyApiImpl()
                }
                return changellyApiImpl!!
            }
    }
}


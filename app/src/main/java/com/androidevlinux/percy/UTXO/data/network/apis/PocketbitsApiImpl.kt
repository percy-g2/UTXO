package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.pocketbits.PocketBitsBean
import com.androidevlinux.percy.UTXO.utils.NativeUtils

import io.reactivex.Observable

/**
 * Created by percy on 20/1/18.
 */

class PocketbitsApiImpl private constructor() : AbstractBaseApi<PocketbitsAPI>() {
    private val pocketbitsAPI: PocketbitsAPI
    val pocketbitsTicker: Observable<PocketBitsBean>
        get() = pocketbitsAPI.pocketbitsTicker

    init {
        setBaseUrl(NativeUtils.getPocketbitsBaseUrl())
        pocketbitsAPI = getClient(PocketbitsAPI::class.java)

    }

    companion object {

        private var pocketbitsApiManager: PocketbitsApiImpl? = null

        val instance: PocketbitsApiImpl
            get() {
                if (pocketbitsApiManager == null)
                    pocketbitsApiManager = PocketbitsApiImpl()
                return pocketbitsApiManager!!
            }
    }
}
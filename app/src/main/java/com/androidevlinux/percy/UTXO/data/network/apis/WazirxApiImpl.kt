package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.wazirx.Wazirx
import com.androidevlinux.percy.UTXO.utils.NativeUtils
import io.reactivex.Observable

class WazirxApiImpl private constructor() : AbstractBaseApi<WazirxAPI>() {
    private val wazirxAPI: WazirxAPI
    val wazirxTicker: Observable<Wazirx>
        get() = wazirxAPI.wazirxTicker

    init {
        setBaseUrl(NativeUtils.wazirxBaseUrl)
        wazirxAPI = getClient(WazirxAPI::class.java)

    }

    companion object {

        private var wazirxApiManager: WazirxApiImpl? = null

        val instance: WazirxApiImpl
            get() {
                if (wazirxApiManager == null)
                    wazirxApiManager = WazirxApiImpl()
                return wazirxApiManager!!
            }
    }
}

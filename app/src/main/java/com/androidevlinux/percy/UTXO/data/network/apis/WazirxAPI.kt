package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.wazirx.Wazirx
import io.reactivex.Observable
import retrofit2.http.GET

interface WazirxAPI {

    @get:GET("/api/v2/tickers")
    val wazirxTicker: Observable<Wazirx>
}

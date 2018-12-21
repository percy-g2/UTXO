package com.androidevlinux.percy.UTXO.data.network.apis

import com.androidevlinux.percy.UTXO.data.models.blocktrail.AddressBean
import com.androidevlinux.percy.UTXO.data.models.blocktrail.TransactionBean
import com.androidevlinux.percy.UTXO.utils.NativeUtils
import com.google.gson.JsonObject

import retrofit2.Callback

/**
 * Created by percy on 27/11/17.
 */

class BlocktrailApiImpl private constructor() : AbstractBaseApi<BlocktrailAPI>() {
    private val blocktrailAPI: BlocktrailAPI

    init {
        setBaseUrl(NativeUtils.getBlocktrailBaseUrl())
        blocktrailAPI = getClient(BlocktrailAPI::class.java)
    }

    fun getBlockTrailAddressData(query: String, data: String, callback: Callback<AddressBean>) {
        blocktrailAPI.getBlockTrailAddressData(query, data, NativeUtils.getBlocktrailApiKey()).enqueue(callback)
    }

    fun getBlockTrailBlockData(query: String, data: String, callback: Callback<JsonObject>) {
        blocktrailAPI.getBlockTrailBlockData(query, data, NativeUtils.getBlocktrailApiKey()).enqueue(callback)
    }

    fun getBlockTrailTransactionData(query: String, data: String, callback: Callback<TransactionBean>) {
        blocktrailAPI.getBlockTrailTransactionData(query, data, NativeUtils.getBlocktrailApiKey()).enqueue(callback)
    }

    companion object {
        private var blocktrailApiImpl: BlocktrailApiImpl? = null

        val instance: BlocktrailApiImpl
            get() {
                if (blocktrailApiImpl == null) {
                    blocktrailApiImpl = BlocktrailApiImpl()
                }
                return blocktrailApiImpl!!
            }
    }
}

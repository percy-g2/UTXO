package com.androidevlinux.percy.UTXO.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Created by percy on 15/11/2017.
 */

object Utils {

    fun hmacDigest(msg: String, keyString: String): String? {
        val algo = "HmacSHA512"
        var digest: String? = null
        val tag = "Utils"
        try {
            val key = SecretKeySpec(keyString.toByteArray(charset("UTF-8")), algo)
            val mac = Mac.getInstance(algo)
            mac.init(key)
            val bytes = mac.doFinal(msg.toByteArray(charset("ASCII")))
            val hash = StringBuilder()
            for (aByte in bytes) {
                val hex = Integer.toHexString(0xFF and aByte.toInt())
                if (hex.length == 1) {
                    hash.append('0')
                }
                hash.append(hex)
            }
            digest = hash.toString()
        } catch (e: Throwable) {
            Log.e(tag, e.message)
        } catch (e: InvalidKeyException) {
            Log.e(tag, e.message)
        } catch (e: NoSuchAlgorithmException) {
            Log.e(tag, e.message)
        }

        return digest
    }

    fun isConnectingToInternet(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}

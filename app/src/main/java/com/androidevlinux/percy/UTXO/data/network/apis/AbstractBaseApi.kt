package com.androidevlinux.percy.UTXO.data.network.apis

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by percy on 22/11/17.
 */


abstract class AbstractBaseApi<T> {

    private val gson = GsonBuilder()
            .setLenient()
            .create()
    private val logging = HttpLoggingInterceptor()
    private var baseUrl: String? = null
    private var retrofit: Retrofit? = null

    val contentType = "application/json"

    fun setBaseUrl(url: String) {
        baseUrl = url
    }

    fun getClient(t: Class<T>): T {
        if (retrofit == null) {
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY

            val clientOkHttp = OkHttpClient.Builder()
                    .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                    .readTimeout(readTimeOut, TimeUnit.SECONDS).addInterceptor(logging)
                    .addInterceptor { chain ->
                        val requestBuilder = chain.request().newBuilder()
                        requestBuilder.header("Content-Type", "application/json")
                        chain.proceed(requestBuilder.build())
                    }
                    .build()

            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl!!)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(clientOkHttp)
                    .build()
        }
        return retrofit!!.create(t)
    }

    companion object {
        private const val connectTimeOut: Long = 120
        private const val writeTimeOut: Long = 120
        private const val readTimeOut: Long = 120
    }
}


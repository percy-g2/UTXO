package com.androidevlinux.percy.UTXO.fragments

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.adapters.GlobalPriceAdapter
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapCoin
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_crypto_prices.*
import java.util.ArrayList
import kotlin.Comparator

class GlobalCryptoPricesFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private var coinMarketCapCoinObservable: Observable<CoinMarketCapCoin>? = null
    private var disposables: CompositeDisposable? = null
    private var coinMarketCapCoinArrayList: ArrayList<CoinMarketCapCoin>? = null
    private var globalPriceAdapter: GlobalPriceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_crypto_prices, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coinMarketCapCoinArrayList = ArrayList()
        val linearLayoutManager = LinearLayoutManager(activity)
        price_list_recycler_view!!.layoutManager = linearLayoutManager
        globalPriceAdapter = GlobalPriceAdapter(coinMarketCapCoinArrayList!!, mActivity!!, price_list_recycler_view!!, swipe_container!!)
        price_list_recycler_view!!.adapter = globalPriceAdapter
        swipe_container!!.setOnRefreshListener(this)
        swipe_container!!.setColorSchemeColors(ContextCompat.getColor(mActivity!!, android.R.color.holo_green_dark),
                ContextCompat.getColor(mActivity!!, android.R.color.holo_red_dark),
                ContextCompat.getColor(mActivity!!, android.R.color.holo_blue_dark),
                ContextCompat.getColor(mActivity!!, android.R.color.holo_orange_dark))
        RefreshTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    override fun onResume() {
        super.onResume()
        mActivity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (disposables != null) {
            disposables!!.dispose()
        }
    }

    override fun onRefresh() {
        disposables!!.dispose()
        RefreshTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    private fun getData(currencyId: String) {
        coinMarketCapCoinObservable = apiManager!!.getChartFooterData(currencyId)
        disposables = CompositeDisposable()
        disposables!!.add(coinMarketCapCoinObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<CoinMarketCapCoin>() {

                    override fun onNext(value: CoinMarketCapCoin) {
                        coinMarketCapCoinArrayList!!.add(value)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        coinMarketCapCoinArrayList!!.sortWith(Comparator { coinMarketCapCoin, t1 ->
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            if (coinMarketCapCoin.data!!.rank!! > t1.data!!.rank!!) -1 else if (coinMarketCapCoin.data!!.rank!! < t1.data!!.rank!!) 1 else 0
                        })
                        coinMarketCapCoinArrayList!!.reverse()
                        globalPriceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    @SuppressLint("StaticFieldLeak")
    private inner class RefreshTask : AsyncTask<Void?, Void?, Void?>() {
        override fun onPreExecute() {
            super.onPreExecute()
            if (!swipe_container!!.isRefreshing) {
                swipe_container!!.isRefreshing = true
            }
            coinMarketCapCoinArrayList!!.clear()
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val arrayOfCurrencyIds = resources.getStringArray(R.array.arrayOfCurrencyIds)
            for (id in arrayOfCurrencyIds) {
                getData(id)
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            globalPriceAdapter!!.notifyDataSetChanged()
        }
    }
}

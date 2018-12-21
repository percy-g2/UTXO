package com.androidevlinux.percy.UTXO.fragments

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.*
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.activities.MainActivity
import com.androidevlinux.percy.UTXO.adapters.ExchangePriceAdapter
import com.androidevlinux.percy.UTXO.data.models.bitfinex.BitfinexPubTickerResponseBean
import com.androidevlinux.percy.UTXO.data.models.bitstamp.BitstampBean
import com.androidevlinux.percy.UTXO.data.models.gdax.GDAX
import com.androidevlinux.percy.UTXO.data.models.pocketbits.PocketBitsBean
import com.androidevlinux.percy.UTXO.data.models.price.PriceBean
import com.androidevlinux.percy.UTXO.utils.Constants
import com.androidevlinux.percy.UTXO.utils.UniqueArrayList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_crypto_prices.*
import java.util.*

class ExchangeCryptoPricesFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, PopupMenu.OnMenuItemClickListener {

    private var strRuppeSymbol = "\u20B9"
    private var strDollarSymbol = "$"
    private var priceBeanArrayList: UniqueArrayList? = null
    private var priceAdapter: ExchangePriceAdapter? = null
    private var bitfinexPubTickerResponseBeanObservable: Observable<BitfinexPubTickerResponseBean>? = null
    private var bitstampObservable: Observable<BitstampBean>? = null
    private var pocketBitsBeanObservable: Observable<PocketBitsBean>? = null
    private var gdaxObservable: Observable<GDAX>? = null
    private var selectedCurrency = "BTC"
    private var disposables: CompositeDisposable? = null

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
        priceBeanArrayList = UniqueArrayList()
        val linearLayoutManager = LinearLayoutManager(activity!!)
        price_list_recycler_view!!.layoutManager = linearLayoutManager
        priceAdapter = ExchangePriceAdapter(priceBeanArrayList!!, mActivity!!, swipe_container!!)
        price_list_recycler_view!!.adapter = priceAdapter
        swipe_container!!.setOnRefreshListener(this)
        swipe_container!!.setColorSchemeColors(ContextCompat.getColor(mActivity!!, android.R.color.holo_green_dark),
                ContextCompat.getColor(mActivity!!, android.R.color.holo_red_dark),
                ContextCompat.getColor(mActivity!!, android.R.color.holo_blue_dark),
                ContextCompat.getColor(mActivity!!, android.R.color.holo_orange_dark))
        Constants.currentCurrency = selectedCurrency
        refreshBtcTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    override fun onResume() {
        super.onResume()
        mActivity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.fragment_crypto_prices, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.filter_crypto_prices_menu_item -> {
                val menuItemView = activity!!.findViewById<View>(R.id.filter_crypto_prices_menu_item)
                showPopupMenu(menuItemView)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showPopupMenu(v: View) {
        val popup = PopupMenu(activity!!, v)
        popup.setOnMenuItemClickListener(this@ExchangeCryptoPricesFragment)

        val inflater = popup.menuInflater
        inflater.inflate(R.menu.crypto_prices_filter_popup, popup.menu)
        popup.show()
    }

    /* from PopupMenu.OnMenuItemClickListener */
    override fun onMenuItemClick(item: MenuItem): Boolean {
        (activity as MainActivity).hideNavigationBar()
        when (item.itemId) {
            R.id.btc_filter_option -> {
                disposables!!.dispose()
                selectedCurrency = "BTC"
                Constants.currentCurrency = selectedCurrency
                refreshBtcTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                mActivity!!.title = getString(R.string.nav_crypto_prices_btc)
                return true
            }
            R.id.bch_filter_option -> {
                disposables!!.dispose()
                selectedCurrency = "BCH"
                Constants.currentCurrency = selectedCurrency
                refreshBchTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                mActivity!!.title = getString(R.string.nav_crypto_prices_bch)
                return true
            }
            R.id.eth_filter_option -> {
                disposables!!.dispose()
                selectedCurrency = "ETH"
                Constants.currentCurrency = selectedCurrency
                refreshEthTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                mActivity!!.title = getString(R.string.nav_crypto_prices_eth)
                return true
            }
            R.id.ltc_filter_option -> {
                disposables!!.dispose()
                selectedCurrency = "LTC"
                Constants.currentCurrency = selectedCurrency
                refreshLtcTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                mActivity!!.title = getString(R.string.nav_crypto_prices_ltc)
                return true
            }
            R.id.trx_filter_option -> {
                disposables!!.dispose()
                selectedCurrency = "TRX"
                Constants.currentCurrency = selectedCurrency
                refreshTrxTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                mActivity!!.title = getString(R.string.nav_crypto_prices_trx)
                return true
            }
            R.id.xrp_filter_option -> {
                disposables!!.dispose()
                selectedCurrency = "XRP"
                Constants.currentCurrency = selectedCurrency
                refreshXrpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                mActivity!!.title = getString(R.string.nav_crypto_prices_xrp)
                return true
            }
            else -> return false
        }
    }

    private fun getGdaxEthTicker() {
        gdaxObservable = apiManager!!.getData("ETH-USD")
        disposables = CompositeDisposable()
        disposables!!.add(gdaxObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<GDAX>() {

                    override fun onNext(value: GDAX) {
                        val priceBean = PriceBean()
                        priceBean.title = "Coinbase Pro (ETH)"
                        priceBean.price = strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", java.lang.Double.parseDouble(value.price))
                        priceBean.low_price = strDollarSymbol + value.bid!!
                        priceBean.high_price = strDollarSymbol + value.ask!!
                        priceBeanArrayList!!.add(priceBean)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getGdaxLtcTicker() {
        gdaxObservable = apiManager!!.getData("LTC-USD")
        disposables = CompositeDisposable()
        disposables!!.add(gdaxObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<GDAX>() {

                    override fun onNext(value: GDAX) {
                        val priceBean = PriceBean()
                        priceBean.title = "Coinbase Pro (LTC)"
                        priceBean.price = strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", java.lang.Double.parseDouble(value.price))
                        priceBean.low_price = strDollarSymbol + value.bid!!
                        priceBean.high_price = strDollarSymbol + value.ask!!
                        priceBeanArrayList!!.add(priceBean)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }


    private fun getGdaxBchTicker() {
        gdaxObservable = apiManager!!.getData("BCH-USD")
        disposables = CompositeDisposable()
        disposables!!.add(gdaxObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<GDAX>() {

                    override fun onNext(value: GDAX) {
                        val priceBean = PriceBean()
                        priceBean.title = "Coinbase Pro (BCH)"
                        priceBean.price = strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", java.lang.Double.parseDouble(value.price))
                        priceBean.low_price = strDollarSymbol + value.bid!!
                        priceBean.high_price = strDollarSymbol + value.ask!!
                        priceBeanArrayList!!.add(priceBean)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getGdaxBtcTicker() {
        gdaxObservable = apiManager!!.getData("BTC-USD")
        disposables = CompositeDisposable()
        disposables!!.add(gdaxObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<GDAX>() {

                    override fun onNext(value: GDAX) {
                        val priceBean = PriceBean()
                        priceBean.title = "Coinbase Pro (BTC)"
                        priceBean.price = strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", java.lang.Double.parseDouble(value.price))
                        priceBean.low_price = strDollarSymbol + value.bid!!
                        priceBean.high_price = strDollarSymbol + value.ask!!
                        priceBeanArrayList!!.add(priceBean)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getBitfinexPubTicker() {
        bitfinexPubTickerResponseBeanObservable = apiManager!!.getBitfinexTicker("btcusd")
        disposables = CompositeDisposable()
        disposables!!.add(bitfinexPubTickerResponseBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BitfinexPubTickerResponseBean>() {

                    override fun onNext(value: BitfinexPubTickerResponseBean) {
                        Constants.btc_price = value.lastPrice
                        Constants.btc_price_low = value.low
                        Constants.btc_price_high = value.high
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        val priceBean = PriceBean()
                        priceBean.title = "Bitfinex (BTC)"
                        priceBean.price = strDollarSymbol + Constants.btc_price
                        priceBean.low_price = strDollarSymbol + Constants.btc_price_low
                        priceBean.high_price = strDollarSymbol + Constants.btc_price_high
                        priceBeanArrayList!!.add(priceBean)
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getBitfinexPubEthTicker() {
        bitfinexPubTickerResponseBeanObservable = apiManager!!.getBitfinexTicker("ethusd")
        disposables = CompositeDisposable()
        disposables!!.add(bitfinexPubTickerResponseBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BitfinexPubTickerResponseBean>() {

                    override fun onNext(value: BitfinexPubTickerResponseBean) {
                        Constants.btc_price = value.lastPrice
                        Constants.btc_price_low = value.low
                        Constants.btc_price_high = value.high
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        val priceBean = PriceBean()
                        priceBean.title = "Bitfinex (ETH)"
                        priceBean.price = strDollarSymbol + Constants.btc_price
                        priceBean.low_price = strDollarSymbol + Constants.btc_price_low
                        priceBean.high_price = strDollarSymbol + Constants.btc_price_high
                        priceBeanArrayList!!.add(priceBean)
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getBitfinexPubTRXTicker() {
        bitfinexPubTickerResponseBeanObservable = apiManager!!.getBitfinexTicker("trxusd")
        disposables = CompositeDisposable()
        disposables!!.add(bitfinexPubTickerResponseBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BitfinexPubTickerResponseBean>() {

                    override fun onNext(value: BitfinexPubTickerResponseBean) {
                        Constants.btc_price = value.lastPrice
                        Constants.btc_price_low = value.low
                        Constants.btc_price_high = value.high
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        val priceBean = PriceBean()
                        priceBean.title = "Bitfinex (TRX)"
                        priceBean.price = strDollarSymbol + Constants.btc_price
                        priceBean.low_price = strDollarSymbol + Constants.btc_price_low
                        priceBean.high_price = strDollarSymbol + Constants.btc_price_high
                        priceBeanArrayList!!.add(priceBean)
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getBitfinexPubLtcTicker() {
        bitfinexPubTickerResponseBeanObservable = apiManager!!.getBitfinexTicker("ltcusd")
        disposables = CompositeDisposable()
        disposables!!.add(bitfinexPubTickerResponseBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BitfinexPubTickerResponseBean>() {

                    override fun onNext(value: BitfinexPubTickerResponseBean) {
                        Constants.btc_price = value.lastPrice
                        Constants.btc_price_low = value.low
                        Constants.btc_price_high = value.high
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        val priceBean = PriceBean()
                        priceBean.title = "Bitfinex (LTC)"
                        priceBean.price = strDollarSymbol + Constants.btc_price
                        priceBean.low_price = strDollarSymbol + Constants.btc_price_low
                        priceBean.high_price = strDollarSymbol + Constants.btc_price_high
                        priceBeanArrayList!!.add(priceBean)
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getBitfinexPubBchTicker() {
        bitfinexPubTickerResponseBeanObservable = apiManager!!.getBitfinexTicker("bchusd")
        disposables = CompositeDisposable()
        disposables!!.add(bitfinexPubTickerResponseBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BitfinexPubTickerResponseBean>() {

                    override fun onNext(value: BitfinexPubTickerResponseBean) {
                        Constants.btc_price = value.lastPrice
                        Constants.btc_price_low = value.low
                        Constants.btc_price_high = value.high
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        val priceBean = PriceBean()
                        priceBean.title = "Bitfinex (BCH)"
                        priceBean.price = strDollarSymbol + Constants.btc_price
                        priceBean.low_price = strDollarSymbol + Constants.btc_price_low
                        priceBean.high_price = strDollarSymbol + Constants.btc_price_high
                        priceBeanArrayList!!.add(priceBean)
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getBitfinexXrpPubTicker() {
        bitfinexPubTickerResponseBeanObservable = apiManager!!.getBitfinexTicker("xrpusd")
        disposables = CompositeDisposable()
        disposables!!.add(bitfinexPubTickerResponseBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BitfinexPubTickerResponseBean>() {

                    override fun onNext(value: BitfinexPubTickerResponseBean) {
                        Constants.btc_price = value.lastPrice
                        Constants.btc_price_low = value.low
                        Constants.btc_price_high = value.high
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        val priceBean = PriceBean()
                        priceBean.title = "Bitfinex (XRP)"
                        priceBean.price = strDollarSymbol + Constants.btc_price
                        priceBean.low_price = strDollarSymbol + Constants.btc_price_low
                        priceBean.high_price = strDollarSymbol + Constants.btc_price_high
                        priceBeanArrayList!!.add(priceBean)
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getBitStampEthTicker() {
        bitstampObservable = apiManager!!.getBitstampTicker("ethusd")
        disposables = CompositeDisposable()
        disposables!!.add(bitstampObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BitstampBean>() {

                    override fun onNext(value: BitstampBean) {
                        Constants.btc_price = value.last.toString().replace("^\"|\"$".toRegex(), "")
                        Constants.btc_price_low = value.low.toString().replace("^\"|\"$".toRegex(), "")
                        Constants.btc_price_high = value.high.toString().replace("^\"|\"$".toRegex(), "")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        val priceBean = PriceBean()
                        priceBean.title = "Bitstamp (ETH)"
                        priceBean.price = strDollarSymbol + Constants.btc_price
                        priceBean.low_price = strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", java.lang.Double.parseDouble(Constants.btc_price_low))
                        priceBean.high_price = strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", java.lang.Double.parseDouble(Constants.btc_price_high))
                        priceBeanArrayList!!.add(priceBean)
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getBitStampXrpTicker() {
        bitstampObservable = apiManager!!.getBitstampTicker("xrpusd")
        disposables = CompositeDisposable()
        disposables!!.add(bitstampObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BitstampBean>() {

                    override fun onNext(value: BitstampBean) {
                        Constants.btc_price = value.last.toString().replace("^\"|\"$".toRegex(), "")
                        Constants.btc_price_low = value.low.toString().replace("^\"|\"$".toRegex(), "")
                        Constants.btc_price_high = value.high.toString().replace("^\"|\"$".toRegex(), "")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        val priceBean = PriceBean()
                        priceBean.title = "Bitstamp (XRP)"
                        priceBean.price = strDollarSymbol + Constants.btc_price
                        priceBean.low_price = strDollarSymbol + Constants.btc_price_low
                        priceBean.high_price = strDollarSymbol + Constants.btc_price_high
                        priceBeanArrayList!!.add(priceBean)
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getBitStampLtcTicker() {
        bitstampObservable = apiManager!!.getBitstampTicker("ltcusd")
        disposables = CompositeDisposable()
        disposables!!.add(bitstampObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BitstampBean>() {

                    override fun onNext(value: BitstampBean) {
                        Constants.btc_price = value.last.toString().replace("^\"|\"$".toRegex(), "")
                        Constants.btc_price_low = value.low.toString().replace("^\"|\"$".toRegex(), "")
                        Constants.btc_price_high = value.high.toString().replace("^\"|\"$".toRegex(), "")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        val priceBean = PriceBean()
                        priceBean.title = "Bitstamp (LTC)"
                        priceBean.price = strDollarSymbol + Constants.btc_price
                        priceBean.low_price = strDollarSymbol + Constants.btc_price_low
                        priceBean.high_price = strDollarSymbol + Constants.btc_price_high
                        priceBeanArrayList!!.add(priceBean)
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }


    private fun getBitStampBchTicker() {
        bitstampObservable = apiManager!!.getBitstampTicker("bchusd")
        disposables = CompositeDisposable()
        disposables!!.add(bitstampObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BitstampBean>() {

                    override fun onNext(value: BitstampBean) {
                        Constants.btc_price = value.last.toString().replace("^\"|\"$".toRegex(), "")
                        Constants.btc_price_low = value.low.toString().replace("^\"|\"$".toRegex(), "")
                        Constants.btc_price_high = value.high.toString().replace("^\"|\"$".toRegex(), "")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        val priceBean = PriceBean()
                        priceBean.title = "Bitstamp (BCH)"
                        priceBean.price = strDollarSymbol + Constants.btc_price
                        priceBean.low_price = strDollarSymbol + Constants.btc_price_low
                        priceBean.high_price = strDollarSymbol + Constants.btc_price_high
                        priceBeanArrayList!!.add(priceBean)
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getBitStampTicker() {
        bitstampObservable = apiManager!!.getBitstampTicker("btcusd")
        disposables = CompositeDisposable()
        disposables!!.add(bitstampObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BitstampBean>() {

                    override fun onNext(value: BitstampBean) {
                        Constants.btc_price = value.last.toString().replace("^\"|\"$".toRegex(), "")
                        Constants.btc_price_low = value.low.toString().replace("^\"|\"$".toRegex(), "")
                        Constants.btc_price_high = value.high.toString().replace("^\"|\"$".toRegex(), "")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        val priceBean = PriceBean()
                        priceBean.title = "Bitstamp (BTC)"
                        priceBean.price = strDollarSymbol + Constants.btc_price
                        priceBean.low_price = strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", java.lang.Double.parseDouble(Constants.btc_price_low))
                        priceBean.high_price = strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", java.lang.Double.parseDouble(Constants.btc_price_high))
                        priceBeanArrayList!!.add(priceBean)
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getPocketbitsTicker() {
        pocketBitsBeanObservable = apiManager!!.pocketbitsTicker
        disposables = CompositeDisposable()
        disposables!!.add(pocketBitsBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<PocketBitsBean>() {

                    override fun onNext(value: PocketBitsBean) {
                        val priceBean = PriceBean()
                        priceBean.title = "Pocketbits (BTC)"
                        priceBean.price = strRuppeSymbol + value.altcoins!![0].altBuyPrice.toString()
                        priceBean.low_price = strRuppeSymbol + value.altcoins!![0].altSellPrice.toString()
                        priceBean.high_price = strRuppeSymbol + value.altcoins!![0].altBuyPrice.toString()
                        priceBeanArrayList!!.add(priceBean)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getPocketbitsTRXTicker() {
        pocketBitsBeanObservable = apiManager!!.pocketbitsTicker
        disposables = CompositeDisposable()
        disposables!!.add(pocketBitsBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<PocketBitsBean>() {

                    override fun onNext(value: PocketBitsBean) {
                        val priceBean = PriceBean()
                        priceBean.title = "Pocketbits (TRX)"
                        priceBean.price = strRuppeSymbol + value.altcoins!![17].altBuyPrice.toString()
                        priceBean.low_price = strRuppeSymbol + value.altcoins!![17].altSellPrice.toString()
                        priceBean.high_price = strRuppeSymbol + value.altcoins!![17].altBuyPrice.toString()
                        priceBeanArrayList!!.add(priceBean)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getPocketbitsLtcTicker() {
        pocketBitsBeanObservable = apiManager!!.pocketbitsTicker
        disposables = CompositeDisposable()
        disposables!!.add(pocketBitsBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<PocketBitsBean>() {

                    override fun onNext(value: PocketBitsBean) {
                        val priceBean = PriceBean()
                        priceBean.title = "Pocketbits (LTC)"
                        priceBean.price = strRuppeSymbol + value.altcoins!![4].altBuyPrice.toString()
                        priceBean.low_price = strRuppeSymbol + value.altcoins!![4].altSellPrice.toString()
                        priceBean.high_price = strRuppeSymbol + value.altcoins!![4].altBuyPrice.toString()
                        priceBeanArrayList!!.add(priceBean)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getPocketbitsEthTicker() {
        pocketBitsBeanObservable = apiManager!!.pocketbitsTicker
        disposables = CompositeDisposable()
        disposables!!.add(pocketBitsBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<PocketBitsBean>() {

                    override fun onNext(value: PocketBitsBean) {
                        val priceBean = PriceBean()
                        priceBean.title = "Pocketbits (ETH)"
                        priceBean.price = strRuppeSymbol + value.altcoins!![3].altBuyPrice.toString()
                        priceBean.low_price = strRuppeSymbol + value.altcoins!![3].altSellPrice.toString()
                        priceBean.high_price = strRuppeSymbol + value.altcoins!![3].altBuyPrice.toString()
                        priceBeanArrayList!!.add(priceBean)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getPocketbitsBchTicker() {
        pocketBitsBeanObservable = apiManager!!.pocketbitsTicker
        disposables = CompositeDisposable()
        disposables!!.add(pocketBitsBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<PocketBitsBean>() {

                    override fun onNext(value: PocketBitsBean) {
                        val priceBean = PriceBean()
                        priceBean.title = "Pocketbits (BCH)"
                        priceBean.price = strRuppeSymbol + value.altcoins!![8].altBuyPrice.toString()
                        priceBean.low_price = strRuppeSymbol + value.altcoins!![8].altSellPrice.toString()
                        priceBean.high_price = strRuppeSymbol + value.altcoins!![8].altBuyPrice.toString()
                        priceBeanArrayList!!.add(priceBean)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    private fun getPocketbitsXrpTicker() {
        pocketBitsBeanObservable = apiManager!!.pocketbitsTicker
        disposables = CompositeDisposable()
        disposables!!.add(pocketBitsBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<PocketBitsBean>() {

                    override fun onNext(value: PocketBitsBean) {
                        val priceBean = PriceBean()
                        priceBean.title = "Pocketbits (XRP)"
                        priceBean.price = strRuppeSymbol + value.altcoins!![6].altBuyPrice.toString()
                        priceBean.low_price = strRuppeSymbol + value.altcoins!![6].altSellPrice.toString()
                        priceBean.high_price = strRuppeSymbol + value.altcoins!![6].altBuyPrice.toString()
                        priceBeanArrayList!!.add(priceBean)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        priceAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (disposables != null) {
            disposables!!.dispose()
        }
    }

    override fun onRefresh() {
        disposables!!.dispose()
        when (selectedCurrency) {
            "BTC" -> refreshBtcTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            "BCH" -> refreshBchTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            "ETH" -> refreshEthTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            "LTC" -> refreshLtcTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            "TRX" -> refreshTrxTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            "XRP" -> refreshXrpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class refreshBtcTask : AsyncTask<String?, String?, String?>() {


        override fun onPreExecute() {
            super.onPreExecute()
            if (!swipe_container!!.isRefreshing) {
                swipe_container!!.isRefreshing = true
            }
            priceBeanArrayList!!.clear()
        }

        override fun doInBackground(vararg value: String?): String? {
            getBitfinexPubTicker()
            getBitStampTicker()
            getPocketbitsTicker()
            getGdaxBtcTicker()
            return null
        }

        override fun onPostExecute(value: String?) {
            priceAdapter!!.notifyDataSetChanged()
        }
    }


    @SuppressLint("StaticFieldLeak")
    private inner class refreshBchTask : AsyncTask<String?, String?, String?>() {


        override fun onPreExecute() {
            super.onPreExecute()
            if (!swipe_container!!.isRefreshing) {
                swipe_container!!.isRefreshing = true
            }
            priceBeanArrayList!!.clear()
        }

        override fun doInBackground(vararg value: String?): String? {
            getPocketbitsBchTicker()
            getBitfinexPubBchTicker()
            getBitStampBchTicker()
            getGdaxBchTicker()
            return null
        }

        override fun onPostExecute(value: String?) {
            priceAdapter!!.notifyDataSetChanged()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class refreshLtcTask : AsyncTask<String?, String?, String?>() {


        override fun onPreExecute() {
            super.onPreExecute()
            if (!swipe_container!!.isRefreshing) {
                swipe_container!!.isRefreshing = true
            }
            priceBeanArrayList!!.clear()
        }

        override fun doInBackground(vararg value: String?): String? {
            getPocketbitsLtcTicker()
            getBitfinexPubLtcTicker()
            getBitStampLtcTicker()
            getGdaxLtcTicker()
            return null
        }

        override fun onPostExecute(value: String?) {
            priceAdapter!!.notifyDataSetChanged()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class refreshXrpTask : AsyncTask<String?, String?, String?>() {


        override fun onPreExecute() {
            super.onPreExecute()
            if (!swipe_container!!.isRefreshing) {
                swipe_container!!.isRefreshing = true
            }
            priceBeanArrayList!!.clear()
        }

        override fun doInBackground(vararg value: String?): String? {
            getPocketbitsXrpTicker()
            getBitfinexXrpPubTicker()
            getBitStampXrpTicker()
            return null
        }

        override fun onPostExecute(value: String?) {
            priceAdapter!!.notifyDataSetChanged()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class refreshEthTask : AsyncTask<String?, String?, String?>() {


        override fun onPreExecute() {
            super.onPreExecute()
            if (!swipe_container!!.isRefreshing) {
                swipe_container!!.isRefreshing = true
            }
            priceBeanArrayList!!.clear()
        }

        override fun doInBackground(vararg value: String?): String? {
            getPocketbitsEthTicker()
            getBitfinexPubEthTicker()
            getBitStampEthTicker()
            getGdaxEthTicker()
            return null
        }

        override fun onPostExecute(value: String?) {
            priceAdapter!!.notifyDataSetChanged()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class refreshTrxTask : AsyncTask<String?, String?, String?>() {


        override fun onPreExecute() {
            super.onPreExecute()
            if (!swipe_container!!.isRefreshing) {
                swipe_container!!.isRefreshing = true
            }
            priceBeanArrayList!!.clear()
        }

        override fun doInBackground(vararg value: String?): String? {
            getPocketbitsTRXTicker()
            getBitfinexPubTRXTicker()
            return null
        }

        override fun onPostExecute(value: String?) {
            priceAdapter!!.notifyDataSetChanged()
        }
    }
}

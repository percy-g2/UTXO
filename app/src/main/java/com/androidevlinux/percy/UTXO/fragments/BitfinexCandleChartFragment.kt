package com.androidevlinux.percy.UTXO.fragments

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.widget.PopupMenu
import com.afollestad.aesthetic.Aesthetic
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.activities.MainActivity
import com.androidevlinux.percy.UTXO.data.models.bitfinex.BitfinexPubTickerResponseBean
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bitfinex_candle_chart_fragment.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.math.BigDecimal
import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by percy on 10/1/18.
 */

class BitfinexCandleChartFragment : BaseFragment(), OnChartValueSelectedListener, PopupMenu.OnMenuItemClickListener {

    private var entries = ArrayList<CandleEntry>()
    private var textColor: Int? = null
    private var xValues = ArrayList<String>()
    private var xFloatValues = ArrayList<Long>()
    private var currencyId = 0
    private var description = Description()

    private var bitfinexPubTickerResponseBeanObservable: Observable<BitfinexPubTickerResponseBean>? = null
    private var disposables: CompositeDisposable? = null

    private var fullDateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bitfinex_candle_chart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chart_interval_button_grp!!.check(R.id.fifteenMinButton)
        getBitfinexData(currencyId, "15m")
        candleChart!!.setOnChartValueSelectedListener(this)
        Aesthetic.get()
                .colorAccent()
                .take(1)
                .subscribe { color ->
                    fifteenMinButton.markerColor = color
                    sixHourssButton.markerColor = color
                    dayButton.markerColor = color
                    weekButton.markerColor = color
                    twoWeeksButton.markerColor = color
                    monthButton.markerColor = color
                }.dispose()

        Aesthetic.get()
                .textColorPrimary()
                .take(1)
                .subscribe { color ->
                    textColor = color
                }.dispose()

        val displayMetrics = DisplayMetrics()
        mActivity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val displayWidth = displayMetrics.widthPixels
        candleChart!!.onChartGestureListener = object : OnChartGestureListener {
            override fun onChartGestureStart(me: MotionEvent, lastPerformedGesture: ChartTouchListener.ChartGesture) {
                val yAxis = candleChart!!.axisLeft
                // Allow scrolling in the right and left margins
                if (me.x > yAxis.longestLabel.length * yAxis.textSize && me.x < displayWidth - candleChart!!.viewPortHandler.offsetRight()) {
                    graphFragmentNestedScrollView!!.setScrollingEnabled(false)
                }
            }

            override fun onChartGestureEnd(me: MotionEvent, lastPerformedGesture: ChartTouchListener.ChartGesture) {
                graphFragmentNestedScrollView!!.setScrollingEnabled(true)
            }

            override fun onChartLongPressed(me: MotionEvent) {

            }

            override fun onChartDoubleTapped(me: MotionEvent) {

            }

            override fun onChartSingleTapped(me: MotionEvent) {

            }

            override fun onChartFling(me1: MotionEvent, me2: MotionEvent, velocityX: Float, velocityY: Float) {

            }

            override fun onChartScale(me: MotionEvent, scaleX: Float, scaleY: Float) {

            }

            override fun onChartTranslate(me: MotionEvent, dX: Float, dY: Float) {

            }
        }
        chart_interval_button_grp!!.setOnCheckedChangeListener { _, checkedId ->
            Calendar.getInstance()
            when (checkedId) {
                R.id.fifteenMinButton -> getBitfinexData(currencyId, "15m")
                R.id.sixHourssButton -> getBitfinexData(currencyId, "6h")
                R.id.dayButton -> getBitfinexData(currencyId, "1D")
                R.id.weekButton -> getBitfinexData(currencyId, "7D")
                R.id.twoWeeksButton -> getBitfinexData(currencyId, "14D")
                R.id.monthButton -> getBitfinexData(currencyId, "1M")
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (disposables != null) {
            disposables!!.dispose()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crypto_prices, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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
        popup.setOnMenuItemClickListener(this@BitfinexCandleChartFragment)

        val inflater = popup.menuInflater
        inflater.inflate(R.menu.crypto_candle_chart_filter_popup, popup.menu)
        popup.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /* from PopupMenu.OnMenuItemClickListener */
    override fun onMenuItemClick(item: MenuItem): Boolean {
        (activity as MainActivity).hideNavigationBar()
        when (item.itemId) {
            R.id.btc_filter_option -> {
                currencyId = 0
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_candle_chart_btc)
                return true
            }
            R.id.bab_filter_option -> {
                currencyId = 1
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_candle_chart_bab)
                return true
            }
            R.id.eth_filter_option -> {
                currencyId = 2
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_candle_chart_eth)
                return true
            }
            R.id.ltc_filter_option -> {
                currencyId = 3
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_candle_chart_ltc)
                return true
            }
            R.id.xrp_filter_option -> {
                currencyId = 4
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_candle_chart_xrp)
                return true
            }
            R.id.bsv_filter_option -> {
                currencyId = 5
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_candle_chart_bsv)
                return true
            }
            else -> return false
        }
    }

    private fun refresh() {
        when (chart_interval_button_grp!!.checkedId) {
            R.id.fifteenMinButton -> getBitfinexData(currencyId, "15m")
            R.id.sixHourssButton -> getBitfinexData(currencyId, "6h")
            R.id.dayButton -> getBitfinexData(currencyId, "1D")
            R.id.weekButton -> getBitfinexData(currencyId, "7D")
            R.id.twoWeeksButton -> getBitfinexData(currencyId, "14D")
            R.id.monthButton -> getBitfinexData(currencyId, "1M")
        }
    }

    private fun getBitfinexPubTicker(symbol: String, currency: String) {
        bitfinexPubTickerResponseBeanObservable = apiManager!!.getBitfinexTicker(symbol)
        disposables = CompositeDisposable()
        disposables!!.add(bitfinexPubTickerResponseBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BitfinexPubTickerResponseBean>() {

                    override fun onNext(value: BitfinexPubTickerResponseBean) {
                        if (value.lastPrice == null) {
                            tableNameDataTextView!!.text = "N/A"
                        } else {
                            tableNameDataTextView!!.text = currency
                        }
                        tablePriceBTCDataTextView!!.text = "N/A"
                        if (value.lastPrice == null) {
                            tablePriceUSDDataTextView!!.text = "N/A"
                        } else {
                            tablePriceUSDDataTextView!!.text = MessageFormat.format("$ {0}", value.lastPrice)
                        }

                        if (value.volume == null) {
                            tableVolUSDDataTextView!!.text = "N/A"
                        } else {
                            tableVolUSDDataTextView!!.text = MessageFormat.format("$ {0}", value.volume)
                        }

                        if (value.low == null) {
                            table24hrLowTitleTextView!!.text = "N/A"
                        } else {
                            table24hrLowTitleTextView!!.text = MessageFormat.format("$ {0}", value.low)
                        }

                        if (value.high == null) {
                            table24hrHighTitleTextView!!.text = "N/A"
                        } else {
                            table24hrHighTitleTextView!!.text = MessageFormat.format("$ {0}", value.high)
                        }

                        if (value.bid == null) {
                            tableBidDataTextView!!.text = "N/A"
                        } else {
                            tableBidDataTextView!!.text = MessageFormat.format("$ {0}", value.bid)
                        }

                        if (value.ask == null) {
                            tableAskDataTextView!!.text = "N/A"
                        } else {
                            tableAskDataTextView!!.text = MessageFormat.format("$ {0}", value.ask)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {

                    }
                }))
    }

    private fun getBitfinexData(currencyId: Int, time: String) {
        chartProgressSpinner!!.visibility = View.VISIBLE
        mActivity!!.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        var symbol = "tBTCUSD"
        var strSymbol = "btcusd"
        var currency = "Bitcoin"
        //candleChart.setDragEnabled(false);
        when (currencyId) {
            0 -> {
                symbol = "tBTCUSD"
                strSymbol = "btcusd"
                currency = "Bitcoin"
            }
            1 -> {
                symbol = "tBABUSD"
                strSymbol = "babusd"
                currency = "Bitcoin Cash ABC(BAB)"
            }
            2 -> {
                symbol = "tETHUSD"
                strSymbol = "ethusd"
                currency = "Ethereum"
            }
            3 -> {
                symbol = "tLTCUSD"
                strSymbol = "ltcusd"
                currency = "Litecoin"
            }
            4 -> {
                symbol = "tXRPUSD"
                strSymbol = "xrpusd"
                currency = "Ripple"
            }
            5 -> {
                symbol = "tBSVUSD"
                strSymbol = "bsvusd"
                currency = "Bitcoin Cash SV(BSV)"
            }
        }
        getBitfinexPubTicker(strSymbol, currency)
        val finalCurrency = currency
        apiManager!!.getBitfinexData(time, symbol, object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val gson = Gson()
                try {
                    val newMap: Array<Array<BigDecimal>>
                    val dummy = Array<Array<BigDecimal>>(0) { arrayOf(BigDecimal.ZERO) }
                    newMap = gson.fromJson(response.body()!!.string(), dummy.javaClass)
                    entries.clear()
                    xValues.clear()
                    xFloatValues.clear()
                    if (candleChart != null) {
                        candleChart!!.clear()
                        candleChart!!.invalidate()
                    }
                    var count = -1

                    newMap.reverse()
                    for (s in newMap) {
                        count += 1
                        entries.add(CandleEntry(count.toFloat(), java.lang.Float.valueOf(s[3].toString()), java.lang.Float.valueOf(s[4].toString()), java.lang.Float.valueOf(s[1].toString()), java.lang.Float.valueOf(s[2].toString())))
                        val dataSet = CandleDataSet(entries, "Data")
                        dataSet.color = Color.rgb(80, 80, 80)
                        dataSet.shadowColor = Color.DKGRAY
                        dataSet.shadowWidth = 0.7f
                        dataSet.decreasingColor = Color.RED
                        dataSet.decreasingPaintStyle = Paint.Style.FILL
                        dataSet.increasingColor = Color.GREEN
                        dataSet.increasingPaintStyle = Paint.Style.FILL
                        dataSet.neutralColor = Color.BLUE
                        dataSet.valueTextColor = Color.RED
                        val date = Date(java.lang.Long.valueOf(s[0].toString()))
                        val sdf: SimpleDateFormat = when ("12h") {
                            "7D", "14D", "1M" -> SimpleDateFormat("MMM/yy", Locale.ENGLISH)
                            "6h", "12h" -> SimpleDateFormat("MM/dd", Locale.ENGLISH)
                            else -> SimpleDateFormat("HH:mm", Locale.ENGLISH)
                        }
                        sdf.timeZone = TimeZone.getDefault()
                        val formattedDate = sdf.format(date)

                        xValues.add(formattedDate)
                        xFloatValues.add(java.lang.Long.valueOf(s[0].toString()))
                        val yAxis = candleChart!!.axisRight
                        yAxis.isGranularityEnabled = true
                        val xAxis = candleChart!!.xAxis
                        xAxis.granularity = 1f

                        xAxis.valueFormatter = object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                return xValues[value.toInt() % xValues.size]
                            }
                        }

                        dataSet.highlightLineWidth = 2f
                        dataSet.isHighlightEnabled = true
                        dataSet.setDrawHighlightIndicators(true)
                        dataSet.highLightColor = Color.YELLOW
                        val data = CandleData(dataSet)
                        candleChart!!.data = data
                    }
                    val currPrice = entries[entries.size - 1].y
                    dateTextView!!.text = getFormattedFullDate(xFloatValues[entries.size - 1].toFloat())
                    current_price!!.text = String.format(getString(R.string.unrounded_usd_chart_price_format), currPrice.toString())
                    current_price!!.setTextColor(textColor!!)

                    candleChart!!.animateX(800)
                    description.text = finalCurrency
                    description.textSize = 15f
                    description.textAlign = Paint.Align.RIGHT
                    description.textColor = textColor!!
                    candleChart!!.description = description
                    //candleChart.setDragEnabled(false);
                    candleChart!!.setScaleEnabled(true)
                    candleChart!!.extraRightOffset = 30f
                    candleChart!!.setDrawGridBackground(false)
                    candleChart!!.axisLeft.setDrawGridLines(false)
                    candleChart!!.xAxis.setDrawGridLines(false)
                    candleChart!!.axisLeft.textColor = textColor!!
                    candleChart!!.xAxis.textColor = textColor!!
                    candleChart!!.axisRight.textColor = textColor!!
                    candleChart!!.axisRight.setDrawGridLines(false)
                    candleChart!!.setDrawBorders(false)
                    candleChart!!.setPinchZoom(true)
                    val l = candleChart!!.legend
                    l.isEnabled = false
                    candleChart!!.isAutoScaleMinMaxEnabled = true
                    candleChart!!.invalidate()
                    chartProgressSpinner!!.visibility = View.GONE
                    mActivity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                } catch (e: IOException) {
                    e.printStackTrace()
                    mActivity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                chartProgressSpinner!!.visibility = View.GONE
            }
        })
    }

    override fun onValueSelected(e: Entry, h: Highlight) {
        current_price!!.text = String.format(getString(R.string.unrounded_usd_chart_price_format), e.y.toString())
        dateTextView!!.text = getFormattedFullDate(xFloatValues[e.x.toInt()].toFloat())
    }

    override fun onNothingSelected() {

    }

    fun getFormattedFullDate(unixSeconds: Float): String {
        val date = Date(unixSeconds.toLong())
        return fullDateFormat.format(date)
    }
}
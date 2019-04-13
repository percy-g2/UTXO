package com.androidevlinux.percy.UTXO.fragments

/**
 * Created by Ryan on 8/11/2017.
 */

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.afollestad.aesthetic.Aesthetic
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.activities.MainActivity
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapChartData
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapCoin
import com.androidevlinux.percy.UTXO.data.network.ApiManager
import com.androidevlinux.percy.UTXO.formatters.MonthSlashDayDateFormatter
import com.androidevlinux.percy.UTXO.formatters.MonthSlashYearFormatter
import com.androidevlinux.percy.UTXO.formatters.TimeDateFormatter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_graph.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class GraphFragment : BaseFragment(), OnChartValueSelectedListener, PopupMenu.OnMenuItemClickListener {
    private var chartFillColor: Int = 0
    private var chartBorderColor: Int = 0
    private var cryptoID: String? = null
    private var currencyId: String? = null
    private var percentageColor: Int = 0
    private var xAxisFormatter: ValueFormatter? = null
    private val monthSlashDayXAxisFormatter: ValueFormatter = MonthSlashDayDateFormatter()
    private val dayCommaTimeDateFormatter = TimeDateFormatter()
    private val monthSlashYearFormatter = MonthSlashYearFormatter()
    private var currentTimeWindow = ""
    private var displayWidth: Int = 0
    private var chartUSDPriceFormat = NumberFormat.getInstance()
    private var fullDateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    private var coinMarketCapFooterDataObservable: Observable<CoinMarketCapCoin>? = null
    private var coinMarketCapDataObservable: Observable<CoinMarketCapChartData>? = null
    private var disposables: CompositeDisposable? = null

    override fun onDestroyView() {
        super.onDestroyView()
        mActivity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        if (disposables != null) {
            disposables!!.dispose()
        }
    }

    fun setColors(percentChange: Float) {
        if (percentChange >= 0) {
            chartFillColor = ResourcesCompat.getColor(mActivity!!.resources, R.color.materialLightGreen, null)
            chartBorderColor = ResourcesCompat.getColor(mActivity!!.resources, R.color.darkGreen, null)
            percentageColor = ResourcesCompat.getColor(mActivity!!.resources, R.color.percentPositiveGreen, null)
        } else {
            chartFillColor = ResourcesCompat.getColor(mActivity!!.resources, R.color.materialLightRed, null)
            chartBorderColor = ResourcesCompat.getColor(mActivity!!.resources, R.color.darkRed, null)
            percentageColor = ResourcesCompat.getColor(mActivity!!.resources, R.color.percentNegativeRed, null)
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
        popup.setOnMenuItemClickListener(this@GraphFragment)

        val inflater = popup.menuInflater
        inflater.inflate(R.menu.cryptoo_graphs_filter_popup, popup.menu)
        popup.show()
    }

    /* from PopupMenu.OnMenuItemClickListener */
    override fun onMenuItemClick(item: MenuItem): Boolean {
        (activity as MainActivity).hideNavigationBar()
        when (item.itemId) {
            R.id.btc_filter_option -> {
                cryptoID = "bitcoin"
                currencyId = "1"
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_graph_btc)
                return true
            }
            R.id.bch_filter_option -> {
                cryptoID = "bitcoin-cash"
                currencyId = "1831"
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_graph_bch)
                return true
            }
            R.id.dash_filter_option -> {
                cryptoID = "dash"
                currencyId = "131"
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_graph_dash)
                return true
            }
            R.id.eos_filter_option -> {
                cryptoID = "eos"
                currencyId = "1765"
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_graph_eos)
                return true
            }
            R.id.eth_filter_option -> {
                cryptoID = "ethereum"
                currencyId = "1027"
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_graph_eth)
                return true
            }
            R.id.ltc_filter_option -> {
                cryptoID = "litecoin"
                currencyId = "2"
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_graph_ltc)
                return true
            }
            R.id.iota_filter_option -> {
                cryptoID = "iota"
                currencyId = "1720"
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_graph_iota)
                return true
            }
            R.id.xlm_filter_option -> {
                cryptoID = "stellar"
                currencyId = "512"
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_graph_xlm)
                return true
            }
            R.id.trx_filter_option -> {
                cryptoID = "tron"
                currencyId = "1958"
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_graph_trx)
                return true
            }
            R.id.xmr_filter_option -> {
                cryptoID = "monero"
                currencyId = "328"
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_graph_xmr)
                return true
            }
            R.id.xrp_filter_option -> {
                cryptoID = "ripple"
                currencyId = "52"
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_graph_xrp)
                return true
            }
            R.id.zec_filter_option -> {
                cryptoID = "zcash"
                currencyId = "1437"
                refresh()
                mActivity!!.title = getString(R.string.nav_crypto_graph_zec)
                return true
            }
            else -> return false
        }
    }

    internal fun refresh() {
        when (chart_interval_button_grp!!.checkedId) {
            R.id.dayButton -> {
                setDayChecked(Calendar.getInstance())
                getCMCChart()
            }
            R.id.weekButton -> {
                setWeekChecked(Calendar.getInstance())
                getCMCChart()
            }
            R.id.monthButton -> {
                setMonthChecked(Calendar.getInstance())
                getCMCChart()
            }
            R.id.threeMonthButton -> {
                setThreeMonthChecked(Calendar.getInstance())
                getCMCChart()
            }
            R.id.yearButton -> {
                setYearChecked(Calendar.getInstance())
                getCMCChart()
            }
            R.id.allTimeButton -> {
                setAllTimeChecked()
                getCMCChart()
            }
        }
    }

    private fun setUpChart() {
        val xAxis = chart.xAxis
        xAxis.setDrawAxisLine(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.setAvoidFirstLastClipping(true)
        chart.axisLeft.isEnabled = true
        chart.axisLeft.setDrawGridLines(false)
        chart.xAxis.setDrawGridLines(false)
        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = false
        chart.isDoubleTapToZoomEnabled = false
        chart.setScaleEnabled(false)
        chart.description.isEnabled = false
        chart.contentDescription = ""
        chart.setNoDataText(getString(R.string.noChartDataString))
        chart.setNoDataTextColor(R.color.darkRed)
        chart.setOnChartValueSelectedListener(this)
        chart.onChartGestureListener = object : OnChartGestureListener {
            override fun onChartGestureStart(me: MotionEvent, lastPerformedGesture: ChartTouchListener.ChartGesture) {
                val yAxis = chart.axisLeft
                // Allow scrolling in the right and left margins
                if (me.x > yAxis.longestLabel.length * yAxis.textSize && me.x < displayWidth - chart.viewPortHandler.offsetRight()) {
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
    }

    fun setUpLineDataSet(entries: List<Entry>): LineDataSet {
        val dataSet = LineDataSet(entries, "Price")
        dataSet.color = chartBorderColor
        dataSet.fillColor = chartFillColor
        dataSet.setDrawHighlightIndicators(true)
        dataSet.setDrawFilled(true)
        dataSet.setDrawCircles(true)
        dataSet.setCircleColor(chartBorderColor)
        dataSet.setDrawCircleHole(false)
        dataSet.setDrawValues(false)
        dataSet.circleRadius = 1f
        dataSet.highlightLineWidth = 2f
        dataSet.isHighlightEnabled = true
        dataSet.setDrawHighlightIndicators(true)
        dataSet.highLightColor = chartBorderColor
        return dataSet
    }

    private fun getCMCChart() {
        chart!!.isEnabled = true
        chart!!.clear()
        chartProgressSpinner!!.visibility = View.VISIBLE
        mActivity!!.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        apiManager = ApiManager.instance

        coinMarketCapFooterDataObservable = apiManager!!.getChartFooterData(currencyId!!)
        disposables = CompositeDisposable()
        disposables!!.add(coinMarketCapFooterDataObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<CoinMarketCapCoin>() {

                    override fun onNext(value: CoinMarketCapCoin) {
                        setTable(value)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                }))
        coinMarketCapDataObservable = apiManager!!.getChartData(String.format(CURRENT_CHART_URL, cryptoID))
        disposables = CompositeDisposable()
        disposables!!.add(coinMarketCapDataObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<CoinMarketCapChartData>() {

                    override fun onNext(value: CoinMarketCapChartData) {
                        val closePrices = ArrayList<Entry>()
                        for (priceTimeUnit in value.priceUsd!!) {
                            closePrices.add(Entry(priceTimeUnit[0], priceTimeUnit[1]))
                        }

                        if (closePrices.size == 0) {
                            chart!!.data = null
                            chart!!.isEnabled = false
                            chart!!.invalidate()
                            percent_change!!.text = ""
                            current_price!!.text = ""
                            chart!!.setNoDataText(getString(R.string.noChartDataString))
                            chartProgressSpinner!!.visibility = View.GONE
                            mActivity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            return
                        }
                        val xAxis = chart!!.xAxis
                        xAxis.textColor = textColor!!
                        chart!!.axisLeft.textColor = textColor!!
                        xAxis.valueFormatter = xAxisFormatter
                        val currPrice = closePrices[closePrices.size - 1].y
                        graphFragmentDateTextView!!.text = getFormattedFullDate(closePrices[closePrices.size - 1].x)
                        current_price!!.text = String.format(getString(R.string.unrounded_usd_chart_price_format), currPrice.toString())
                        current_price!!.setTextColor(Color.WHITE)
                        var firstPrice = closePrices[0].y
                        for (e in closePrices) {
                            if (firstPrice != 0f) {
                                break
                            } else {
                                firstPrice = e.y
                            }
                        }
                        val difference = currPrice - firstPrice
                        val percentChange = difference / firstPrice * 100
                        if (percentChange < 0) {
                            percent_change!!.text = String.format(getString(R.string.negative_variable_pct_change_with_dollars_format), currentTimeWindow, percentChange, Math.abs(difference))
                        } else {
                            percent_change!!.text = String.format(getString(R.string.positive_variable_pct_change_with_dollars_format), currentTimeWindow, percentChange, Math.abs(difference))
                        }
                        setColors(percentChange)
                        percent_change!!.setTextColor(percentageColor)
                        val dataSet = setUpLineDataSet(closePrices)
                        val lineData = LineData(dataSet)

                        chart!!.data = lineData
                        chart!!.animateX(800)
                        mActivity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        chartProgressSpinner!!.visibility = View.GONE
                    }

                    override fun onError(e: Throwable) {
                        chart!!.setNoDataText(getString(R.string.noChartDataString))
                        mActivity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        chartProgressSpinner!!.visibility = View.GONE
                    }

                    override fun onComplete() {}
                }))
    }


    private fun setDayChecked(cal: Calendar) {
        val endTime = cal.timeInMillis
        cal.add(Calendar.DAY_OF_YEAR, -1)
        val startTime = cal.timeInMillis
        cal.clear()
        CURRENT_CHART_URL = String.format(COIN_MARKET_CAP_CHART_URL_WINDOW, cryptoID, startTime, endTime)
        currentTimeWindow = getString(R.string.oneDay)
        xAxisFormatter = dayCommaTimeDateFormatter
    }

    private fun setWeekChecked(cal: Calendar) {
        val endTime = cal.timeInMillis
        cal.add(Calendar.DAY_OF_YEAR, -7)
        val startTime = cal.timeInMillis
        cal.clear()
        CURRENT_CHART_URL = String.format(COIN_MARKET_CAP_CHART_URL_WINDOW, cryptoID, startTime, endTime)
        currentTimeWindow = getString(R.string.Week)
        xAxisFormatter = monthSlashDayXAxisFormatter
    }

    private fun setMonthChecked(cal: Calendar) {
        val endTime = cal.timeInMillis
        cal.add(Calendar.MONTH, -1)
        val startTime = cal.timeInMillis
        cal.clear()
        CURRENT_CHART_URL = String.format(COIN_MARKET_CAP_CHART_URL_WINDOW, cryptoID, startTime, endTime)
        currentTimeWindow = getString(R.string.Month)
        xAxisFormatter = monthSlashDayXAxisFormatter
    }

    private fun setThreeMonthChecked(cal: Calendar) {
        val endTime = cal.timeInMillis
        cal.add(Calendar.MONTH, -3)
        val startTime = cal.timeInMillis
        cal.clear()
        CURRENT_CHART_URL = String.format(COIN_MARKET_CAP_CHART_URL_WINDOW, cryptoID, startTime, endTime)
        currentTimeWindow = getString(R.string.threeMonth)
        xAxisFormatter = monthSlashDayXAxisFormatter
    }

    private fun setYearChecked(cal: Calendar) {
        val endTime = cal.timeInMillis
        cal.add(Calendar.YEAR, -1)
        val startTime = cal.timeInMillis
        cal.clear()
        CURRENT_CHART_URL = String.format(COIN_MARKET_CAP_CHART_URL_WINDOW, cryptoID, startTime, endTime)
        currentTimeWindow = getString(R.string.Year)
        xAxisFormatter = monthSlashYearFormatter
    }

    private fun setAllTimeChecked() {
        currentTimeWindow = getString(R.string.AllTime)
        CURRENT_CHART_URL = String.format(COIN_MARKET_CAP_CHART_URL_ALL_DATA, cryptoID)
        xAxisFormatter = monthSlashYearFormatter
    }

    private fun setTable(coinObject: CoinMarketCapCoin) {
        val usdFormat = getString(R.string.usd_format)
        val negativePctFormat = getString(R.string.negative_pct_format)
        val positivePctFormat = getString(R.string.positive_pct_format)
        val negativeRedColor = ContextCompat.getColor(mActivity!!, R.color.percentNegativeRed)
        val positiveGreenColor = ContextCompat.getColor(mActivity!!, R.color.percentPositiveGreen)
        if (coinObject.data!!.name == null) {
            tableNameDataTextView!!.text = "N/A"
        } else {
            tableNameDataTextView!!.text = coinObject.data!!.name
        }

        if (coinObject.data!!.quotes!!.usd!!.price == null) {
            tablePriceUSDDataTextView!!.text = "N/A"
        } else {
            tablePriceUSDDataTextView!!.text = String.format(usdFormat, coinObject.data!!.quotes!!.usd!!.price)
        }

        // if (coinObject.getData().getQuotes().getUsd().getPrice() == null) {
        tablePriceBTCDataTextView!!.text = "N/A"
        //  } else {
        //      priceBTCTextView.setText(String.format(getString(R.string.btc_format), coinObject.getPrice_btc()));
        //  }

        if (coinObject.data!!.quotes!!.usd!!.volume24h == null) {
            tableVolUSDDataTextView!!.text = "N/A"
        } else {
            tableVolUSDDataTextView!!.text = String.format(usdFormat, coinObject.data!!.quotes!!.usd!!.volume24h)
        }

        if (coinObject.data!!.quotes!!.usd!!.marketCap == null) {
            tableMktCapDataTextView!!.text = "N/A"
        } else {
            tableMktCapDataTextView!!.text = String.format(usdFormat, coinObject.data!!.quotes!!.usd!!.marketCap)
        }

        if (coinObject.data!!.circulatingSupply == null) {
            tableAvailableSupplyDataTextView!!.text = "N/A"
        } else {
            tableAvailableSupplyDataTextView!!.text = rawNumberFormat.format(coinObject.data!!.circulatingSupply)
        }

        if (coinObject.data!!.totalSupply == null) {
            tableTotalSupplyDataTextView!!.text = "N/A"
        } else {
            tableTotalSupplyDataTextView!!.text = rawNumberFormat.format(coinObject.data!!.totalSupply)
        }

        if (coinObject.data!!.maxSupply == null) {
            tableMaxSupplyDataTextView!!.text = "N/A"
        } else {
            tableMaxSupplyDataTextView!!.text = rawNumberFormat.format(coinObject.data!!.maxSupply)
        }

        if (coinObject.data!!.quotes!!.usd!!.percentChange1h == null) {
            table1hrChangeDataTextView!!.text = "N/A"
        } else {
            val amount = coinObject.data!!.quotes!!.usd!!.percentChange1h!!
            if (amount >= 0) {
                table1hrChangeDataTextView!!.text = String.format(positivePctFormat, amount)
                table1hrChangeDataTextView!!.setTextColor(positiveGreenColor)
            } else {
                table1hrChangeDataTextView!!.text = String.format(negativePctFormat, amount)
                table1hrChangeDataTextView!!.setTextColor(negativeRedColor)
            }
        }

        if (coinObject.data!!.quotes!!.usd!!.percentChange24h == null) {
            table24hrChangeDataTextView!!.text = "N/A"
        } else {
            val amount = coinObject.data!!.quotes!!.usd!!.percentChange24h!!
            if (amount >= 0) {
                table24hrChangeDataTextView!!.text = String.format(positivePctFormat, amount)
                table24hrChangeDataTextView!!.setTextColor(positiveGreenColor)
            } else {
                table24hrChangeDataTextView!!.text = String.format(negativePctFormat, amount)
                table24hrChangeDataTextView!!.setTextColor(negativeRedColor)
            }
        }

        if (coinObject.data!!.quotes!!.usd!!.percentChange7d == null) {
            tableWeekChangeDataTextView!!.text = "N/A"
        } else {
            val amount = coinObject.data!!.quotes!!.usd!!.percentChange7d!!
            if (amount >= 0) {
                tableWeekChangeDataTextView!!.text = String.format(positivePctFormat, amount)
                tableWeekChangeDataTextView!!.setTextColor(positiveGreenColor)
            } else {
                tableWeekChangeDataTextView!!.text = String.format(negativePctFormat, amount)
                tableWeekChangeDataTextView!!.setTextColor(negativeRedColor)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private var textColor: Int? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Aesthetic.get()
                .colorAccent()
                .take(1)
                .subscribe { color ->
                    dayButton.markerColor = color
                    weekButton.markerColor = color
                    monthButton.markerColor = color
                    threeMonthButton.markerColor = color
                    yearButton.markerColor = color
                    allTimeButton.markerColor = color
                }.dispose()
        Aesthetic.get()
                .textColorPrimary()
                .take(1)
                .subscribe { color ->
                    textColor = color
                }.dispose()
        chartUSDPriceFormat = NumberFormat.getInstance()
        chartUSDPriceFormat.maximumFractionDigits = 10
        setUpChart()
        val displayMetrics = DisplayMetrics()
        mActivity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayWidth = displayMetrics.widthPixels
        cryptoID = "bitcoin"
        currencyId = "1"
        setDayChecked(Calendar.getInstance())
        getCMCChart()
        chart_interval_button_grp!!.check(R.id.dayButton)
        currentTimeWindow = getString(R.string.oneDay)
        chart_interval_button_grp!!.setOnCheckedChangeListener { _, checkedId ->
            Calendar.getInstance()
            when (checkedId) {
                R.id.dayButton -> {
                    setDayChecked(Calendar.getInstance())
                    getCMCChart()
                }
                R.id.weekButton -> {
                    setWeekChecked(Calendar.getInstance())
                    getCMCChart()
                }
                R.id.monthButton -> {
                    setMonthChecked(Calendar.getInstance())
                    getCMCChart()
                }
                R.id.threeMonthButton -> {
                    setThreeMonthChecked(Calendar.getInstance())
                    getCMCChart()
                }
                R.id.yearButton -> {
                    setYearChecked(Calendar.getInstance())
                    getCMCChart()
                }
                R.id.allTimeButton -> {
                    setAllTimeChecked()
                    getCMCChart()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_graph, container, false)
    }

    override fun onValueSelected(e: Entry, h: Highlight) {
        current_price!!.text = String.format(getString(R.string.unrounded_usd_chart_price_format), e.y.toString())
        graphFragmentDateTextView!!.text = getFormattedFullDate(e.x)
    }

    override fun onNothingSelected() {

    }

    fun getFormattedFullDate(unixSeconds: Float): String {
        val date = Date(unixSeconds.toLong())
        return fullDateFormat.format(date)
    }

    companion object {

        const val COIN_MARKET_CAP_CHART_URL_WINDOW = "https://graphs2.coinmarketcap.com/currencies/%s/%s/%s/"
        const val COIN_MARKET_CAP_CHART_URL_ALL_DATA = "https://graphs2.coinmarketcap.com/currencies/%s/"
        var CURRENT_CHART_URL: String = ""
        val rawNumberFormat = DecimalFormat("#,###.##")
    }
}

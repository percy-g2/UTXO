package com.androidevlinux.percy.UTXO.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.utils.UniqueArrayList
import java.text.MessageFormat

/**
 * Created by percy on 3/12/17.
 */

class ExchangePriceAdapter(private val priceBeanArrayList: UniqueArrayList, private val context: Context, private val mSwipeRefreshLayout: androidx.swiperefreshlayout.widget.SwipeRefreshLayout) : androidx.recyclerview.widget.RecyclerView.Adapter<ExchangePriceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.exchange_price_fragment_adapter_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val priceBean = priceBeanArrayList[position]
        holder.id.text = (position + 1).toString()
        holder.title.text = priceBean.title
        holder.price.text = MessageFormat.format("{0}{1}", context.resources.getString(R.string.price), priceBean.price)
        if (priceBean.title.matches(".*Pocketbits.*".toRegex()) || priceBean.title.matches(".*Coinbase.*".toRegex())) {
            holder.priceLow.text = MessageFormat.format("{0} {1}", context.resources.getString(R.string.sell), priceBean.low_price)
            holder.priceHigh.text = MessageFormat.format("{0} {1}", context.resources.getString(R.string.buy), priceBean.high_price)
        } else {
            holder.priceLow.text = MessageFormat.format("{0} {1}", context.resources.getString(R.string._24_hr_s_low), priceBean.low_price)
            holder.priceHigh.text = MessageFormat.format("{0} {1}", context.resources.getString(R.string._24_hr_s_high), priceBean.high_price)
        }
        when {
            priceBean.title.matches(".*BitMEX.*".toRegex()) -> holder.exchangeImage.background = ContextCompat.getDrawable(context, R.mipmap.ic_bitmex)
            priceBean.title.matches(".*Binance.*".toRegex()) -> holder.exchangeImage.background = ContextCompat.getDrawable(context, R.mipmap.ic_binance)
            priceBean.title.matches(".*Pocketbits.*".toRegex()) -> holder.exchangeImage.background = ContextCompat.getDrawable(context, R.mipmap.ic_pocketbits)
            priceBean.title.matches(".*Bitfinex.*".toRegex()) -> holder.exchangeImage.background = ContextCompat.getDrawable(context, R.mipmap.ic_bitfinex)
            priceBean.title.matches(".*Bitstamp.*".toRegex()) -> holder.exchangeImage.background = ContextCompat.getDrawable(context, R.mipmap.ic_bitstamp)
            priceBean.title.matches(".*Coinbase.*".toRegex()) -> holder.exchangeImage.background = ContextCompat.getDrawable(context, R.mipmap.ic_gdax)
            priceBean.title.matches(".*OKEx.*".toRegex()) -> holder.exchangeImage.background = ContextCompat.getDrawable(context, R.mipmap.ic_okex)
            priceBean.title.matches(".*WazirX.*".toRegex()) -> holder.exchangeImage.background = ContextCompat.getDrawable(context, R.mipmap.ic_wazirx_logo)
        }

        holder.cardView.setOnClickListener {
            when {
                priceBean.title.matches(".*BitMEX.*".toRegex()) -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse("https://www.bitmex.com/"))
                }
                priceBean.title.matches(".*Pocketbits.*".toRegex()) -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse("https://www.pocketbits.in/"))
                }
                priceBean.title.matches(".*Binance.*".toRegex()) -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse("https://www.binance.com/"))
                    startNewActivity("com.binance.dev")
                }
                priceBean.title.matches(".*OKEx.*".toRegex()) -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse("https://www.okex.com/"))
                    startNewActivity("com.okinc.okex")
                }
                priceBean.title.matches(".*Bitfinex.*".toRegex()) -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse("https://www.bitfinex.com/"))
                    startNewActivity("com.bitfinex.mobileapp")
                }
                priceBean.title.matches(".*Bitstamp.*".toRegex()) -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse("https://www.bitstamp.net/"))
                    startNewActivity("net.bitstamp.app")
                }
                priceBean.title.matches(".*Coinbase.*".toRegex()) -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse("https://pro.coinbase.com/"))
                }
                priceBean.title.matches(".*WazirX.*".toRegex()) -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse("https://wazirx.com/"))
                    startNewActivity("com.wrx.wazirx")
                }
            }
        }

        if (priceBeanArrayList.size == 3 && priceBean.title.matches(".*BCH.*".toRegex())) {
            if (mSwipeRefreshLayout.isRefreshing) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        } else if (priceBeanArrayList.size == 5 && priceBean.title.matches(".*ABC.*".toRegex())
                || priceBean.title.matches(".*SV.*".toRegex())) {
            if (mSwipeRefreshLayout.isRefreshing) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        } else if (priceBeanArrayList.size == 6 && priceBean.title.matches(".*TRX.*".toRegex())) {
            if (mSwipeRefreshLayout.isRefreshing) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        } else if (priceBeanArrayList.size == 7 && priceBean.title.matches(".*XRP.*".toRegex())) {
            if (mSwipeRefreshLayout.isRefreshing) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        } else if (priceBeanArrayList.size == 8 && priceBean.title.matches(".*BTC.*".toRegex()) || priceBean.title.matches(".*ETH.*".toRegex())
                || priceBean.title.matches(".*LTC.*".toRegex())) {
            if (mSwipeRefreshLayout.isRefreshing) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        }
    }


    private fun startNewActivity(packageName: String) {
        var intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=$packageName")
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return priceBeanArrayList.size
    }

    /**
     * View holder class
     */
    inner class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val cardView: androidx.cardview.widget.CardView = view.findViewById(R.id.cardView)
        val id: TextView = view.findViewById(R.id.id)
        val exchangeImage: AppCompatImageView = view.findViewById(R.id.exchangeImage)
        val title: TextView = view.findViewById(R.id.txt_title)
        val price: TextView = view.findViewById(R.id.txt_price)
        val priceLow: TextView = view.findViewById(R.id.txt_low_price)
        val priceHigh: TextView = view.findViewById(R.id.txt_high_price)

    }
}

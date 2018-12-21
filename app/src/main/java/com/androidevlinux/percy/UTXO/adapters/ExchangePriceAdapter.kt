package com.androidevlinux.percy.UTXO.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.utils.UniqueArrayList
import java.text.MessageFormat

/**
 * Created by percy on 3/12/17.
 */

class ExchangePriceAdapter(private val priceBeanArrayList: UniqueArrayList, private val context: Context, private val mSwipeRefreshLayout: SwipeRefreshLayout) : RecyclerView.Adapter<ExchangePriceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangePriceAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.exchange_price_fragment_adapter_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ExchangePriceAdapter.ViewHolder, position: Int) {
        val priceBean = priceBeanArrayList[position]
        holder.id.text = (position + 1).toString()
        holder.title.text = priceBean.title
        holder.price.text = MessageFormat.format("{0}{1}", context.resources.getString(R.string.price), priceBean.price)
        if (priceBean.title.matches(".*Zebpay.*".toRegex())
                || priceBean.title.matches(".*Pocketbits.*".toRegex())
                || priceBean.title.matches(".*Coinbase.*".toRegex())) {
            holder.priceLow.text = MessageFormat.format("{0} {1}", context.resources.getString(R.string.sell), priceBean.low_price)
            holder.priceHigh.text = MessageFormat.format("{0} {1}", context.resources.getString(R.string.buy), priceBean.high_price)
        } else {
            holder.priceLow.text = MessageFormat.format("{0} {1}", context.resources.getString(R.string._24_hr_s_low), priceBean.low_price)
            holder.priceHigh.text = MessageFormat.format("{0} {1}", context.resources.getString(R.string._24_hr_s_high), priceBean.high_price)
        }
        when {
            priceBean.title.matches(".*Pocketbits.*".toRegex()) -> holder.exchangeImage.background = context.resources.getDrawable(R.mipmap.ic_pocketbits)
            priceBean.title.matches(".*Bitfinex.*".toRegex()) -> holder.exchangeImage.background = context.resources.getDrawable(R.mipmap.ic_bitfinex)
            priceBean.title.matches(".*Bitstamp.*".toRegex()) -> holder.exchangeImage.background = context.resources.getDrawable(R.mipmap.ic_bitstamp)
            priceBean.title.matches(".*Coinbase.*".toRegex()) -> holder.exchangeImage.background = context.resources.getDrawable(R.mipmap.ic_gdax)
        }

        holder.cardView.setOnClickListener { view ->
            when {
                priceBean.title.matches(".*Pocketbits.*".toRegex()) -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse("https://www.pocketbits.in/"))
                }
                priceBean.title.matches(".*Bitfinex.*".toRegex()) -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse("https://www.bitfinex.com/"))
                    startNewActivity("com.bitfinex.bfxapp")
                }
                priceBean.title.matches(".*Bitstamp.*".toRegex()) -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse("https://www.bitstamp.net/"))
                    startNewActivity("net.bitstamp.bitstamp")
                }
                priceBean.title.matches(".*Coinbase.*".toRegex()) -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse("https://pro.coinbase.com/"))
                }
            }
        }

        if (priceBeanArrayList.size == 4 && !priceBean.title.matches(".*XRP.*".toRegex())) {
            if (mSwipeRefreshLayout.isRefreshing) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        } else if (priceBeanArrayList.size == 3 && priceBean.title.matches(".*XRP.*".toRegex())) {
            if (mSwipeRefreshLayout.isRefreshing) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        } else if (priceBeanArrayList.size == 2 && priceBean.title.matches(".*TRX.*".toRegex())) {
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
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cardView)
        val id: TextView = view.findViewById(R.id.id)
        val exchangeImage: AppCompatImageView = view.findViewById(R.id.exchangeImage)
        val title: TextView = view.findViewById(R.id.txt_title)
        val price: TextView = view.findViewById(R.id.txt_price)
        val priceLow: TextView = view.findViewById(R.id.txt_low_price)
        val priceHigh: TextView = view.findViewById(R.id.txt_high_price)

    }
}

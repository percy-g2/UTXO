package com.androidevlinux.percy.UTXO.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapCoin
import com.squareup.picasso.Picasso
import java.text.MessageFormat
import java.util.*


class GlobalPriceAdapter(private val coinMarketCapCoinArrayList: ArrayList<CoinMarketCapCoin>, private val context: Context, private val mSwipeRefreshLayout: androidx.swiperefreshlayout.widget.SwipeRefreshLayout) : androidx.recyclerview.widget.RecyclerView.Adapter<GlobalPriceAdapter.ViewHolder>() {
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlobalPriceAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.global_price_fragment_adapter_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: GlobalPriceAdapter.ViewHolder, position: Int) {
        val coinMarketCapCoin = coinMarketCapCoinArrayList[position]
        val animation = AnimationUtils.loadAnimation(
                context,
                if (position > lastPosition)
                    R.anim.up_from_bottom
                else
                    R.anim.down_from_top
        )
        holder.itemView.startAnimation(animation)
        lastPosition = holder.adapterPosition

        holder.id.text = (position + 1).toString()
        holder.title.text = coinMarketCapCoin.data!!.name
        holder.price.text = MessageFormat.format("{0}{1}", context.resources.getString(R.string.price) + " $", coinMarketCapCoin.data!!.quotes!!.usd!!.price)
        holder.txtVolume24h.text = MessageFormat.format("{0}{1}", context.resources.getString(R.string.volume24h) + "$", coinMarketCapCoin.data!!.quotes!!.usd!!.volume24h)

        holder.txt1hrPercentChange.text = MessageFormat.format("{0}{1}", coinMarketCapCoin.data!!.quotes!!.usd!!.percentChange1h, "% 1Hr")
        if (coinMarketCapCoin.data!!.quotes!!.usd!!.percentChange1h!! > 0) {
            holder.txt1hrPercentChange.setTextColor(ContextCompat.getColor(context, R.color.md_green))
        } else {
            holder.txt1hrPercentChange.setTextColor(ContextCompat.getColor(context, R.color.md_red))
        }

        holder.txt24hrPercentChange.text = MessageFormat.format("{0}{1}", coinMarketCapCoin.data!!.quotes!!.usd!!.percentChange24h!!, "% 24Hr")
        if (coinMarketCapCoin.data!!.quotes!!.usd!!.percentChange24h!! > 0) {
            holder.txt24hrPercentChange.setTextColor(ContextCompat.getColor(context, R.color.md_green))
        } else {
            holder.txt24hrPercentChange.setTextColor(ContextCompat.getColor(context, R.color.md_red))
        }

        holder.txt7dPercentChange.text = MessageFormat.format("{0}{1}", coinMarketCapCoin.data!!.quotes!!.usd!!.percentChange7d, "% 7d")
        if (coinMarketCapCoin.data!!.quotes!!.usd!!.percentChange7d!! > 0) {
            holder.txt7dPercentChange.setTextColor(ContextCompat.getColor(context, R.color.md_green))
        } else {
            holder.txt7dPercentChange.setTextColor(ContextCompat.getColor(context, R.color.md_red))
        }

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(R.color.md_blue,
                R.color.md_pink)
        circularProgressDrawable.start()
        Picasso.get().load("https://s2.coinmarketcap.com/generated/sparklines/web/7d/usd/" + coinMarketCapCoin.data!!.id.toString() + ".png").placeholder(circularProgressDrawable).into(holder.snapshotImage)
        Picasso.get().load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + coinMarketCapCoin.data!!.id.toString() + ".png").placeholder(circularProgressDrawable).into(holder.exchangeImage)

        // EOS is having dark black border icon which is not visible
        // in black background so add a white background color
        if (coinMarketCapCoin.data!!.id == 1765 || coinMarketCapCoin.data!!.id == 52 || coinMarketCapCoin.data!!.id == 1720 || coinMarketCapCoin.data!!.id == 1027) {
            holder.exchangeImage.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
        } else {
            holder.exchangeImage.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }

        val isExpanded = coinMarketCapCoin.isExpanded
        holder.expandedView.visibility = if (isExpanded) View.VISIBLE else View.GONE
        if (isExpanded) {
            holder.imageViewToggle.setImageResource(R.drawable.ic_expand_less)
        } else {
            holder.imageViewToggle.setImageResource(R.drawable.ic_expand_more)
        }
        holder.itemView.setOnClickListener {
            val isViewExpanded = coinMarketCapCoin.isExpanded
            if (isViewExpanded) {
                holder.expandedView.visibility = View.GONE
                coinMarketCapCoin.isExpanded = false
                holder.imageViewToggle.setImageResource(R.drawable.ic_expand_more)
            } else {
                holder.expandedView.visibility = View.VISIBLE
                coinMarketCapCoin.isExpanded = true
                holder.imageViewToggle.setImageResource(R.drawable.ic_expand_less)
            }
        }
        if (coinMarketCapCoinArrayList.size == 13) {
            if (mSwipeRefreshLayout.isRefreshing) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        }
    }


    override fun getItemCount(): Int {
        return coinMarketCapCoinArrayList.size
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
        val txtVolume24h: TextView = view.findViewById(R.id.txt_volume_24h)
        val txt1hrPercentChange: TextView = view.findViewById(R.id.txt_1hr_percent_change)
        val txt24hrPercentChange: TextView = view.findViewById(R.id.txt_24hr_percent_change)
        val txt7dPercentChange: TextView = view.findViewById(R.id.txt_7d_percent_change)
        val snapshotImage: AppCompatImageView = view.findViewById(R.id.snapshotImage)
        val expandedView: RelativeLayout = view.findViewById(R.id.expandView)
        val mainLayout: RelativeLayout = view.findViewById(R.id.mainLayout)
        val imageViewToggle: ImageView = view.findViewById(R.id.imageView_toggle)

    }
}


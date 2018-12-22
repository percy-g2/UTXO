package com.androidevlinux.percy.UTXO.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.data.models.newsapi.Article
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import java.util.*

class NewsAdapter(private val articleArrayList: ArrayList<Article>, private val context: Context, private val mSwipeRefreshLayout: androidx.swiperefreshlayout.widget.SwipeRefreshLayout) : androidx.recyclerview.widget.RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_fragment_adapter_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        val article = articleArrayList[position]
        holder.title.text = article.title
        holder.txtDescription.text = article.description
        holder.innerFrame.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(article.url))
        }
        if (null != article.urlToImage && !article.urlToImage!!.isEmpty()) {
            Picasso.get().load(article.urlToImage).into(holder.articleImage)
        }
        holder.txtAuthor.text = article.author
        holder.txtDate.text = article.publishedAt

        if (articleArrayList.size == 20) {
            if (mSwipeRefreshLayout.isRefreshing) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun getItemCount(): Int {
        return articleArrayList.size
    }

    /**
     * View holder class
     */
    inner class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val innerFrame: FrameLayout = view.findViewById(R.id.inner_frame)
        val articleImage: RoundedImageView = view.findViewById(R.id.discover_icon)
        val title: TextView = view.findViewById(R.id.txt_title)
        val txtDescription: TextView = view.findViewById(R.id.txt_description)
        val txtAuthor: TextView = view.findViewById(R.id.txt_author)
        val txtDate: TextView = view.findViewById(R.id.txt_date)

    }
}

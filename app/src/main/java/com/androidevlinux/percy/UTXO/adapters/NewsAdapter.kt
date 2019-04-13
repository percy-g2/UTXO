package com.androidevlinux.percy.UTXO.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.data.models.newsapi.Article
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val articleArrayList: ArrayList<Article>, private val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_fragment_adapter_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleArrayList[position]
        val animation = AnimationUtils.loadAnimation(
                context,
                if (position > lastPosition)
                    R.anim.up_from_bottom
                else
                    R.anim.down_from_top
        )
        holder.itemView.startAnimation(animation)
        lastPosition = holder.adapterPosition

        holder.title.text = article.title
        holder.txtDescription.text = article.description
        holder.innerFrame.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(article.url))
        }
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(R.color.md_blue,
                R.color.md_pink)
        circularProgressDrawable.start()
        if (null != article.urlToImage && article.urlToImage!!.isNotEmpty()) {
            Picasso.get().load(article.urlToImage).placeholder(circularProgressDrawable).into(holder.articleImage)
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        sdf.parse(article.publishedAt)
        val outputFormat = SimpleDateFormat("E, dd MMM yyyy HH:mm aaa", Locale.getDefault())
        val inputDateStr = article.publishedAt
        val date = sdf.parse(inputDateStr)
        val outputDateStr = outputFormat.format(date)


        holder.txtAuthor.text = article.author
        holder.txtDate.text = outputDateStr


    }

    override fun getItemCount(): Int {
        return articleArrayList.size
    }

    /**
     * View holder class
     */
    inner class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val innerFrame: CardView = view.findViewById(R.id.inner_frame)
        val articleImage: AppCompatImageView = view.findViewById(R.id.news_poster)
        val title: TextView = view.findViewById(R.id.txt_title)
        val txtDescription: TextView = view.findViewById(R.id.txt_description)
        val txtAuthor: TextView = view.findViewById(R.id.txt_author)
        val txtDate: TextView = view.findViewById(R.id.txt_date)

    }
}

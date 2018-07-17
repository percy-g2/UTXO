package com.androidevlinux.percy.UTXO.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androidevlinux.percy.UTXO.R;
import com.androidevlinux.percy.UTXO.data.models.newsapi.Article;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<Article> articleArrayList;
    private Context context;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public NewsAdapter(ArrayList<Article> articleArrayList, Context context, SwipeRefreshLayout mSwipeRefreshLayout) {
        this.context = context;
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        this.articleArrayList = articleArrayList;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_fragment_adapter_row, parent, false);
        return new NewsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.ViewHolder holder, int position) {
        Article article = articleArrayList.get(position);
        holder.title.setText(article.getTitle());
        holder.txt_description.setText(article.getDescription());
        holder.inner_frame.setOnClickListener(view -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(context, Uri.parse(article.getUrl()));
        });
        if (null != article.getUrlToImage() && !article.getUrlToImage().isEmpty()) {
            Picasso.get().load(article.getUrlToImage()).into(holder.articleImage);
        }
        holder.txt_author.setText(article.getAuthor());
        holder.txt_date.setText(article.getPublishedAt());

        if (articleArrayList.size() == 20) {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    /**
     * View holder class
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout inner_frame;
        private RoundedImageView articleImage;
        private TextView title;
        private TextView txt_description;
        private TextView txt_author;
        private TextView txt_date;

        private ViewHolder(View view) {
            super(view);
            inner_frame = view.findViewById(R.id.inner_frame);
            articleImage = view.findViewById(R.id.discover_icon);
            title = view.findViewById(R.id.txt_title);
            txt_description = view.findViewById(R.id.txt_description);
            txt_author = view.findViewById(R.id.txt_author);
            txt_date = view.findViewById(R.id.txt_date);
        }
    }
}

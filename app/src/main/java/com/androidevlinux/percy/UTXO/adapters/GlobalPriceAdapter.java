package com.androidevlinux.percy.UTXO.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidevlinux.percy.UTXO.R;
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapCoin;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;

public class GlobalPriceAdapter extends RecyclerView.Adapter<GlobalPriceAdapter.ViewHolder> {

    private ArrayList<CoinMarketCapCoin> coinMarketCapCoinArrayList;
    private Context context;
    private RecyclerView recyclerView;
    private int mExpandedPosition = -1;

    public GlobalPriceAdapter(ArrayList<CoinMarketCapCoin> coinMarketCapCoinArrayList, Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.coinMarketCapCoinArrayList = coinMarketCapCoinArrayList;
    }

    @NonNull
    @Override
    public GlobalPriceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.global_price_fragment_adapter_row, parent, false);
        return new GlobalPriceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final GlobalPriceAdapter.ViewHolder holder, int position) {
        CoinMarketCapCoin coinMarketCapCoin = coinMarketCapCoinArrayList.get(position);
        holder.id.setText(String.valueOf(position + 1));
        holder.title.setText(coinMarketCapCoin.getData().getName());
        holder.price.setText(MessageFormat.format("{0}{1}", context.getResources().getString(R.string.price) + " $", coinMarketCapCoin.getData().getQuotes().getUSD().getPrice()));
        holder.txt_volume_24h.setText(MessageFormat.format("{0}{1}", context.getResources().getString(R.string.volume24h) + "$", coinMarketCapCoin.getData().getQuotes().getUSD().getVolume24h()));

        holder.txt_1hr_percent_change.setText(MessageFormat.format("{0}{1}", coinMarketCapCoin.getData().getQuotes().getUSD().getPercentChange1h(), "% 1Hr"));
        if (coinMarketCapCoin.getData().getQuotes().getUSD().getPercentChange1h() > 0) {
            holder.txt_1hr_percent_change.setTextColor(context.getResources().getColor(R.color.md_green_900));
        } else {
            holder.txt_1hr_percent_change.setTextColor(context.getResources().getColor(R.color.md_red_900));
        }

        holder.txt_24hr_percent_change.setText(MessageFormat.format("{0}{1}", coinMarketCapCoin.getData().getQuotes().getUSD().getPercentChange24h(), "% 24Hr"));
        if (coinMarketCapCoin.getData().getQuotes().getUSD().getPercentChange24h() > 0) {
            holder.txt_24hr_percent_change.setTextColor(context.getResources().getColor(R.color.md_green_900));
        } else {
            holder.txt_24hr_percent_change.setTextColor(context.getResources().getColor(R.color.md_red_900));
        }

        holder.txt_7d_percent_change.setText(MessageFormat.format("{0}{1}", coinMarketCapCoin.getData().getQuotes().getUSD().getPercentChange7d(), "% 7d"));
        if (coinMarketCapCoin.getData().getQuotes().getUSD().getPercentChange7d() > 0) {
            holder.txt_7d_percent_change.setTextColor(context.getResources().getColor(R.color.md_green_900));
        } else {
            holder.txt_7d_percent_change.setTextColor(context.getResources().getColor(R.color.md_red_900));
        }

        Picasso.get().load("https://s2.coinmarketcap.com/generated/sparklines/web/7d/usd/" + String.valueOf(coinMarketCapCoin.getData().getId()) + ".png").into(holder.snapshotImage);
        Picasso.get().load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + String.valueOf(coinMarketCapCoin.getData().getId()) + ".png").into(holder.exchangeImage);
        final boolean isExpanded = position == mExpandedPosition;
        holder.expandedView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(v -> {
            boolean shouldExpand = holder.expandedView.getVisibility() == View.GONE;
            mExpandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
            ChangeBounds transition = new ChangeBounds();
            transition.setDuration(125);

            if (shouldExpand) {
                holder.expandedView.setVisibility(View.VISIBLE);
                holder.imageView_toggle.setImageResource(R.drawable.ic_expand_less);
            } else {
                holder.expandedView.setVisibility(View.GONE);
                holder.imageView_toggle.setImageResource(R.drawable.ic_expand_more);
            }

            TransitionManager.beginDelayedTransition(recyclerView, transition);
            holder.itemView.setActivated(shouldExpand);
        });
    }


    @Override
    public int getItemCount() {
        return coinMarketCapCoinArrayList.size();
    }

    /**
     * View holder class
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView id;
        private AppCompatImageView exchangeImage;
        private TextView title;
        private TextView price;
        private TextView txt_volume_24h;
        private TextView txt_1hr_percent_change;
        private TextView txt_24hr_percent_change;
        private TextView txt_7d_percent_change;
        private AppCompatImageView snapshotImage;
        private LinearLayout expandedView;
        private ImageView imageView_toggle;

        private ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardView);
            expandedView = view.findViewById(R.id.expandView);
            imageView_toggle = view.findViewById(R.id.imageView_toggle);
            id = view.findViewById(R.id.id);
            exchangeImage = view.findViewById(R.id.exchangeImage);
            title = view.findViewById(R.id.txt_title);
            price = view.findViewById(R.id.txt_price);
            txt_volume_24h = view.findViewById(R.id.txt_volume_24h);
            txt_1hr_percent_change = view.findViewById(R.id.txt_1hr_percent_change);
            txt_24hr_percent_change = view.findViewById(R.id.txt_24hr_percent_change);
            txt_7d_percent_change = view.findViewById(R.id.txt_7d_percent_change);
            snapshotImage = view.findViewById(R.id.snapshotImage);
        }
    }
}


package com.androidevlinux.percy.UTXO.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidevlinux.percy.UTXO.R;
import com.androidevlinux.percy.UTXO.data.models.price.PriceBean;
import com.androidevlinux.percy.UTXO.utils.UniqueArrayList;

import java.text.MessageFormat;

/**
 * Created by percy on 3/12/17.
 */

public class ExchangePriceAdapter extends RecyclerView.Adapter<ExchangePriceAdapter.ViewHolder> {

    private UniqueArrayList priceBeanArrayList;
    private Context context;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public ExchangePriceAdapter(UniqueArrayList priceBeanArrayList, Context context, SwipeRefreshLayout mSwipeRefreshLayout) {
        this.context = context;
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        this.priceBeanArrayList = priceBeanArrayList;
    }

    @NonNull
    @Override
    public ExchangePriceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exchange_price_fragment_adapter_row, parent, false);
        return new ExchangePriceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExchangePriceAdapter.ViewHolder holder, int position) {
        PriceBean priceBean = priceBeanArrayList.get(position);
        holder.id.setText(String.valueOf(position + 1));
        holder.title.setText(priceBean.getTitle());
        holder.price.setText(MessageFormat.format("{0}{1}", context.getResources().getString(R.string.price), priceBean.getPrice()));
        if (priceBean.getTitle().matches(".*Zebpay.*")
                || priceBean.getTitle().matches(".*Pocketbits.*")
                || priceBean.getTitle().matches(".*GDAX.*")) {
            holder.price_low.setText(MessageFormat.format("{0} {1}", context.getResources().getString(R.string.sell), priceBean.getLow_price()));
            holder.price_high.setText(MessageFormat.format("{0} {1}", context.getResources().getString(R.string.buy), priceBean.getHigh_price()));
        } else {
            holder.price_low.setText(MessageFormat.format("{0} {1}", context.getResources().getString(R.string._24_hr_s_low), priceBean.getLow_price()));
            holder.price_high.setText(MessageFormat.format("{0} {1}", context.getResources().getString(R.string._24_hr_s_high), priceBean.getHigh_price()));
        }
        if (priceBean.getTitle().matches(".*Zebpay.*")) {
            holder.exchangeImage.setBackground(context.getResources().getDrawable(R.mipmap.ic_zebpay));
        } else if (priceBean.getTitle().matches(".*Pocketbits.*")) {
            holder.exchangeImage.setBackground(context.getResources().getDrawable(R.mipmap.ic_pocketbits));
        } else if (priceBean.getTitle().matches(".*Bitfinex.*")) {
            holder.exchangeImage.setBackground(context.getResources().getDrawable(R.mipmap.ic_bitfinex));
        } else if (priceBean.getTitle().matches(".*Bitstamp.*")) {
            holder.exchangeImage.setBackground(context.getResources().getDrawable(R.mipmap.ic_bitstamp));
        } else if (priceBean.getTitle().matches(".*GDAX.*")) {
            holder.exchangeImage.setBackground(context.getResources().getDrawable(R.mipmap.ic_gdax));
        }

        holder.cardView.setOnClickListener(view -> {
            if (priceBean.getTitle().matches(".*Zebpay.*")) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse("https://www.zebpay.com/"));
                startNewActivity("zebpay.Application");
            } else if (priceBean.getTitle().matches(".*Pocketbits.*")) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse("https://www.pocketbits.in/"));
            } else if (priceBean.getTitle().matches(".*Bitfinex.*")) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse("https://www.bitfinex.com/"));
                startNewActivity("com.bitfinex.bfxapp");
            } else if (priceBean.getTitle().matches(".*Bitstamp.*")) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse("https://www.bitstamp.net/"));
                startNewActivity("net.bitstamp.bitstamp");
            } else if (priceBean.getTitle().matches(".*GDAX.*")) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse("https://www.gdax.com/"));
            }
        });

        if (priceBeanArrayList.size() == 5 && !priceBean.getTitle().matches(".*XRP.*")) {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        } else if (priceBeanArrayList.size() == 4 && priceBean.getTitle().matches(".*XRP.*")) {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }


    private void startNewActivity(String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return priceBeanArrayList.size();
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
        private TextView price_low;
        private TextView price_high;

        private ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardView);
            id = view.findViewById(R.id.id);
            exchangeImage = view.findViewById(R.id.exchangeImage);
            title = view.findViewById(R.id.txt_title);
            price = view.findViewById(R.id.txt_price);
            price_low = view.findViewById(R.id.txt_low_price);
            price_high = view.findViewById(R.id.txt_high_price);
        }
    }
}

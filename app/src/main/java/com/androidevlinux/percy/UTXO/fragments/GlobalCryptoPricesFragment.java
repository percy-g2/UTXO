package com.androidevlinux.percy.UTXO.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.androidevlinux.percy.UTXO.R;
import com.androidevlinux.percy.UTXO.adapters.GlobalPriceAdapter;
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapCoin;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class GlobalCryptoPricesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    Unbinder unbinder;
    @BindView(R.id.price_list_recycler_view)
    RecyclerView priceListRecyclerView;
    Observable<CoinMarketCapCoin> coinMarketCapCoinObservable;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    CompositeDisposable disposables;
    ArrayList<CoinMarketCapCoin> coinMarketCapCoinArrayList;
    GlobalPriceAdapter globalPriceAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crypto_prices, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        coinMarketCapCoinArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        priceListRecyclerView.setLayoutManager(linearLayoutManager);
        globalPriceAdapter = new GlobalPriceAdapter(coinMarketCapCoinArrayList, mActivity, priceListRecyclerView, mSwipeRefreshLayout);
        priceListRecyclerView.setAdapter(globalPriceAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));
        new refreshTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (disposables != null) {
            disposables.dispose();
        }
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        disposables.dispose();
        new refreshTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void getData(String currencyId) {
        coinMarketCapCoinObservable = apiManager.getChartFooterData(currencyId);
        disposables = new CompositeDisposable();
        disposables.add(coinMarketCapCoinObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CoinMarketCapCoin>() {

                    @Override
                    public void onNext(CoinMarketCapCoin value) {
                        coinMarketCapCoinArrayList.add(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Collections.sort(coinMarketCapCoinArrayList, (coinMarketCapCoin, t1) -> {
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            return coinMarketCapCoin.getData().getRank() > t1.getData().getRank() ? -1 : (coinMarketCapCoin.getData().getRank() < t1.getData().getRank()) ? 1 : 0;
                        });
                        Collections.reverse(coinMarketCapCoinArrayList);
                        globalPriceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    @SuppressLint("StaticFieldLeak")
    private class refreshTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
            coinMarketCapCoinArrayList.clear();
        }

        @Override
        protected String doInBackground(String... value) {
            String[] arrayOfCurrencyIds = getResources().getStringArray(R.array.arrayOfCurrencyIds);
            for (String id : arrayOfCurrencyIds) {
                getData(id);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String value) {
            globalPriceAdapter.notifyDataSetChanged();
        }
    }
}

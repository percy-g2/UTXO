package com.androidevlinux.percy.UTXO.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.androidevlinux.percy.UTXO.R;
import com.androidevlinux.percy.UTXO.activities.MainActivity;
import com.androidevlinux.percy.UTXO.adapters.ExchangePriceAdapter;
import com.androidevlinux.percy.UTXO.data.models.bitfinex.BitfinexPubTickerResponseBean;
import com.androidevlinux.percy.UTXO.data.models.bitstamp.BitstampBean;
import com.androidevlinux.percy.UTXO.data.models.gdax.GDAX;
import com.androidevlinux.percy.UTXO.data.models.pocketbits.PocketBitsBean;
import com.androidevlinux.percy.UTXO.data.models.price.PriceBean;
import com.androidevlinux.percy.UTXO.data.models.zebpay.ZebPayBean;
import com.androidevlinux.percy.UTXO.utils.Constants;
import com.androidevlinux.percy.UTXO.utils.UniqueArrayList;

import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ExchangeCryptoPricesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, PopupMenu.OnMenuItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.price_list_recycler_view)
    RecyclerView priceListRecyclerView;
    String strRuppeSymbol = "\u20B9", strDollarSymbol = "$";
    UniqueArrayList priceBeanArrayList;
    ExchangePriceAdapter priceAdapter;
    Observable<BitfinexPubTickerResponseBean> bitfinexPubTickerResponseBeanObservable;
    Observable<BitstampBean> bitstampObservable;
    Observable<ZebPayBean> zebPayBeanObservable;
    Observable<PocketBitsBean> pocketBitsBeanObservable;
    Observable<GDAX> gdaxObservable;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    protected String selectedCurrency = "BTC";
    CompositeDisposable disposables;

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
        priceBeanArrayList = new UniqueArrayList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        priceListRecyclerView.setLayoutManager(linearLayoutManager);
        priceAdapter = new ExchangePriceAdapter(priceBeanArrayList, mActivity, mSwipeRefreshLayout);
        priceListRecyclerView.setAdapter(priceAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));
        Constants.currentCurrency = selectedCurrency;
        new refreshBtcTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crypto_prices, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_crypto_prices_menu_item:
                View menuItemView = Objects.requireNonNull(getActivity()).findViewById(R.id.filter_crypto_prices_menu_item);
                showPopupMenu(menuItemView);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(Objects.requireNonNull(getActivity()), v);
        popup.setOnMenuItemClickListener(ExchangeCryptoPricesFragment.this);

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.crypto_prices_filter_popup, popup.getMenu());
        popup.show();
    }

    /* from PopupMenu.OnMenuItemClickListener */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        ((MainActivity) Objects.requireNonNull(getActivity())).hideNavigationBar();
        switch (item.getItemId()) {
            case R.id.btc_filter_option:
                disposables.dispose();
                selectedCurrency = "BTC";
                Constants.currentCurrency = selectedCurrency;
                new refreshBtcTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                mActivity.setTitle(getString(R.string.nav_crypto_prices_btc));
                return true;
            case R.id.bch_filter_option:
                disposables.dispose();
                selectedCurrency = "BCH";
                Constants.currentCurrency = selectedCurrency;
                new refreshBchTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                mActivity.setTitle(getString(R.string.nav_crypto_prices_bch));
                return true;
            case R.id.eth_filter_option:
                disposables.dispose();
                selectedCurrency = "ETH";
                Constants.currentCurrency = selectedCurrency;
                new refreshEthTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                mActivity.setTitle(getString(R.string.nav_crypto_prices_eth));
                return true;
            case R.id.ltc_filter_option:
                disposables.dispose();
                selectedCurrency = "LTC";
                Constants.currentCurrency = selectedCurrency;
                new refreshLtcTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                mActivity.setTitle(getString(R.string.nav_crypto_prices_ltc));
                return true;
            case R.id.xrp_filter_option:
                disposables.dispose();
                selectedCurrency = "XRP";
                Constants.currentCurrency = selectedCurrency;
                new refreshXrpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                mActivity.setTitle(getString(R.string.nav_crypto_prices_xrp));
                return true;
            default:
                return false;
        }
    }

    public static String rupeeFormat(String value) {
        value = value.replace(",", "");
        char lastDigit = value.charAt(value.length() - 1);
        StringBuilder result = new StringBuilder();
        int len = value.length() - 1;
        int nDigits = 0;

        for (int i = len - 1; i >= 0; i--) {
            result.insert(0, value.charAt(i));
            nDigits++;
            if (((nDigits % 2) == 0) && (i > 0)) {
                result.insert(0, ",");
            }
        }
        return (result.toString() + lastDigit);
    }

    private void getGdaxEthTicker() {
        gdaxObservable = apiManager.getData("ETH-USD");
        disposables = new CompositeDisposable();
        disposables.add(gdaxObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GDAX>() {

                    @Override
                    public void onNext(GDAX value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("GDAX (ETH)");
                        priceBean.setPrice(strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(value.getPrice())));
                        priceBean.setLow_price(strDollarSymbol + value.getBid());
                        priceBean.setHigh_price(strDollarSymbol + value.getAsk());
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getGdaxLtcTicker() {
        gdaxObservable = apiManager.getData("LTC-USD");
        disposables = new CompositeDisposable();
        disposables.add(gdaxObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GDAX>() {

                    @Override
                    public void onNext(GDAX value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("GDAX (LTC)");
                        priceBean.setPrice(strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(value.getPrice())));
                        priceBean.setLow_price(strDollarSymbol + value.getBid());
                        priceBean.setHigh_price(strDollarSymbol + value.getAsk());
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }


    private void getGdaxBchTicker() {
        gdaxObservable = apiManager.getData("BCH-USD");
        disposables = new CompositeDisposable();
        disposables.add(gdaxObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GDAX>() {

                    @Override
                    public void onNext(GDAX value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("GDAX (BCH)");
                        priceBean.setPrice(strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(value.getPrice())));
                        priceBean.setLow_price(strDollarSymbol + value.getBid());
                        priceBean.setHigh_price(strDollarSymbol + value.getAsk());
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }
    private void getGdaxBtcTicker() {
        gdaxObservable = apiManager.getData("BTC-USD");
        disposables = new CompositeDisposable();
        disposables.add(gdaxObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GDAX>() {

                    @Override
                    public void onNext(GDAX value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("GDAX (BTC)");
                        priceBean.setPrice(strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(value.getPrice())));
                        priceBean.setLow_price(strDollarSymbol + value.getBid());
                        priceBean.setHigh_price(strDollarSymbol + value.getAsk());
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getBitfinexPubTicker() {
        bitfinexPubTickerResponseBeanObservable = apiManager.getBitfinexTicker("btcusd");
        disposables = new CompositeDisposable();
        disposables.add(bitfinexPubTickerResponseBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BitfinexPubTickerResponseBean>() {

                    @Override
                    public void onNext(BitfinexPubTickerResponseBean value) {
                        Constants.btc_price = value.getLastPrice();
                        Constants.btc_price_low = value.getLow();
                        Constants.btc_price_high = value.getHigh();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Bitfinex (BTC)");
                        priceBean.setPrice(strDollarSymbol + Constants.btc_price);
                        priceBean.setLow_price(strDollarSymbol + Constants.btc_price_low);
                        priceBean.setHigh_price(strDollarSymbol + Constants.btc_price_high);
                        priceBeanArrayList.add(priceBean);
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getBitfinexPubEthTicker() {
        bitfinexPubTickerResponseBeanObservable = apiManager.getBitfinexTicker("ethusd");
        disposables = new CompositeDisposable();
        disposables.add(bitfinexPubTickerResponseBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BitfinexPubTickerResponseBean>() {

                    @Override
                    public void onNext(BitfinexPubTickerResponseBean value) {
                        Constants.btc_price = value.getLastPrice();
                        Constants.btc_price_low = value.getLow();
                        Constants.btc_price_high = value.getHigh();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Bitfinex (ETH)");
                        priceBean.setPrice(strDollarSymbol + Constants.btc_price);
                        priceBean.setLow_price(strDollarSymbol + Constants.btc_price_low);
                        priceBean.setHigh_price(strDollarSymbol + Constants.btc_price_high);
                        priceBeanArrayList.add(priceBean);
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getBitfinexPubLtcTicker() {
        bitfinexPubTickerResponseBeanObservable = apiManager.getBitfinexTicker("ltcusd");
        disposables = new CompositeDisposable();
        disposables.add(bitfinexPubTickerResponseBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BitfinexPubTickerResponseBean>() {

                    @Override
                    public void onNext(BitfinexPubTickerResponseBean value) {
                        Constants.btc_price = value.getLastPrice();
                        Constants.btc_price_low = value.getLow();
                        Constants.btc_price_high = value.getHigh();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Bitfinex (LTC)");
                        priceBean.setPrice(strDollarSymbol + Constants.btc_price);
                        priceBean.setLow_price(strDollarSymbol + Constants.btc_price_low);
                        priceBean.setHigh_price(strDollarSymbol + Constants.btc_price_high);
                        priceBeanArrayList.add(priceBean);
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getBitfinexPubBchTicker() {
        bitfinexPubTickerResponseBeanObservable = apiManager.getBitfinexTicker("bchusd");
        disposables = new CompositeDisposable();
        disposables.add(bitfinexPubTickerResponseBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BitfinexPubTickerResponseBean>() {

                    @Override
                    public void onNext(BitfinexPubTickerResponseBean value) {
                        Constants.btc_price = value.getLastPrice();
                        Constants.btc_price_low = value.getLow();
                        Constants.btc_price_high = value.getHigh();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Bitfinex (BCH)");
                        priceBean.setPrice(strDollarSymbol + Constants.btc_price);
                        priceBean.setLow_price(strDollarSymbol + Constants.btc_price_low);
                        priceBean.setHigh_price(strDollarSymbol + Constants.btc_price_high);
                        priceBeanArrayList.add(priceBean);
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getBitfinexXrpPubTicker() {
        bitfinexPubTickerResponseBeanObservable = apiManager.getBitfinexTicker("xrpusd");
        disposables = new CompositeDisposable();
        disposables.add(bitfinexPubTickerResponseBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BitfinexPubTickerResponseBean>() {

                    @Override
                    public void onNext(BitfinexPubTickerResponseBean value) {
                        Constants.btc_price = value.getLastPrice();
                        Constants.btc_price_low = value.getLow();
                        Constants.btc_price_high = value.getHigh();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Bitfinex (XRP)");
                        priceBean.setPrice(strDollarSymbol + Constants.btc_price);
                        priceBean.setLow_price(strDollarSymbol + Constants.btc_price_low);
                        priceBean.setHigh_price(strDollarSymbol + Constants.btc_price_high);
                        priceBeanArrayList.add(priceBean);
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getBitStampEthTicker() {
        bitstampObservable = apiManager.getBitstampTicker("ethusd");
        disposables = new CompositeDisposable();
        disposables.add(bitstampObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BitstampBean>() {

                    @Override
                    public void onNext(BitstampBean value) {
                        Constants.btc_price = String.valueOf(value.getLast()).replaceAll("^\"|\"$", "");
                        Constants.btc_price_low = String.valueOf(value.getLow()).replaceAll("^\"|\"$", "");
                        Constants.btc_price_high = String.valueOf(value.getHigh()).replaceAll("^\"|\"$", "");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Bitstamp (ETH)");
                        priceBean.setPrice(strDollarSymbol + Constants.btc_price);
                        priceBean.setLow_price(strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(Constants.btc_price_low)));
                        priceBean.setHigh_price(strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(Constants.btc_price_high)));
                        priceBeanArrayList.add(priceBean);
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getBitStampXrpTicker() {
        bitstampObservable = apiManager.getBitstampTicker("xrpusd");
        disposables = new CompositeDisposable();
        disposables.add(bitstampObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BitstampBean>() {

                    @Override
                    public void onNext(BitstampBean value) {
                        Constants.btc_price = String.valueOf(value.getLast()).replaceAll("^\"|\"$", "");
                        Constants.btc_price_low = String.valueOf(value.getLow()).replaceAll("^\"|\"$", "");
                        Constants.btc_price_high = String.valueOf(value.getHigh()).replaceAll("^\"|\"$", "");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Bitstamp (XRP)");
                        priceBean.setPrice(strDollarSymbol + Constants.btc_price);
                        priceBean.setLow_price(strDollarSymbol + Constants.btc_price_low);
                        priceBean.setHigh_price(strDollarSymbol + Constants.btc_price_high);
                        priceBeanArrayList.add(priceBean);
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getBitStampLtcTicker() {
        bitstampObservable = apiManager.getBitstampTicker("ltcusd");
        disposables = new CompositeDisposable();
        disposables.add(bitstampObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BitstampBean>() {

                    @Override
                    public void onNext(BitstampBean value) {
                        Constants.btc_price = String.valueOf(value.getLast()).replaceAll("^\"|\"$", "");
                        Constants.btc_price_low = String.valueOf(value.getLow()).replaceAll("^\"|\"$", "");
                        Constants.btc_price_high = String.valueOf(value.getHigh()).replaceAll("^\"|\"$", "");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Bitstamp (LTC)");
                        priceBean.setPrice(strDollarSymbol + Constants.btc_price);
                        priceBean.setLow_price(strDollarSymbol + Constants.btc_price_low);
                        priceBean.setHigh_price(strDollarSymbol + Constants.btc_price_high);
                        priceBeanArrayList.add(priceBean);
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }


    private void getBitStampBchTicker() {
        bitstampObservable = apiManager.getBitstampTicker("bchusd");
        disposables = new CompositeDisposable();
        disposables.add(bitstampObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BitstampBean>() {

                    @Override
                    public void onNext(BitstampBean value) {
                        Constants.btc_price = String.valueOf(value.getLast()).replaceAll("^\"|\"$", "");
                        Constants.btc_price_low = String.valueOf(value.getLow()).replaceAll("^\"|\"$", "");
                        Constants.btc_price_high = String.valueOf(value.getHigh()).replaceAll("^\"|\"$", "");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Bitstamp (BCH)");
                        priceBean.setPrice(strDollarSymbol + Constants.btc_price);
                        priceBean.setLow_price(strDollarSymbol + Constants.btc_price_low);
                        priceBean.setHigh_price(strDollarSymbol + Constants.btc_price_high);
                        priceBeanArrayList.add(priceBean);
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getBitStampTicker() {
        bitstampObservable = apiManager.getBitstampTicker("btcusd");
        disposables = new CompositeDisposable();
        disposables.add(bitstampObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BitstampBean>() {

                    @Override
                    public void onNext(BitstampBean value) {
                        Constants.btc_price = String.valueOf(value.getLast()).replaceAll("^\"|\"$", "");
                        Constants.btc_price_low = String.valueOf(value.getLow()).replaceAll("^\"|\"$", "");
                        Constants.btc_price_high = String.valueOf(value.getHigh()).replaceAll("^\"|\"$", "");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Bitstamp (BTC)");
                        priceBean.setPrice(strDollarSymbol + Constants.btc_price);
                        priceBean.setLow_price(strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(Constants.btc_price_low)));
                        priceBean.setHigh_price(strDollarSymbol + String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(Constants.btc_price_high)));
                        priceBeanArrayList.add(priceBean);
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getZebpayTicker() {
        zebPayBeanObservable = apiManager.getZebpayTicker("BTC");
        disposables = new CompositeDisposable();
        disposables.add(zebPayBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ZebPayBean>() {

                    @Override
                    public void onNext(ZebPayBean value) {
                        Constants.btc_price = String.valueOf(value.getBuy());
                        Constants.btc_price_low = String.valueOf(value.getSell());
                        Constants.btc_price_high = String.valueOf(value.getBuy());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Zebpay (BTC)");
                        priceBean.setPrice(strRuppeSymbol + rupeeFormat(Constants.btc_price.substring(0, Constants.btc_price.length() - 2)));
                        priceBean.setLow_price(strRuppeSymbol + rupeeFormat(Constants.btc_price_low.substring(0, Constants.btc_price_low.length() - 2)));
                        priceBean.setHigh_price(strRuppeSymbol + rupeeFormat(Constants.btc_price_high.substring(0, Constants.btc_price_high.length() - 2)));
                        priceBeanArrayList.add(priceBean);
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getPocketbitsTicker() {
        pocketBitsBeanObservable = apiManager.getPocketbitsTicker();
        disposables = new CompositeDisposable();
        disposables.add(pocketBitsBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PocketBitsBean>() {

                    @Override
                    public void onNext(PocketBitsBean value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Pocketbits (BTC)");
                        priceBean.setPrice(strRuppeSymbol + String.valueOf(value.getAltcoins().get(0).getAltBuyPrice()));
                        priceBean.setLow_price(strRuppeSymbol + String.valueOf(value.getAltcoins().get(0).getAltSellPrice()));
                        priceBean.setHigh_price(strRuppeSymbol + String.valueOf(value.getAltcoins().get(0).getAltBuyPrice()));
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getPocketbitsLtcTicker() {
        pocketBitsBeanObservable = apiManager.getPocketbitsTicker();
        disposables = new CompositeDisposable();
        disposables.add(pocketBitsBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PocketBitsBean>() {

                    @Override
                    public void onNext(PocketBitsBean value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Pocketbits (LTC)");
                        priceBean.setPrice(strRuppeSymbol + String.valueOf(value.getAltcoins().get(4).getAltBuyPrice()));
                        priceBean.setLow_price(strRuppeSymbol + String.valueOf(value.getAltcoins().get(4).getAltSellPrice()));
                        priceBean.setHigh_price(strRuppeSymbol + String.valueOf(value.getAltcoins().get(4).getAltBuyPrice()));
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getPocketbitsEthTicker() {
        pocketBitsBeanObservable = apiManager.getPocketbitsTicker();
        disposables = new CompositeDisposable();
        disposables.add(pocketBitsBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PocketBitsBean>() {

                    @Override
                    public void onNext(PocketBitsBean value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Pocketbits (ETH)");
                        priceBean.setPrice(strRuppeSymbol + String.valueOf(value.getAltcoins().get(3).getAltBuyPrice()));
                        priceBean.setLow_price(strRuppeSymbol + String.valueOf(value.getAltcoins().get(3).getAltSellPrice()));
                        priceBean.setHigh_price(strRuppeSymbol + String.valueOf(value.getAltcoins().get(3).getAltBuyPrice()));
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getPocketbitsBchTicker() {
        pocketBitsBeanObservable = apiManager.getPocketbitsTicker();
        disposables = new CompositeDisposable();
        disposables.add(pocketBitsBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PocketBitsBean>() {

                    @Override
                    public void onNext(PocketBitsBean value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Pocketbits (BCH)");
                        priceBean.setPrice(strRuppeSymbol + String.valueOf(value.getAltcoins().get(8).getAltBuyPrice()));
                        priceBean.setLow_price(strRuppeSymbol + String.valueOf(value.getAltcoins().get(8).getAltSellPrice()));
                        priceBean.setHigh_price(strRuppeSymbol + String.valueOf(value.getAltcoins().get(8).getAltBuyPrice()));
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getPocketbitsXrpTicker() {
        pocketBitsBeanObservable = apiManager.getPocketbitsTicker();
        disposables = new CompositeDisposable();
        disposables.add(pocketBitsBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PocketBitsBean>() {

                    @Override
                    public void onNext(PocketBitsBean value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Pocketbits (XRP)");
                        priceBean.setPrice(strRuppeSymbol + String.valueOf(value.getAltcoins().get(6).getAltBuyPrice()));
                        priceBean.setLow_price(strRuppeSymbol + String.valueOf(value.getAltcoins().get(6).getAltSellPrice()));
                        priceBean.setHigh_price(strRuppeSymbol + String.valueOf(value.getAltcoins().get(6).getAltBuyPrice()));
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
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
        switch (selectedCurrency) {
            case "BTC":
                new refreshBtcTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
            case "BCH":
                new refreshBchTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
            case "ETH":
                new refreshEthTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
            case "LTC":
                new refreshLtcTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
            case "XRP":
                new refreshXrpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class refreshBtcTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
            priceBeanArrayList.clear();
        }

        @Override
        protected String doInBackground(String... value) {
            getBitfinexPubTicker();
            getBitStampTicker();
            getZebpayTicker();
            getPocketbitsTicker();
            getGdaxBtcTicker();
            return null;
        }

        @Override
        protected void onPostExecute(String value) {
            priceAdapter.notifyDataSetChanged();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class refreshBchTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
            priceBeanArrayList.clear();
        }

        @Override
        protected String doInBackground(String... value) {
            getZebpayBchTicker();
            getPocketbitsBchTicker();
            getBitfinexPubBchTicker();
            getBitStampBchTicker();
            getGdaxBchTicker();
            return null;
        }

        @Override
        protected void onPostExecute(String value) {
            priceAdapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class refreshEthTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
            priceBeanArrayList.clear();
        }

        @Override
        protected String doInBackground(String... value) {
            getZebpayEthTicker();
            getPocketbitsEthTicker();
            getBitfinexPubEthTicker();
            getBitStampEthTicker();
            getGdaxEthTicker();
            return null;
        }

        @Override
        protected void onPostExecute(String value) {
            priceAdapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class refreshLtcTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
            priceBeanArrayList.clear();
        }

        @Override
        protected String doInBackground(String... value) {
            getZebpayLtcTicker();
            getPocketbitsLtcTicker();
            getBitfinexPubLtcTicker();
            getBitStampLtcTicker();
            getGdaxLtcTicker();
            return null;
        }

        @Override
        protected void onPostExecute(String value) {
            priceAdapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class refreshXrpTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
            priceBeanArrayList.clear();
        }

        @Override
        protected String doInBackground(String... value) {
            getZebpayXrpTicker();
            getPocketbitsXrpTicker();
            getBitfinexXrpPubTicker();
            getBitStampXrpTicker();
            return null;
        }

        @Override
        protected void onPostExecute(String value) {
            priceAdapter.notifyDataSetChanged();
        }
    }

    private void getZebpayXrpTicker() {
        zebPayBeanObservable = apiManager.getZebpayTicker("XRP");
        disposables = new CompositeDisposable();
        disposables.add(zebPayBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ZebPayBean>() {

                    @Override
                    public void onNext(ZebPayBean value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Zebpay (XRP)");
                        priceBean.setPrice(strRuppeSymbol + String.valueOf(value.getBuy()));
                        priceBean.setLow_price(strRuppeSymbol + String.valueOf(value.getSell()));
                        priceBean.setHigh_price(strRuppeSymbol + String.valueOf(value.getBuy()));
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getZebpayBchTicker() {
        zebPayBeanObservable = apiManager.getZebpayTicker("BCH");
        disposables = new CompositeDisposable();
        disposables.add(zebPayBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ZebPayBean>() {

                    @Override
                    public void onNext(ZebPayBean value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Zebpay (BCH)");
                        priceBean.setPrice(strRuppeSymbol + String.valueOf(value.getBuy()));
                        priceBean.setLow_price(strRuppeSymbol + String.valueOf(value.getSell()));
                        priceBean.setHigh_price(strRuppeSymbol + String.valueOf(value.getBuy()));
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void getZebpayEthTicker() {
        zebPayBeanObservable = apiManager.getZebpayTicker("ETH");
        disposables = new CompositeDisposable();
        disposables.add(zebPayBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ZebPayBean>() {

                    @Override
                    public void onNext(ZebPayBean value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Zebpay (ETH)");
                        priceBean.setPrice(strRuppeSymbol + String.valueOf(value.getBuy()));
                        priceBean.setLow_price(strRuppeSymbol + String.valueOf(value.getSell()));
                        priceBean.setHigh_price(strRuppeSymbol + String.valueOf(value.getBuy()));
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }


    private void getZebpayLtcTicker() {
        zebPayBeanObservable = apiManager.getZebpayTicker("LTC");
        disposables = new CompositeDisposable();
        disposables.add(zebPayBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ZebPayBean>() {

                    @Override
                    public void onNext(ZebPayBean value) {
                        PriceBean priceBean = new PriceBean();
                        priceBean.setTitle("Zebpay (LTC)");
                        priceBean.setPrice(strRuppeSymbol + String.valueOf(value.getBuy()));
                        priceBean.setLow_price(strRuppeSymbol + String.valueOf(value.getSell()));
                        priceBean.setHigh_price(strRuppeSymbol + String.valueOf(value.getBuy()));
                        priceBeanArrayList.add(priceBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        priceAdapter.notifyDataSetChanged();
                    }
                }));
    }
}

package com.androidevlinux.percy.UTXO.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidevlinux.percy.UTXO.R;
import com.androidevlinux.percy.UTXO.activities.MainActivity;
import com.androidevlinux.percy.UTXO.data.models.bitfinex.BitfinexPubTickerResponseBean;
import com.androidevlinux.percy.UTXO.data.network.ApiManager;
import com.androidevlinux.percy.UTXO.utils.LockableNestedScrollView;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by percy on 10/1/18.
 */

public class BitfinexCandleChartFragment extends Fragment implements OnChartValueSelectedListener, PopupMenu.OnMenuItemClickListener {

    @BindView(R.id.candleChart)
    CandleStickChart candleChart;
    Unbinder unbinder;
    ArrayList<CandleEntry> entries = new ArrayList<>();
    ArrayList<String> xValues = new ArrayList<>();
    ArrayList<Long> xFloatValues = new ArrayList<>();
    int currencyId = 0;
    Description description = new Description();
    @BindView(R.id.current_price)
    TextView currentPrice;
    @BindView(R.id.dateTextView)
    TextView dateTextView;
    @BindView(R.id.graphFragmentNestedScrollView)
    LockableNestedScrollView nestedScrollView;
    @BindView(R.id.chart_interval_button_grp)
    SingleSelectToggleGroup buttonGroup;
    @BindView(R.id.tableNameDataTextView)
    TextView tableNameDataTextView;
    @BindView(R.id.tablePriceUSDDataTextView)
    TextView tablePriceUSDDataTextView;
    @BindView(R.id.tablePriceBTCDataTextView)
    TextView tablePriceBTCDataTextView;
    @BindView(R.id.tableVolUSDDataTextView)
    TextView tableVolUSDDataTextView;
    @BindView(R.id.table24hrHighTitleTextView)
    TextView table24hrHighTitleTextView;
    @BindView(R.id.table24hrLowTitleTextView)
    TextView table24hrLowTitleTextView;
    @BindView(R.id.tableBidDataTextView)
    TextView tableBidDataTextView;
    @BindView(R.id.tableAskDataTextView)
    TextView tableAskDataTextView;
    @BindView(R.id.chartProgressSpinner)
    ProgressBar chartProgressSpinner;
    private ApiManager apiManager;
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert inflater != null;
        View view = inflater.inflate(R.layout.bitfinex_candle_chart_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        assert view != null;
        super.onViewCreated(view, savedInstanceState);
        apiManager = ApiManager.getInstance();
        buttonGroup.check(R.id.fifteenMinButton);
        getBitfinexData(currencyId, "15m");
        candleChart.setOnChartValueSelectedListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        candleChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                YAxis yAxis = candleChart.getAxisLeft();
                // Allow scrolling in the right and left margins
                if (me.getX() > yAxis.getLongestLabel().length() * yAxis.getTextSize() &&
                        me.getX() < displayWidth - candleChart.getViewPortHandler().offsetRight()) {
                    nestedScrollView.setScrollingEnabled(false);
                }
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                nestedScrollView.setScrollingEnabled(true);
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });
        buttonGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Calendar.getInstance();
            switch (checkedId) {
                case R.id.fifteenMinButton:
                    getBitfinexData(currencyId, "15m");
                    break;
                case R.id.sixHourssButton:
                    getBitfinexData(currencyId, "6h");
                    break;
                case R.id.dayButton:
                    getBitfinexData(currencyId, "1D");
                    break;
                case R.id.weekButton:
                    getBitfinexData(currencyId, "7D");
                    break;
                case R.id.twoWeeksButton:
                    getBitfinexData(currencyId, "14D");
                    break;
                case R.id.monthButton:
                    getBitfinexData(currencyId, "1M");
                    break;
            }
        });

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
        popup.setOnMenuItemClickListener(BitfinexCandleChartFragment.this);

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.crypto_prices_filter_popup, popup.getMenu());
        popup.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /* from PopupMenu.OnMenuItemClickListener */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        ((MainActivity) Objects.requireNonNull(getActivity())).hideNavigationBar();
        switch (item.getItemId()) {
            case R.id.btc_filter_option:
                currencyId = 0;
                refresh();
                mActivity.setTitle(getString(R.string.nav_crypto_candle_chart_btc));
                return true;
            case R.id.bch_filter_option:
                currencyId = 1;
                refresh();
                mActivity.setTitle(getString(R.string.nav_crypto_candle_chart_bch));
                return true;
            case R.id.eth_filter_option:
                currencyId = 2;
                refresh();
                mActivity.setTitle(getString(R.string.nav_crypto_candle_chart_eth));
                return true;
            case R.id.ltc_filter_option:
                currencyId = 3;
                refresh();
                mActivity.setTitle(getString(R.string.nav_crypto_candle_chart_ltc));
                return true;
            case R.id.xrp_filter_option:
                currencyId = 4;
                refresh();
                mActivity.setTitle(getString(R.string.nav_crypto_candle_chart_xrp));
                return true;
            default:
                return false;
        }
    }

    void refresh() {
        switch (buttonGroup.getCheckedId()) {
            case R.id.fifteenMinButton:
                getBitfinexData(currencyId, "15m");
                break;
            case R.id.sixHourssButton:
                getBitfinexData(currencyId, "6h");
                break;
            case R.id.dayButton:
                getBitfinexData(currencyId, "1D");
                break;
            case R.id.weekButton:
                getBitfinexData(currencyId, "7D");
                break;
            case R.id.twoWeeksButton:
                getBitfinexData(currencyId, "14D");
                break;
            case R.id.monthButton:
                getBitfinexData(currencyId, "1M");
                break;
        }
    }

    Observable<BitfinexPubTickerResponseBean> bitfinexPubTickerResponseBeanObservable;
    CompositeDisposable disposables;

    private void getBitfinexPubTicker(String symbol, String currency) {
        bitfinexPubTickerResponseBeanObservable = apiManager.getBitfinexTicker(symbol);
        disposables = new CompositeDisposable();
        disposables.add(bitfinexPubTickerResponseBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BitfinexPubTickerResponseBean>() {

                    @Override
                    public void onNext(BitfinexPubTickerResponseBean value) {
                        if (value.getLastPrice() == null) {
                            tableNameDataTextView.setText("N/A");
                        } else {
                            tableNameDataTextView.setText(currency);
                        }
                        tablePriceBTCDataTextView.setText("N/A");
                        if (value.getLastPrice() == null) {
                            tablePriceUSDDataTextView.setText("N/A");
                        } else {
                            tablePriceUSDDataTextView.setText(MessageFormat.format("$ {0}", value.getLastPrice()));
                        }

                        if (value.getVolume() == null) {
                            tableVolUSDDataTextView.setText("N/A");
                        } else {
                            tableVolUSDDataTextView.setText(MessageFormat.format("$ {0}", value.getVolume()));
                        }

                        if (value.getLow() == null) {
                            table24hrLowTitleTextView.setText("N/A");
                        } else {
                            table24hrLowTitleTextView.setText(MessageFormat.format("$ {0}", value.getLow()));
                        }

                        if (value.getHigh() == null) {
                            table24hrHighTitleTextView.setText("N/A");
                        } else {
                            table24hrHighTitleTextView.setText(MessageFormat.format("$ {0}", value.getHigh()));
                        }

                        if (value.getBid() == null) {
                            tableBidDataTextView.setText("N/A");
                        } else {
                            tableBidDataTextView.setText(MessageFormat.format("$ {0}", value.getBid()));
                        }

                        if (value.getAsk() == null) {
                            tableAskDataTextView.setText("N/A");
                        } else {
                            tableAskDataTextView.setText(MessageFormat.format("$ {0}", value.getAsk()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void getBitfinexData(int currencyId, String time) {
        chartProgressSpinner.setVisibility(View.VISIBLE);
        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        String symbol = "tBTCUSD";
        String strSymbol = "btcusd";
        String currency = "Bitcoin";
        if (currencyId == 0) {
            symbol = "tBTCUSD";
            strSymbol = "btcusd";
            currency = "Bitcoin";
        } else if (currencyId == 1) {
            symbol = "tBCHUSD";
            strSymbol = "bchusd";
            currency = "Bitcoin Cash";
        } else if (currencyId == 2) {
            symbol = "tETHUSD";
            strSymbol = "ethusd";
            currency = "Ethereum";
        } else if (currencyId == 3) {
            symbol = "tLTCUSD";
            strSymbol = "ltcusd";
            currency = "Litecoin";
        } else if (currencyId == 4) {
            symbol = "tXRPUSD";
            strSymbol = "xrpusd";
            currency = "Ripple";
        }
        getBitfinexPubTicker(strSymbol, currency);
        String finalCurrency = currency;
        apiManager.getBitfinexData(time, symbol, new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Gson gson = new Gson();
                try {
                    BigDecimal[][] newMap;
                    BigDecimal[][] dummy = new BigDecimal[0][0];
                    newMap = gson.fromJson(Objects.requireNonNull(response.body()).string(), dummy.getClass());
                    entries.clear();
                    xValues.clear();
                    xFloatValues.clear();
                    if (candleChart != null) {
                        candleChart.clear();
                        candleChart.invalidate();
                    }
                    int count = -1;

                    Collections.reverse(Arrays.asList(newMap));
                    for (BigDecimal[] s : newMap) {
                        count += 1;
                        entries.add(new CandleEntry(count, Float.valueOf(String.valueOf(s[3])), Float.valueOf(String.valueOf(s[4])), Float.valueOf(String.valueOf(s[1])), Float.valueOf(String.valueOf(s[2]))));
                        CandleDataSet data_set = new CandleDataSet(entries, "Data");
                        data_set.setColor(Color.rgb(80, 80, 80));
                        data_set.setShadowColor(Color.DKGRAY);
                        data_set.setShadowWidth(0.7f);
                        data_set.setDecreasingColor(Color.RED);
                        data_set.setDecreasingPaintStyle(Paint.Style.FILL);
                        data_set.setIncreasingColor(Color.rgb(122, 242, 84));
                        data_set.setIncreasingPaintStyle(Paint.Style.FILL);
                        data_set.setNeutralColor(Color.BLUE);
                        data_set.setValueTextColor(Color.RED);
                        Date date = new Date(Long.valueOf(String.valueOf(s[0])));
                        SimpleDateFormat sdf;
                        switch ("12h") {
                            case "7D":
                            case "14D":
                            case "1M":
                                sdf = new SimpleDateFormat("MMM/yy", Locale.ENGLISH);
                                break;
                            case "6h":
                            case "12h":
                                sdf = new SimpleDateFormat("MM/dd", Locale.ENGLISH);
                                break;
                            default:
                                sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                                break;
                        }
                        sdf.setTimeZone(TimeZone.getDefault());
                        String formattedDate = sdf.format(date);

                        xValues.add(formattedDate);
                        xFloatValues.add(Long.valueOf(String.valueOf(s[0])));
                        YAxis yAxis = candleChart.getAxisRight();
                        yAxis.setGranularityEnabled(true);
                        XAxis xAxis = candleChart.getXAxis();
                        xAxis.setGranularity(1f);
                        xAxis.setValueFormatter((value1, axis) -> xValues.get((int) value1 % xValues.size()));

                        data_set.setHighlightLineWidth(2);
                        data_set.setHighlightEnabled(true);
                        data_set.setDrawHighlightIndicators(true);
                        data_set.setHighLightColor(Color.YELLOW);
                        CandleData data = new CandleData(data_set);
                        candleChart.setData(data);
                    }
                    float currPrice = entries.get(entries.size() - 1).getY();
                    dateTextView.setText(getFormattedFullDate(xFloatValues.get(entries.size() - 1)));
                    currentPrice.setText(String.format(getString(R.string.unrounded_usd_chart_price_format), String.valueOf(currPrice)));
                    currentPrice.setTextColor(Color.WHITE);

                    candleChart.animateX(800);
                    description.setText(finalCurrency);
                    description.setTextSize(15f);
                    description.setTextAlign(Paint.Align.RIGHT);
                    candleChart.setDescription(description);
                    //candleChart.setDragEnabled(false);
                    candleChart.setScaleEnabled(true);
                    candleChart.setExtraRightOffset(30f);
                    candleChart.setBackgroundColor(Color.BLACK);
                    candleChart.setDrawGridBackground(false);
                    candleChart.getAxisLeft().setDrawGridLines(false);
                    candleChart.getXAxis().setDrawGridLines(false);
                    candleChart.getAxisLeft().setTextColor(getResources().getColor(R.color.white));
                    candleChart.getXAxis().setTextColor(getResources().getColor(R.color.white));
                    candleChart.getAxisRight().setDrawGridLines(false);
                    candleChart.setDrawBorders(false);
                    candleChart.setPinchZoom(true);
                    Legend l = candleChart.getLegend();
                    l.setEnabled(false);
                    candleChart.setAutoScaleMinMaxEnabled(true);
                    candleChart.invalidate();
                    chartProgressSpinner.setVisibility(View.GONE);
                    mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                } catch (IOException e) {
                    e.printStackTrace();
                    mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                t.printStackTrace();
                chartProgressSpinner.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("TAG", getFormattedFullDate(xFloatValues.get(((int) e.getX()))));
        currentPrice.setText(String.format(getString(R.string.unrounded_usd_chart_price_format), String.valueOf(e.getY())));
        dateTextView.setText(getFormattedFullDate(xFloatValues.get(((int) e.getX()))));
    }

    @Override
    public void onNothingSelected() {

    }

    SimpleDateFormat fullDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);

    public String getFormattedFullDate(float unixSeconds) {
        Date date = new Date((long) unixSeconds);
        return fullDateFormat.format(date);
    }
}
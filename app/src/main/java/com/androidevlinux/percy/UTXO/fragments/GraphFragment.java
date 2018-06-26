package com.androidevlinux.percy.UTXO.fragments;

/**
 * Created by Ryan on 8/11/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.PopupMenu;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.androidevlinux.percy.UTXO.R;
import com.androidevlinux.percy.UTXO.activities.MainActivity;
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapChartData;
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapCoin;
import com.androidevlinux.percy.UTXO.data.network.ApiManager;
import com.androidevlinux.percy.UTXO.formatters.MonthSlashDayDateFormatter;
import com.androidevlinux.percy.UTXO.formatters.MonthSlashYearFormatter;
import com.androidevlinux.percy.UTXO.formatters.TimeDateFormatter;
import com.androidevlinux.percy.UTXO.utils.LockableNestedScrollView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.CircularToggle;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class GraphFragment extends Fragment implements OnChartValueSelectedListener, PopupMenu.OnMenuItemClickListener {

    public static final String COIN_MARKETCAP_CHART_URL_WINDOW = "https://graphs2.coinmarketcap.com/currencies/%s/%s/%s/";
    public static final String COIN_MARKETCAP_CHART_URL_ALL_DATA = "https://graphs2.coinmarketcap.com/currencies/%s/";
    @BindView(R.id.current_price)
    TextView currPriceText;
    @BindView(R.id.graphFragmentDateTextView)
    TextView chartDateTextView;
    @BindView(R.id.percent_change)
    TextView percentChangeText;
    @BindView(R.id.dayButton)
    CircularToggle dayButton;
    @BindView(R.id.weekButton)
    CircularToggle weekButton;
    @BindView(R.id.monthButton)
    CircularToggle monthButton;
    @BindView(R.id.threeMonthButton)
    CircularToggle threeMonthButton;
    @BindView(R.id.yearButton)
    CircularToggle yearButton;
    @BindView(R.id.allTimeButton)
    CircularToggle allTimeButton;
    @BindView(R.id.chart_interval_button_grp)
    SingleSelectToggleGroup buttonGroup;
    @BindView(R.id.chart)
    LineChart lineChart;
    @BindView(R.id.tableNameTitleTextView)
    TextView tableNameTitleTextView;
    @BindView(R.id.tableNameDataTextView)
    TextView nameTextView;
    @BindView(R.id.nameTableRow)
    TableRow nameTableRow;
    @BindView(R.id.tablePriceUSDTitleTextView)
    TextView tablePriceUSDTitleTextView;
    @BindView(R.id.tablePriceUSDDataTextView)
    TextView priceUSDTextView;
    @BindView(R.id.priceUSDTableRow)
    TableRow priceUSDTableRow;
    @BindView(R.id.tablePriceBTCTitleTextView)
    TextView tablePriceBTCTitleTextView;
    @BindView(R.id.tablePriceBTCDataTextView)
    TextView priceBTCTextView;
    @BindView(R.id.priceBTCTableRow)
    TableRow priceBTCTableRow;
    @BindView(R.id.tableVolUSDTitleTextView)
    TextView tableVolUSDTitleTextView;
    @BindView(R.id.tableVolUSDDataTextView)
    TextView volumeTextView;
    @BindView(R.id.volUSDTableRow)
    TableRow volUSDTableRow;
    @BindView(R.id.tableMktCapTitleTextView)
    TextView tableMktCapTitleTextView;
    @BindView(R.id.tableMktCapDataTextView)
    TextView mktCapTextView;
    @BindView(R.id.mktCapUSDTableRow)
    TableRow mktCapUSDTableRow;
    @BindView(R.id.tableAvailableSupplyTitleTextView)
    TextView tableAvailableSupplyTitleTextView;
    @BindView(R.id.tableAvailableSupplyDataTextView)
    TextView availSupplyTextView;
    @BindView(R.id.availableSupplyTableRow)
    TableRow availableSupplyTableRow;
    @BindView(R.id.tableTotalSupplyTextView)
    TextView tableTotalSupplyTextView;
    @BindView(R.id.tableTotalSupplyDataTextView)
    TextView totalSupplyTextView;
    @BindView(R.id.totalSupplyTableRow)
    TableRow totalSupplyTableRow;
    @BindView(R.id.tableMaxSupplyTitleTextView)
    TextView tableMaxSupplyTitleTextView;
    @BindView(R.id.tableMaxSupplyDataTextView)
    TextView maxSupplyTextView;
    @BindView(R.id.maxSupplyTableRow)
    TableRow maxSupplyTableRow;
    @BindView(R.id.table1hrChangeTitleTextView)
    TextView table1hrChangeTitleTextView;
    @BindView(R.id.table24hrChangeTitleTextView)
    TextView table24hrChangeTitleTextView;
    @BindView(R.id.tableWeekChangeTitleTextView)
    TextView tableWeekChangeTitleTextView;
    @BindView(R.id.changeTitlesTableRow)
    TableRow changeTitlesTableRow;
    @BindView(R.id.table1hrChangeDataTextView)
    TextView oneHrChangeTextView;
    @BindView(R.id.table24hrChangeDataTextView)
    TextView dayChangeTextView;
    @BindView(R.id.tableWeekChangeDataTextView)
    TextView weekChangeTextView;
    @BindView(R.id.tableLayoutGraphFragment)
    TableLayout tableLayoutGraphFragment;
    @BindView(R.id.chartProgressSpinner)
    ProgressBar chartProgressBar;
    @BindView(R.id.graphFragmentNestedScrollView)
    LockableNestedScrollView nestedScrollView;
    Unbinder unbinder;
    private int chartFillColor;
    private int chartBorderColor;
    private String cryptoID, currencyId;
    private int percentageColor;
    private IAxisValueFormatter XAxisFormatter;
    public final IAxisValueFormatter monthSlashDayXAxisFormatter = new MonthSlashDayDateFormatter();
    public final TimeDateFormatter dayCommaTimeDateFormatter = new TimeDateFormatter();
    public final MonthSlashYearFormatter monthSlashYearFormatter = new MonthSlashYearFormatter();
    private String currentTimeWindow = "";
    public static String CURRENT_CHART_URL;
    public final static DecimalFormat rawNumberFormat = new DecimalFormat("#,###.##");
    private int displayWidth;
    NumberFormat chartUSDPriceFormat = NumberFormat.getInstance();
    SimpleDateFormat fullDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    protected Activity mActivity;
    ApiManager apiManager;
    Observable<CoinMarketCapCoin> coinMarketCapFooterDataObservable;
    Observable<CoinMarketCapChartData> coinMarketCapDataObservable;
    CompositeDisposable disposables;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.dispose();
        unbinder.unbind();
    }

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

    public void setColors(float percentChange) {
        if (percentChange >= 0) {
            chartFillColor = ResourcesCompat.getColor(mActivity.getResources(), R.color.materialLightGreen, null);
            chartBorderColor = ResourcesCompat.getColor(mActivity.getResources(), R.color.darkGreen, null);
            percentageColor = ResourcesCompat.getColor(mActivity.getResources(), R.color.percentPositiveGreen, null);
        } else {
            chartFillColor = ResourcesCompat.getColor(mActivity.getResources(), R.color.materialLightRed, null);
            chartBorderColor = ResourcesCompat.getColor(mActivity.getResources(), R.color.darkRed, null);
            percentageColor = ResourcesCompat.getColor(mActivity.getResources(), R.color.percentNegativeRed, null);
        }
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
        popup.setOnMenuItemClickListener(GraphFragment.this);

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
                cryptoID = "bitcoin";
                currencyId = "1";
                refresh();
                mActivity.setTitle(getString(R.string.nav_crypto_graph_btc));
                return true;
            case R.id.bch_filter_option:
                cryptoID = "bitcoin-cash";
                currencyId = "1831";
                refresh();
                mActivity.setTitle(getString(R.string.nav_crypto_graph_bch));
                return true;
            case R.id.eth_filter_option:
                cryptoID = "ethereum";
                currencyId = "1027";
                refresh();
                mActivity.setTitle(getString(R.string.nav_crypto_graph_eth));
                return true;
            case R.id.ltc_filter_option:
                cryptoID = "litecoin";
                currencyId = "2";
                refresh();
                mActivity.setTitle(getString(R.string.nav_crypto_graph_ltc));
                return true;
            case R.id.xrp_filter_option:
                cryptoID = "ripple";
                currencyId = "52";
                refresh();
                mActivity.setTitle(getString(R.string.nav_crypto_graph_xrp));
                return true;
            default:
                return false;
        }
    }

    void refresh() {
        switch (buttonGroup.getCheckedId()) {
            case R.id.dayButton:
                setDayChecked(Calendar.getInstance());
                getCMCChart();
                break;
            case R.id.weekButton:
                setWeekChecked(Calendar.getInstance());
                getCMCChart();
                break;
            case R.id.monthButton:
                setMonthChecked(Calendar.getInstance());
                getCMCChart();
                break;
            case R.id.threeMonthButton:
                setThreeMonthChecked(Calendar.getInstance());
                getCMCChart();
                break;
            case R.id.yearButton:
                setYearChecked(Calendar.getInstance());
                getCMCChart();
                break;
            case R.id.allTimeButton:
                setAllTimeChecked();
                getCMCChart();
                break;
        }
    }
    public void setUpChart() {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setAvoidFirstLastClipping(true);
        lineChart.getAxisLeft().setEnabled(true);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setContentDescription("");
        lineChart.setNoDataText(getString(R.string.noChartDataString));
        lineChart.setNoDataTextColor(R.color.darkRed);
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                YAxis yAxis = lineChart.getAxisLeft();
                // Allow scrolling in the right and left margins
                if (me.getX() > yAxis.getLongestLabel().length() * yAxis.getTextSize() &&
                        me.getX() < displayWidth - lineChart.getViewPortHandler().offsetRight()) {
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
    }

    public LineDataSet setUpLineDataSet(List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, "Price");
        dataSet.setColor(chartBorderColor);
        dataSet.setFillColor(chartFillColor);
        dataSet.setDrawHighlightIndicators(true);
        dataSet.setDrawFilled(true);
        dataSet.setDrawCircles(true);
        dataSet.setCircleColor(chartBorderColor);
        dataSet.setDrawCircleHole(false);
        dataSet.setDrawValues(false);
        dataSet.setCircleRadius(1);
        dataSet.setHighlightLineWidth(2);
        dataSet.setHighlightEnabled(true);
        dataSet.setDrawHighlightIndicators(true);
        dataSet.setHighLightColor(chartBorderColor);
        return dataSet;
    }

    public void getCMCChart() {
        lineChart.setEnabled(true);
        lineChart.clear();
        chartProgressBar.setVisibility(View.VISIBLE);
        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        apiManager = ApiManager.getInstance();

        coinMarketCapFooterDataObservable = apiManager.getChartFooterData(currencyId);
        disposables = new CompositeDisposable();
        disposables.add(coinMarketCapFooterDataObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CoinMarketCapCoin>() {

                    @Override
                    public void onNext(CoinMarketCapCoin value) {
                        setTable(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
        coinMarketCapDataObservable = apiManager.getChartData(String.format(CURRENT_CHART_URL, cryptoID));
        disposables = new CompositeDisposable();
        disposables.add(coinMarketCapDataObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CoinMarketCapChartData>() {

                    @Override
                    public void onNext(CoinMarketCapChartData value) {
                        List<Entry> closePrices = new ArrayList<>();
                        for (List<Float> priceTimeUnit : value.getPriceUsd()) {
                            closePrices.add(new Entry(priceTimeUnit.get(0), priceTimeUnit.get(1)));
                        }

                        if (closePrices.size() == 0) {
                            lineChart.setData(null);
                            lineChart.setEnabled(false);
                            lineChart.invalidate();
                            percentChangeText.setText("");
                            currPriceText.setText("");
                            lineChart.setNoDataText(getString(R.string.noChartDataString));
                            chartProgressBar.setVisibility(View.GONE);
                            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            return;
                        }
                        XAxis xAxis = lineChart.getXAxis();
                        xAxis.setValueFormatter(XAxisFormatter);
                        float currPrice = closePrices.get(closePrices.size() - 1).getY();
                        chartDateTextView.setText(getFormattedFullDate(closePrices.get(closePrices.size() - 1).getX()));
                        currPriceText.setText(String.format(getString(R.string.unrounded_usd_chart_price_format), String.valueOf(currPrice)));
                        currPriceText.setTextColor(Color.BLACK);
                        float firstPrice = closePrices.get(0).getY();
                        for (Entry e : closePrices) {
                            if (firstPrice != 0) {
                                break;
                            } else {
                                firstPrice = e.getY();
                            }
                        }
                        float difference = (currPrice - firstPrice);
                        float percentChange = (difference / firstPrice) * 100;
                        if (percentChange < 0) {
                            percentChangeText.setText(String.format(getString(R.string.negative_variable_pct_change_with_dollars_format), currentTimeWindow, percentChange, Math.abs(difference)));
                        } else {
                            percentChangeText.setText(String.format(getString(R.string.positive_variable_pct_change_with_dollars_format), currentTimeWindow, percentChange, Math.abs(difference)));
                        }
                        setColors(percentChange);
                        percentChangeText.setTextColor(percentageColor);
                        LineDataSet dataSet = setUpLineDataSet(closePrices);
                        LineData lineData = new LineData(dataSet);
                        lineChart.setData(lineData);
                        lineChart.animateX(800);
                        mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        chartProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getMessage());
                        lineChart.setNoDataText(getString(R.string.noChartDataString));
                        mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        chartProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }


    public void setDayChecked(Calendar cal) {
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();
        cal.clear();
        CURRENT_CHART_URL = String.format(COIN_MARKETCAP_CHART_URL_WINDOW, cryptoID, startTime, endTime);
        currentTimeWindow = getString(R.string.oneDay);
        XAxisFormatter = dayCommaTimeDateFormatter;
    }

    public void setWeekChecked(Calendar cal) {
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        long startTime = cal.getTimeInMillis();
        cal.clear();
        CURRENT_CHART_URL = String.format(COIN_MARKETCAP_CHART_URL_WINDOW, cryptoID, startTime, endTime);
        currentTimeWindow = getString(R.string.Week);
        XAxisFormatter = monthSlashDayXAxisFormatter;
    }

    public void setMonthChecked(Calendar cal) {
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.MONTH, -1);
        long startTime = cal.getTimeInMillis();
        cal.clear();
        CURRENT_CHART_URL = String.format(COIN_MARKETCAP_CHART_URL_WINDOW, cryptoID, startTime, endTime);
        currentTimeWindow = getString(R.string.Month);
        XAxisFormatter = monthSlashDayXAxisFormatter;
    }

    public void setThreeMonthChecked(Calendar cal) {
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.MONTH, -3);
        long startTime = cal.getTimeInMillis();
        cal.clear();
        CURRENT_CHART_URL = String.format(COIN_MARKETCAP_CHART_URL_WINDOW, cryptoID, startTime, endTime);
        currentTimeWindow = getString(R.string.threeMonth);
        XAxisFormatter = monthSlashDayXAxisFormatter;
    }

    public void setYearChecked(Calendar cal) {
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.YEAR, -1);
        long startTime = cal.getTimeInMillis();
        cal.clear();
        CURRENT_CHART_URL = String.format(COIN_MARKETCAP_CHART_URL_WINDOW, cryptoID, startTime, endTime);
        currentTimeWindow = getString(R.string.Year);
        XAxisFormatter = monthSlashYearFormatter;
    }

    public void setAllTimeChecked() {
        currentTimeWindow = getString(R.string.AllTime);
        CURRENT_CHART_URL = String.format(COIN_MARKETCAP_CHART_URL_ALL_DATA, cryptoID);
        XAxisFormatter = monthSlashYearFormatter;
    }

    public void setTable(CoinMarketCapCoin coinObject) {
        String usdFormat = getString(R.string.usd_format);
        String negativePctFormat = getString(R.string.negative_pct_format);
        String positivePctFormat = getString(R.string.positive_pct_format);
        int negativeRedColor = getResources().getColor(R.color.percentNegativeRed);
        int positiveGreenColor = getResources().getColor(R.color.percentPositiveGreen);
        if (coinObject.getData().getName() == null) {
            nameTextView.setText("N/A");
        } else {
            nameTextView.setText(coinObject.getData().getName());
        }

        if (coinObject.getData().getQuotes().getUSD().getPrice() == null) {
            priceUSDTextView.setText("N/A");
        } else {
            priceUSDTextView.setText(String.format(usdFormat, coinObject.getData().getQuotes().getUSD().getPrice()));
        }

       // if (coinObject.getData().getQuotes().getUSD().getPrice() == null) {
            priceBTCTextView.setText("N/A");
      //  } else {
      //      priceBTCTextView.setText(String.format(getString(R.string.btc_format), coinObject.getPrice_btc()));
      //  }

        if (coinObject.getData().getQuotes().getUSD().getVolume24h() == null) {
            volumeTextView.setText("N/A");
        } else {
            volumeTextView.setText(String.format(usdFormat, coinObject.getData().getQuotes().getUSD().getVolume24h()));
        }

        if (coinObject.getData().getQuotes().getUSD().getMarketCap() == null) {
            mktCapTextView.setText("N/A");
        } else {
            mktCapTextView.setText(String.format(usdFormat, coinObject.getData().getQuotes().getUSD().getMarketCap()));
        }

        if (coinObject.getData().getCirculatingSupply() == null) {
            availSupplyTextView.setText("N/A");
        } else {
            availSupplyTextView.setText(rawNumberFormat.format(coinObject.getData().getCirculatingSupply()));
        }

        if (coinObject.getData().getTotalSupply() == null) {
            totalSupplyTextView.setText("N/A");
        } else {
            totalSupplyTextView.setText(rawNumberFormat.format(coinObject.getData().getTotalSupply()));
        }

        if (coinObject.getData().getMaxSupply() == null) {
            maxSupplyTextView.setText("N/A");
        } else {
            maxSupplyTextView.setText(rawNumberFormat.format(coinObject.getData().getMaxSupply()));
        }

        if (coinObject.getData().getQuotes().getUSD().getPercentChange1h() == null) {
            oneHrChangeTextView.setText("N/A");
        } else {
            double amount = coinObject.getData().getQuotes().getUSD().getPercentChange1h();
            if (amount >= 0) {
                oneHrChangeTextView.setText(String.format(positivePctFormat, amount));
                oneHrChangeTextView.setTextColor(positiveGreenColor);
            } else {
                oneHrChangeTextView.setText(String.format(negativePctFormat, amount));
                oneHrChangeTextView.setTextColor(negativeRedColor);
            }
        }

        if (coinObject.getData().getQuotes().getUSD().getPercentChange24h() == null) {
            dayChangeTextView.setText("N/A");
        } else {
            double amount = coinObject.getData().getQuotes().getUSD().getPercentChange24h();
            if (amount >= 0) {
                dayChangeTextView.setText(String.format(positivePctFormat, amount));
                dayChangeTextView.setTextColor(positiveGreenColor);
            } else {
                dayChangeTextView.setText(String.format(negativePctFormat, amount));
                dayChangeTextView.setTextColor(negativeRedColor);
            }
        }

        if (coinObject.getData().getQuotes().getUSD().getPercentChange7d() == null) {
            weekChangeTextView.setText("N/A");
        } else {
            double amount = coinObject.getData().getQuotes().getUSD().getPercentChange7d();
            if (amount >= 0) {
                weekChangeTextView.setText(String.format(positivePctFormat, amount));
                weekChangeTextView.setTextColor(positiveGreenColor);
            } else {
                weekChangeTextView.setText(String.format(negativePctFormat, amount));
                weekChangeTextView.setTextColor(negativeRedColor);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        chartUSDPriceFormat = NumberFormat.getInstance();
        chartUSDPriceFormat.setMaximumFractionDigits(10);
        setUpChart();
        WindowManager mWinMgr = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        displayWidth = Objects.requireNonNull(mWinMgr).getDefaultDisplay().getWidth();
        cryptoID = "bitcoin";
        currencyId = "1";
        setDayChecked(Calendar.getInstance());
        getCMCChart();
        buttonGroup.check(R.id.dayButton);
        currentTimeWindow = getString(R.string.oneDay);
        buttonGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Calendar.getInstance();
            switch (checkedId) {
                case R.id.dayButton:
                    setDayChecked(Calendar.getInstance());
                    getCMCChart();
                    break;
                case R.id.weekButton:
                    setWeekChecked(Calendar.getInstance());
                    getCMCChart();
                    break;
                case R.id.monthButton:
                    setMonthChecked(Calendar.getInstance());
                    getCMCChart();
                    break;
                case R.id.threeMonthButton:
                    setThreeMonthChecked(Calendar.getInstance());
                    getCMCChart();
                    break;
                case R.id.yearButton:
                    setYearChecked(Calendar.getInstance());
                    getCMCChart();
                    break;
                case R.id.allTimeButton:
                    setAllTimeChecked();
                    getCMCChart();
                    break;
            }
        });
        return rootView;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        currPriceText.setText(String.format(getString(R.string.unrounded_usd_chart_price_format), String.valueOf(e.getY())));
        chartDateTextView.setText(getFormattedFullDate(e.getX()));
    }

    @Override
    public void onNothingSelected() {

    }

    public String getFormattedFullDate(float unixSeconds) {
        Date date = new Date((long) unixSeconds);
        return fullDateFormat.format(date);
    }
}

package com.androidevlinux.percy.UTXO.data.network;

import com.androidevlinux.percy.UTXO.data.models.bitfinex.BitfinexPubTickerResponseBean;
import com.androidevlinux.percy.UTXO.data.models.bitstamp.BitstampBean;
import com.androidevlinux.percy.UTXO.data.models.blocktrail.AddressBean;
import com.androidevlinux.percy.UTXO.data.models.blocktrail.TransactionBean;
import com.androidevlinux.percy.UTXO.data.models.changelly.GetCurrenciesResponseBean;
import com.androidevlinux.percy.UTXO.data.models.changelly.GetMinAmountReponseBean;
import com.androidevlinux.percy.UTXO.data.models.changelly.MainBodyBean;
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapChartData;
import com.androidevlinux.percy.UTXO.data.models.coinmarketcap.CoinMarketCapCoin;
import com.androidevlinux.percy.UTXO.data.models.gdax.GDAX;
import com.androidevlinux.percy.UTXO.data.models.newsapi.NewsBean;
import com.androidevlinux.percy.UTXO.data.models.pocketbits.PocketBitsBean;
import com.androidevlinux.percy.UTXO.data.network.apis.BitfinexApiImpl;
import com.androidevlinux.percy.UTXO.data.network.apis.BitstampApiImpl;
import com.androidevlinux.percy.UTXO.data.network.apis.BlocktrailApiImpl;
import com.androidevlinux.percy.UTXO.data.network.apis.ChangellyApiImpl;
import com.androidevlinux.percy.UTXO.data.network.apis.CoinMarketCapApiImpl;
import com.androidevlinux.percy.UTXO.data.network.apis.GdaxApiImpl;
import com.androidevlinux.percy.UTXO.data.network.apis.NewsApiImpl;
import com.androidevlinux.percy.UTXO.data.network.apis.PocketbitsApiImpl;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Callback;

public class ApiManager {

    private static ApiManager apiManager;
    private BitfinexApiImpl bitfinexApiImpl;
    private BitstampApiImpl bitstampApiImpl;
    private BlocktrailApiImpl blocktrailApiImpl;
    private ChangellyApiImpl changellyApiImpl;
    private PocketbitsApiImpl pocketbitsApiImpl;
    private GdaxApiImpl gdaxApiImpl;
    private CoinMarketCapApiImpl coinMarketCapApiImpl;
    private NewsApiImpl newsApiImpl;

    private ApiManager() {
        bitfinexApiImpl = BitfinexApiImpl.getInstance();
        bitstampApiImpl = BitstampApiImpl.getInstance();
        blocktrailApiImpl = BlocktrailApiImpl.getInstance();
        changellyApiImpl = ChangellyApiImpl.getInstance();
        pocketbitsApiImpl = PocketbitsApiImpl.getInstance();
        gdaxApiImpl = GdaxApiImpl.getInstance();
        coinMarketCapApiImpl = CoinMarketCapApiImpl.getInstance();
        newsApiImpl = NewsApiImpl.getInstance();
    }

    public static ApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public Observable<CoinMarketCapChartData> getChartData(String url){
        return coinMarketCapApiImpl.getChartData(url);
    }

    public Observable<CoinMarketCapCoin> getChartFooterData(String currency){
        return coinMarketCapApiImpl.getChartFooterData(currency);
    }

    public Observable<GDAX> getData(String symbol){
        return gdaxApiImpl.getData(symbol);
    }

    public Observable<BitfinexPubTickerResponseBean> getBitfinexTicker(String symbol) {
        return bitfinexApiImpl.getBitfinexTicker(symbol);
    }

    public void getBitfinexData(String time, String symbol, Callback<ResponseBody> callback) {
        bitfinexApiImpl.getBitfinexData(time, symbol, callback);
    }

    public Observable<BitstampBean> getBitstampTicker(String symbol){
        return bitstampApiImpl.getBitstampTicker(symbol);
    }
    public void getBlockTrailAddressData(String query, String data, Callback<AddressBean> callback) {
        blocktrailApiImpl.getBlockTrailAddressData(query, data, callback);
    }

    public void getBlockTrailBlockData(String query, String data, Callback<JsonObject> callback) {
        blocktrailApiImpl.getBlockTrailBlockData(query, data, callback);
    }

    public void getBlockTrailTransactionData(String query, String data, Callback<TransactionBean> callback) {
        blocktrailApiImpl.getBlockTrailTransactionData(query, data, callback);
    }

    public void getCurrencies(String sign, MainBodyBean body, Callback<GetCurrenciesResponseBean> callback) {
        changellyApiImpl.getCurrencies(sign, body, callback);
    }

    public void getMinAmount(String sign, MainBodyBean body, Callback<GetMinAmountReponseBean> callback) {
        changellyApiImpl.getMinAmount(sign, body, callback);
    }

    public void createTransaction(String sign, MainBodyBean body, Callback<com.androidevlinux.percy.UTXO.data.models.changelly.TransactionBean> callback) {
        changellyApiImpl.createTransaction(sign, body, callback);
    }

    public Observable<PocketBitsBean> getPocketbitsTicker() {
        return pocketbitsApiImpl.getPocketbitsTicker();
    }

    public Observable<NewsBean> getNewsData(String apiKey) {
        return newsApiImpl.getNewsData(apiKey);
    }
}

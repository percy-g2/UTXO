package com.androidevlinux.percy.UTXO.data.models.bitmex

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable
import java.math.BigDecimal

class BitMEXTickerBean : Serializable {

    @SerializedName("symbol")
    @Expose
    var symbol: String? = null
    @SerializedName("rootSymbol")
    @Expose
    var rootSymbol: String? = null
    @SerializedName("state")
    @Expose
    var state: String? = null
    @SerializedName("typ")
    @Expose
    var typ: String? = null
    @SerializedName("listing")
    @Expose
    var listing: String? = null
    @SerializedName("front")
    @Expose
    var front: String? = null
    @SerializedName("expiry")
    @Expose
    var expiry: String? = null
    @SerializedName("settle")
    @Expose
    var settle: String? = null
    @SerializedName("relistInterval")
    @Expose
    var relistInterval: Any? = null
    @SerializedName("inverseLeg")
    @Expose
    var inverseLeg: String? = null
    @SerializedName("sellLeg")
    @Expose
    var sellLeg: String? = null
    @SerializedName("buyLeg")
    @Expose
    var buyLeg: String? = null
    @SerializedName("optionStrikePcnt")
    @Expose
    var optionStrikePcnt: Any? = null
    @SerializedName("optionStrikeRound")
    @Expose
    var optionStrikeRound: Any? = null
    @SerializedName("optionStrikePrice")
    @Expose
    var optionStrikePrice: Any? = null
    @SerializedName("optionMultiplier")
    @Expose
    var optionMultiplier: Any? = null
    @SerializedName("positionCurrency")
    @Expose
    var positionCurrency: String? = null
    @SerializedName("underlying")
    @Expose
    var underlying: String? = null
    @SerializedName("quoteCurrency")
    @Expose
    var quoteCurrency: String? = null
    @SerializedName("underlyingSymbol")
    @Expose
    var underlyingSymbol: String? = null
    @SerializedName("reference")
    @Expose
    var reference: String? = null
    @SerializedName("referenceSymbol")
    @Expose
    var referenceSymbol: String? = null
    @SerializedName("calcInterval")
    @Expose
    var calcInterval: Any? = null
    @SerializedName("publishInterval")
    @Expose
    var publishInterval: Any? = null
    @SerializedName("publishTime")
    @Expose
    var publishTime: Any? = null
    @SerializedName("maxOrderQty")
    @Expose
    var maxOrderQty: Double? = null
    @SerializedName("maxPrice")
    @Expose
    var maxPrice: Double? = null
    @SerializedName("lotSize")
    @Expose
    var lotSize: Double? = null
    @SerializedName("tickSize")
    @Expose
    var tickSize: Double? = null
    @SerializedName("multiplier")
    @Expose
    var multiplier: Double? = null
    @SerializedName("settlCurrency")
    @Expose
    var settlCurrency: String? = null
    @SerializedName("underlyingToPositionMultiplier")
    @Expose
    var underlyingToPositionMultiplier: Int? = null
    @SerializedName("underlyingToSettleMultiplier")
    @Expose
    var underlyingToSettleMultiplier: Any? = null
    @SerializedName("quoteToSettleMultiplier")
    @Expose
    var quoteToSettleMultiplier: Int? = null
    @SerializedName("isQuanto")
    @Expose
    var isQuanto: Boolean? = null
    @SerializedName("isInverse")
    @Expose
    var isInverse: Boolean? = null
    @SerializedName("initMargin")
    @Expose
    var initMargin: Double? = null
    @SerializedName("maintMargin")
    @Expose
    var maintMargin: Double? = null
    @SerializedName("riskLimit")
    @Expose
    var riskLimit: Double? = null
    @SerializedName("riskStep")
    @Expose
    var riskStep: Double? = null
    @SerializedName("limit")
    @Expose
    var limit: Any? = null
    @SerializedName("capped")
    @Expose
    var capped: Boolean? = null
    @SerializedName("taxed")
    @Expose
    var taxed: Boolean? = null
    @SerializedName("deleverage")
    @Expose
    var deleverage: Boolean? = null
    @SerializedName("makerFee")
    @Expose
    var makerFee: Double? = null
    @SerializedName("takerFee")
    @Expose
    var takerFee: Double? = null
    @SerializedName("settlementFee")
    @Expose
    var settlementFee: Double? = null
    @SerializedName("insuranceFee")
    @Expose
    var insuranceFee: Double? = null
    @SerializedName("fundingBaseSymbol")
    @Expose
    var fundingBaseSymbol: String? = null
    @SerializedName("fundingQuoteSymbol")
    @Expose
    var fundingQuoteSymbol: String? = null
    @SerializedName("fundingPremiumSymbol")
    @Expose
    var fundingPremiumSymbol: String? = null
    @SerializedName("fundingTimestamp")
    @Expose
    var fundingTimestamp: Any? = null
    @SerializedName("fundingInterval")
    @Expose
    var fundingInterval: Any? = null
    @SerializedName("fundingRate")
    @Expose
    var fundingRate: Any? = null
    @SerializedName("indicativeFundingRate")
    @Expose
    var indicativeFundingRate: Any? = null
    @SerializedName("rebalanceTimestamp")
    @Expose
    var rebalanceTimestamp: Any? = null
    @SerializedName("rebalanceInterval")
    @Expose
    var rebalanceInterval: Any? = null
    @SerializedName("openingTimestamp")
    @Expose
    var openingTimestamp: String? = null
    @SerializedName("closingTimestamp")
    @Expose
    var closingTimestamp: String? = null
    @SerializedName("sessionInterval")
    @Expose
    var sessionInterval: String? = null
    @SerializedName("prevClosePrice")
    @Expose
    var prevClosePrice: Double? = null
    @SerializedName("limitDownPrice")
    @Expose
    var limitDownPrice: Any? = null
    @SerializedName("limitUpPrice")
    @Expose
    var limitUpPrice: Any? = null
    @SerializedName("bankruptLimitDownPrice")
    @Expose
    var bankruptLimitDownPrice: Any? = null
    @SerializedName("bankruptLimitUpPrice")
    @Expose
    var bankruptLimitUpPrice: Any? = null
    @SerializedName("prevTotalVolume")
    @Expose
    var prevTotalVolume: Double? = null
    @SerializedName("totalVolume")
    @Expose
    var totalVolume: Double? = null
    @SerializedName("volume")
    @Expose
    var volume: Double? = null
    @SerializedName("volume24h")
    @Expose
    var volume24h: Double? = null
    @SerializedName("prevTotalTurnover")
    @Expose
    var prevTotalTurnover: Double? = null
    @SerializedName("totalTurnover")
    @Expose
    var totalTurnover: Double? = null
    @SerializedName("turnover")
    @Expose
    var turnover: Double? = null
    @SerializedName("turnover24h")
    @Expose
    var turnover24h: Double? = null
    @SerializedName("homeNotional24h")
    @Expose
    var homeNotional24h: Double? = null
    @SerializedName("foreignNotional24h")
    @Expose
    var foreignNotional24h: Double? = null
    @SerializedName("prevPrice24h")
    @Expose
    var prevPrice24h: Double? = null
    @SerializedName("vwap")
    @Expose
    var vwap: Double? = null
    @SerializedName("highPrice")
    @Expose
    var highPrice: BigDecimal? = null
    @SerializedName("lowPrice")
    @Expose
    var lowPrice: BigDecimal? = null
    @SerializedName("lastPrice")
    @Expose
    var lastPrice: BigDecimal? = null
    @SerializedName("lastPriceProtected")
    @Expose
    var lastPriceProtected: Double? = null
    @SerializedName("lastTickDirection")
    @Expose
    var lastTickDirection: String? = null
    @SerializedName("lastChangePcnt")
    @Expose
    var lastChangePcnt: Double? = null
    @SerializedName("bidPrice")
    @Expose
    var bidPrice: BigDecimal? = null
    @SerializedName("midPrice")
    @Expose
    var midPrice: Double? = null
    @SerializedName("askPrice")
    @Expose
    var askPrice: BigDecimal? = null
    @SerializedName("impactBidPrice")
    @Expose
    var impactBidPrice: Double? = null
    @SerializedName("impactMidPrice")
    @Expose
    var impactMidPrice: Double? = null
    @SerializedName("impactAskPrice")
    @Expose
    var impactAskPrice: Double? = null
    @SerializedName("hasLiquidity")
    @Expose
    var hasLiquidity: Boolean? = null
    @SerializedName("openInterest")
    @Expose
    var openInterest: Double? = null
    @SerializedName("openValue")
    @Expose
    var openValue: Double? = null
    @SerializedName("fairMethod")
    @Expose
    var fairMethod: String? = null
    @SerializedName("fairBasisRate")
    @Expose
    var fairBasisRate: Double? = null
    @SerializedName("fairBasis")
    @Expose
    var fairBasis: Double? = null
    @SerializedName("fairPrice")
    @Expose
    var fairPrice: Double? = null
    @SerializedName("markMethod")
    @Expose
    var markMethod: String? = null
    @SerializedName("markPrice")
    @Expose
    var markPrice: Double? = null
    @SerializedName("indicativeTaxRate")
    @Expose
    var indicativeTaxRate: Double? = null
    @SerializedName("indicativeSettlePrice")
    @Expose
    var indicativeSettlePrice: Double? = null
    @SerializedName("optionUnderlyingPrice")
    @Expose
    var optionUnderlyingPrice: Any? = null
    @SerializedName("settledPrice")
    @Expose
    var settledPrice: Any? = null
    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null

}


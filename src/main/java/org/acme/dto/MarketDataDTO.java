package org.acme.dto;

import java.util.List;

public class MarketDataDTO {

    // The name of the market, e.g., "BTC-EUR"
    private String market;

    // The current status of the market, e.g., "trading"
    private String status;

    // The base currency of the market, e.g., "BTC"
    private String base;

    // The quote currency of the market, e.g., "EUR"
    private String quote;

    // The number of digits allowed in the price
    private String pricePrecision;

    // The minimum amount of base currency for an order
    private String minOrderInBaseAsset;

    // The minimum amount of quote currency for an order
    private String minOrderInQuoteAsset;

    // The maximum amount of base currency for an order
    private String maxOrderInBaseAsset;

    // The maximum amount of quote currency for an order
    private String maxOrderInQuoteAsset;

    // The types of orders that can be placed in this market
    private List<String> orderTypes;

    // The maximum number of decimals in the amount of an order
    private String quantityDecimals;

    // The maximum number of decimals in the amountQuote of a market order
    private String notionalDecimals;

    // The minimum price increment of the market
    private String tickSize;

    // The maximum number of open orders allowed
    private int maxOpenOrders;

    // The fee category of the market
    private String feeCategory;

    // Standard getters and setters

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getPricePrecision() {
        return pricePrecision;
    }

    public void setPricePrecision(String pricePrecision) {
        this.pricePrecision = pricePrecision;
    }

    public String getMinOrderInBaseAsset() {
        return minOrderInBaseAsset;
    }

    public void setMinOrderInBaseAsset(String minOrderInBaseAsset) {
        this.minOrderInBaseAsset = minOrderInBaseAsset;
    }

    public String getMinOrderInQuoteAsset() {
        return minOrderInQuoteAsset;
    }

    public void setMinOrderInQuoteAsset(String minOrderInQuoteAsset) {
        this.minOrderInQuoteAsset = minOrderInQuoteAsset;
    }

    public String getMaxOrderInBaseAsset() {
        return maxOrderInBaseAsset;
    }

    public void setMaxOrderInBaseAsset(String maxOrderInBaseAsset) {
        this.maxOrderInBaseAsset = maxOrderInBaseAsset;
    }

    public String getMaxOrderInQuoteAsset() {
        return maxOrderInQuoteAsset;
    }

    public void setMaxOrderInQuoteAsset(String maxOrderInQuoteAsset) {
        this.maxOrderInQuoteAsset = maxOrderInQuoteAsset;
    }

    public List<String> getOrderTypes() {
        return orderTypes;
    }

    public void setOrderTypes(List<String> orderTypes) {
        this.orderTypes = orderTypes;
    }

    public String getQuantityDecimals() {
        return quantityDecimals;
    }

    public void setQuantityDecimals(String quantityDecimals) {
        this.quantityDecimals = quantityDecimals;
    }

    public String getNotionalDecimals() {
        return notionalDecimals;
    }

    public void setNotionalDecimals(String notionalDecimals) {
        this.notionalDecimals = notionalDecimals;
    }

    public String getTickSize() {
        return tickSize;
    }

    public void setTickSize(String tickSize) {
        this.tickSize = tickSize;
    }

    public int getMaxOpenOrders() {
        return maxOpenOrders;
    }

    public void setMaxOpenOrders(int maxOpenOrders) {
        this.maxOpenOrders = maxOpenOrders;
    }

    public String getFeeCategory() {
        return feeCategory;
    }

    public void setFeeCategory(String feeCategory) {
        this.feeCategory = feeCategory;
    }

    @Override
    public String toString() {
        return "MarketDataDTO{" +
                "market='" + market + '\'' +
                ", status='" + status + '\'' +
                ", base='" + base + '\'' +
                ", quote='" + quote + '\'' +
                ", pricePrecision='" + pricePrecision + '\'' +
                ", minOrderInBaseAsset='" + minOrderInBaseAsset + '\'' +
                ", minOrderInQuoteAsset='" + minOrderInQuoteAsset + '\'' +
                ", maxOrderInBaseAsset='" + maxOrderInBaseAsset + '\'' +
                ", maxOrderInQuoteAsset='" + maxOrderInQuoteAsset + '\'' +
                ", orderTypes=" + orderTypes +
                ", quantityDecimals='" + quantityDecimals + '\'' +
                ", notionalDecimals='" + notionalDecimals + '\'' +
                ", tickSize='" + tickSize + '\'' +
                ", maxOpenOrders=" + maxOpenOrders +
                ", feeCategory='" + feeCategory + '\'' +
                '}';
    }
}

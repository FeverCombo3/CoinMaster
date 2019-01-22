package com.yjq.coinmaster.bean;

public class MarketList {

    private String url;

    private String market;
    private int cmcId;
    private double amount24h;
    private double netInflow24h;
    private String name;
    private double amountRate;
    private double netInflowChange;
    private double outflow24h;
    private double inflow24h;
    private double price;
    private double pricePair;
    private double change24h;
    private double rate24h;
    private String pair;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPricePair() {
        return pricePair;
    }

    public void setPricePair(double pricePair) {
        this.pricePair = pricePair;
    }

    public double getChange24h() {
        return change24h;
    }

    public void setChange24h(double change24h) {
        this.change24h = change24h;
    }

    public double getRate24h() {
        return rate24h;
    }

    public void setRate24h(double rate24h) {
        this.rate24h = rate24h;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public int getCmcId() {
        return cmcId;
    }

    public void setCmcId(int cmcId) {
        this.cmcId = cmcId;
    }

    public double getAmount24h() {
        return amount24h;
    }

    public void setAmount24h(double amount24h) {
        this.amount24h = amount24h;
    }

    public double getNetInflow24h() {
        return netInflow24h;
    }

    public void setNetInflow24h(double netInflow24h) {
        this.netInflow24h = netInflow24h;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmountRate() {
        return amountRate;
    }

    public void setAmountRate(double amountRate) {
        this.amountRate = amountRate;
    }

    public double getNetInflowChange() {
        return netInflowChange;
    }

    public void setNetInflowChange(double netInflowChange) {
        this.netInflowChange = netInflowChange;
    }

    public double getOutflow24h() {
        return outflow24h;
    }

    public void setOutflow24h(double outflow24h) {
        this.outflow24h = outflow24h;
    }

    public double getInflow24h() {
        return inflow24h;
    }

    public void setInflow24h(double inflow24h) {
        this.inflow24h = inflow24h;
    }

    @Override
    public String toString() {
        return "MarketList{" +
                "url='" + url + '\'' +
                ", market='" + market + '\'' +
                ", cmcId=" + cmcId +
                ", amount24h=" + amount24h +
                ", netInflow24h=" + netInflow24h +
                ", name='" + name + '\'' +
                ", amountRate=" + amountRate +
                ", netInflowChange=" + netInflowChange +
                ", outflow24h=" + outflow24h +
                ", inflow24h=" + inflow24h +
                ", price=" + price +
                ", pricePair=" + pricePair +
                ", change24h=" + change24h +
                ", rate24h=" + rate24h +
                ", pair='" + pair + '\'' +
                '}';
    }
}

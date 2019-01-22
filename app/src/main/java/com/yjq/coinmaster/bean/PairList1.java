package com.yjq.coinmaster.bean;

import java.util.List;

public class PairList1 {
    private String url;
    private double amount24h;
    private double netInflow24h;
    private double pricePair;
    private double change24h;
    private String pair;
    private String market;
    private double volume;
    private int cmcId;
    private double price;
    private String name;
    private double rate24h;
    private String coin;

    private int upDown;

    public int getUpDown() {
        return upDown;
    }

    public void setUpDown(int upDown) {
        this.upDown = upDown;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getCmcId() {
        return cmcId;
    }

    public void setCmcId(int cmcId) {
        this.cmcId = cmcId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate24h() {
        return rate24h;
    }

    public void setRate24h(double rate24h) {
        this.rate24h = rate24h;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    @Override
    public String toString() {
        return "PairList1{" +
                "url='" + url + '\'' +
                ", amount24h=" + amount24h +
                ", netInflow24h=" + netInflow24h +
                ", pricePair=" + pricePair +
                ", change24h=" + change24h +
                ", pair='" + pair + '\'' +
                ", market='" + market + '\'' +
                ", volume=" + volume +
                ", cmcId=" + cmcId +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", rate24h=" + rate24h +
                ", coin='" + coin + '\'' +
                ", upDown=" + upDown +
                '}';
    }
}

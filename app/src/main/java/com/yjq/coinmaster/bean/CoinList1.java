package com.yjq.coinmaster.bean;

import java.util.List;

public class CoinList1 {

    private String url;

    private String symbol;
    private double marketCap;
    private int cmcId;
    private double amount;
    private double price;
    private String name;
    private String nameCn;
    private double change24h;
    private double rate24h;
    private long circulatingSupply;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public int getCmcId() {
        return cmcId;
    }

    public void setCmcId(int cmcId) {
        this.cmcId = cmcId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
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

    public long getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(long circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    @Override
    public String toString() {
        return "CoinList1{" +
                "url='" + url + '\'' +
                ", symbol='" + symbol + '\'' +
                ", marketCap=" + marketCap +
                ", cmcId=" + cmcId +
                ", amount=" + amount +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", nameCn='" + nameCn + '\'' +
                ", change24h=" + change24h +
                ", rate24h=" + rate24h +
                ", circulatingSupply=" + circulatingSupply +
                ", coin='" + coin + '\'' +
                ", upDown=" + upDown +
                '}';
    }
}

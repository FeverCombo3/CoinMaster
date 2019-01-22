package com.yjq.coinmaster.request;

public class CoinDetailDataRequest {

    private String type;

    private String coin;

    private String pair;

    private String market;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
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

    @Override
    public String toString() {
        return "CoinDetailDataRequest{" +
                "type='" + type + '\'' +
                ", coin='" + coin + '\'' +
                ", pair='" + pair + '\'' +
                ", market='" + market + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}

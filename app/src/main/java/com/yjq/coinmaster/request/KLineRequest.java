package com.yjq.coinmaster.request;

public class KLineRequest {
    private String pair;
    private String market;
    private String interval;
    private String number;
    private String coin;
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "KLineRequest{" +
                "pair='" + pair + '\'' +
                ", market='" + market + '\'' +
                ", interval='" + interval + '\'' +
                ", number='" + number + '\'' +
                ", coin='" + coin + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

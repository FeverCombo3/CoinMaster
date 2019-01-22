package com.yjq.coinmaster.bean;

public class KLineData {


    private long timestamp;
    private double highPrice;
    private double volume;
    private double openPrice;
    private double closePrice;
    private double lowPrice;

    public long getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timestamp = timeStamp;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    @Override
    public String toString() {
        return "KLineData{" +
                "timeStamp=" + timestamp +
                ", highPrice=" + highPrice +
                ", volume=" + volume +
                ", openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                ", lowPrice=" + lowPrice +
                '}';
    }
}

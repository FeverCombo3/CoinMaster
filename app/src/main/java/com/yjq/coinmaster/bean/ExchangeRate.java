package com.yjq.coinmaster.bean;

public class ExchangeRate {
    private double rateCny;
    private double rateEth;
    private double rateBtc;
    private double rateUsdt;
    public double getRateCny() {
        return rateCny;
    }

    public double getRateUsdt() {
        return rateUsdt;
    }

    public void setRateUsdt(double rateUsdt) {
        this.rateUsdt = rateUsdt;
    }

    public void setRateCny(double rateCny) {
        this.rateCny = rateCny;
    }

    public double getRateEth() {
        return rateEth;
    }

    public void setRateEth(double rateEth) {
        this.rateEth = rateEth;
    }

    public double getRateBtc() {
        return rateBtc;
    }

    public void setRateBtc(double rateBtc) {
        this.rateBtc = rateBtc;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "rateCny=" + rateCny +
                ", rateEth=" + rateEth +
                ", rateBtc=" + rateBtc +
                '}';
    }
}

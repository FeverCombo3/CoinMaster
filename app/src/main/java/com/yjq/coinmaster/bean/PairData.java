package com.yjq.coinmaster.bean;

import java.util.List;

public class PairData {

    private String baseUrl;
    private int count;
    private List<PairList1> pairList;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PairList1> getPairList() {
        return pairList;
    }

    public void setPairList(List<PairList1> pairList) {
        this.pairList = pairList;
    }

    @Override
    public String toString() {
        return "PairData{" +
                "baseUrl='" + baseUrl + '\'' +
                ", count=" + count +
                ", pairList=" + pairList +
                '}';
    }
}

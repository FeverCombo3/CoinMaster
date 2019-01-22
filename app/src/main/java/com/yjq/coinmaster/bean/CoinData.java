package com.yjq.coinmaster.bean;

import java.util.List;

public class CoinData {

    private String baseUrl;
    private int pageCount;
    private List<TypeList1> typeList;
    private List<CoinList1> coinList;
    private int count;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<TypeList1> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<TypeList1> typeList) {
        this.typeList = typeList;
    }

    public List<CoinList1> getCoinList() {
        return coinList;
    }

    public void setCoinList(List<CoinList1> coinList) {
        this.coinList = coinList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CoinData{" +
                "baseUrl='" + baseUrl + '\'' +
                ", pageCount=" + pageCount +
                ", typeList=" + typeList +
                ", coinList=" + coinList +
                ", count=" + count +
                '}';
    }
}

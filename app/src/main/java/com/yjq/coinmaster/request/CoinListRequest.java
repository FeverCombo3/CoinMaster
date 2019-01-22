package com.yjq.coinmaster.request;

public class CoinListRequest {

    private String pageType;
    private String column;
    private String pageSize;
    private String page;
    private String sort;
    private String market;
    private String pair;
    private String coinType;

    public CoinListRequest() {
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    @Override
    public String toString() {
        return "CoinListRequest{" +
                "pageType='" + pageType + '\'' +
                ", column='" + column + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", page='" + page + '\'' +
                ", sort='" + sort + '\'' +
                ", market='" + market + '\'' +
                ", pair='" + pair + '\'' +
                ", coinType='" + coinType + '\'' +
                '}';
    }
}

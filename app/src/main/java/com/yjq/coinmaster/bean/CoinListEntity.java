package com.yjq.coinmaster.bean;

public class CoinListEntity {

    public static final int TYPE_COIN = 0;
    public static final int TYPE_PAIR = 1;
    public static final int TYPE_MARKET = 2;
    public static final int TYPE_MARKET_PAIR = 3;

    public static final int TYPE_COIN_TITLE = 4;
    public static final int TYPE_PAIR_TITLE = 5;
    public static final int TYPE_MARKET_TITLE = 6;
    public static final int TYPE_MARKET_PAIR_TITLE = 7;

    private int type;

    public CoinListEntity(int type) {
        this.type = type;
    }

    private CoinList1 mCoinList1;
    private PairList1 mPairList1;
    private MarketList mMarketList;

    public CoinListEntity(int type, CoinList1 coinList1) {
        this.type = type;
        mCoinList1 = coinList1;
    }

    public CoinListEntity(int type, PairList1 pairList1) {
        this.type = type;
        mPairList1 = pairList1;
    }

    public CoinListEntity(int type, MarketList marketList) {
        this.type = type;
        mMarketList = marketList;
    }

    public int getType() {
        return type;
    }

    public CoinList1 getCoinList1() {
        return mCoinList1;
    }

    public PairList1 getPairList1() {
        return mPairList1;
    }

    public MarketList getMarketList() {
        return mMarketList;
    }

    public void setCoinList1(CoinList1 coinList1) {
        mCoinList1 = coinList1;
    }

    public void setPairList1(PairList1 pairList1) {
        mPairList1 = pairList1;
    }

    public void setMarketList(MarketList marketList) {
        mMarketList = marketList;
    }

    @Override
    public String toString() {
        return "CoinListEntity{" +
                "type=" + type +
                ", mCoinList1=" + mCoinList1 +
                ", mPairList1=" + mPairList1 +
                ", mMarketList=" + mMarketList +
                '}';
    }
}

package com.yjq.coinmaster.mvp.ui.quote;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.bean.CoinList1;
import com.yjq.coinmaster.bean.CoinListEntity;
import com.yjq.coinmaster.constant.Constants;
import com.yjq.coinmaster.util.NumberUtils;
import com.yjq.coinmaster.util.SpUtil;
import com.yjq.coinmaster.widget.HScrollView;

import java.util.ArrayList;
import java.util.List;

public class QuoteAdapter extends BaseQuickAdapter<CoinListEntity, BaseViewHolder> implements HScrollView.ScrollListener {

    public static final int TYPE_COIN = 100;
    public static final int TYPE_PAIR = 200;
    public static final int TYPE_MARKET = 300;
    public static final int TYPE_MARKET_PAIR = 400;

    private List<HScrollView> hScrollViews = new ArrayList<>();

    public QuoteAdapter(int layoutResId, @Nullable List<CoinListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinListEntity item) {
        HScrollView hScrollView = helper.getView(R.id.hscrollview);
        addHorizonViews(hScrollView);
        hScrollView.setScrollListener(this);

        ImageView imageView = helper.getView(R.id.image);
        TextView positionText = helper.getView(R.id.position);
        TextView name = helper.getView(R.id.name_1);
        TextView market = helper.getView(R.id.market);
        TextView price = helper.getView(R.id.price);
        TextView price_ = helper.getView(R.id.price_);
        TextView rate = helper.getView(R.id.rate);
        TextView amount = helper.getView(R.id.amount);
        TextView marketCap = helper.getView(R.id.marketCap);

        CoinList1 coinList1 = item.getCoinList1();
        int position = helper.getLayoutPosition();
        positionText.setText(String.valueOf(position + 1));
        name.setText(coinList1.getSymbol());
        market.setText(coinList1.getName());
        price.setText(NumberUtils.formatPriceWithUnitForward(coinList1.getPrice()));
        price_.setText(NumberUtils.formatPrice(coinList1.getChange24h()));
        if (coinList1.getChange24h() > 0) {
            price_.setTextColor(ContextCompat.getColor(mContext, R.color.coin_rise));
        } else if (coinList1.getChange24h() < 0) {
            price_.setTextColor(ContextCompat.getColor(mContext, R.color.coin_drop));
        } else {
            price_.setTextColor(ContextCompat.getColor(mContext, R.color.coin_gray));
        }
        rate.setText(NumberUtils.formatPercent(coinList1.getRate24h()));
        if (coinList1.getRate24h() > 0) {
            rate.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_text_rise_list));
        } else if (coinList1.getRate24h() < 0) {
            rate.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_text_drop_list));
        } else {
            rate.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_text_gray_list));
        }
        amount.setText(NumberUtils.formatAmount(coinList1.getAmount()));
        marketCap.setText(NumberUtils.formatAmount(coinList1.getMarketCap()));

        Glide.with(mContext).load(coinList1.getUrl()).into(imageView);

        int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
        String nameself = "";
        if (anInt == Constants.CNY) {
            nameself = "CNY";
        } else if (anInt == Constants.ETH) {
            nameself = "ETH";
        } else if (anInt == Constants.BTC) {
            nameself = "BTC";
        } else if (anInt == Constants.USDT) {
            nameself = "USDT";
        }
        if (nameself.equals(coinList1.getSymbol())) {
            price.setText(NumberUtils.formatPriceWithUnitForwardNoRate(1.0));
            price_.setText(NumberUtils.formatPriceWithUnitForwardNoRate(0.0));
            price_.setTextColor(ContextCompat.getColor(mContext, R.color.coin_gray));
            rate.setText(NumberUtils.formatPercent(0.0));
            rate.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_text_gray_list));
        }
    }


    public void addHorizonViews(HScrollView view) {
        if (!hScrollViews.contains(view)) {
            if (!hScrollViews.isEmpty()) {
                HScrollView scrollView = hScrollViews.get(0);
                int scrollX = scrollView.getScrollX();
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.scrollTo(scrollX, 0);
                    }
                });
            }
            hScrollViews.add(view);
        }
    }

    @Override
    public void scroll(int x, int y) {
        for (HScrollView hScrollView : hScrollViews) {
            hScrollView.scrollTo(x, y);
        }
    }
}

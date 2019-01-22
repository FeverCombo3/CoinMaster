package com.yjq.coinmaster.mvp.ui.quote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.kline.library.bean.CandleData;
import com.kline.library.indicator.Indicator;
import com.kline.library.indicator.Kline;
import com.kline.library.indicator.VOL;
import com.kline.library.type.Cycle;
import com.kline.library.util.IndicatorUtil;
import com.kline.library.view.KlineView;
import com.wang.avi.AVLoadingIndicatorView;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.bean.CoinDetailData;
import com.yjq.coinmaster.bean.KLineData;
import com.yjq.coinmaster.di.component.DaggerQuoteKlineComponent;
import com.yjq.coinmaster.di.module.QuoteKlineModule;
import com.yjq.coinmaster.mvp.contract.QuoteKlineContract;
import com.yjq.coinmaster.mvp.presenter.QuoteKlinePresenter;
import com.yjq.coinmaster.util.KlineToolBar;
import com.yjq.coinmaster.util.NumberUtils;
import com.yjq.coinmaster.util.SpUtil;
import com.yjq.common.base.BaseUiActivity;
import com.yjq.common.util.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuoteKlineActivity extends BaseUiActivity<QuoteKlinePresenter> implements QuoteKlineContract.View,KlineToolBar.OnKlineToolBarClickListener{

    public static final String PARAM_SYMBOL = "symbol";
    public static final String PARAM_COIN = "coin";

    private static int KLINE_SIZE = 500;

    //头部
    @BindView(R.id.tv_now_price)
    TextView mTvNowPrice;
    @BindView(R.id.tv_price_rmb)
    TextView mTvPriceRmb;
    @BindView(R.id.tv_rate)
    TextView mTvRate;
    @BindView(R.id.tv_high_price)
    TextView mTvHighPrice;
    @BindView(R.id.tv_low_price)
    TextView mTvLowPrice;
    @BindView(R.id.tv_24h_vol)
    TextView mTv24Vol;

    //K线及指标
    @BindView(R.id.klineView)
    KlineView mKlineView;
    @BindView(R.id.kline_toolbar)
    KlineToolBar toolBar;
    @BindView(R.id.kline_loading)
    AVLoadingIndicatorView mLoading;

    private List<CandleData> mData;
    private List<Indicator> indicators = new ArrayList<>();

    private String coin;
    private String symbol;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerQuoteKlineComponent.builder()
                .appComponent(appComponent)
                .quoteKlineModule(new QuoteKlineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        showSuccessStatus();
        toolBar.setOnKlineToolBarClickListener(this);

        initParams();
        initIndicators();

        mPresenter.getKlineDetails(coin);
        mPresenter.getKlineData(null,null, Cycle.getCycleFromIndex(SpUtil.getKlineCycle()).getValue(),KLINE_SIZE + "",coin);
    }

    @Override
    protected void initStatusBar() {
        StatusBarCompat.setStatusBarColor(this,ArmsUtils.getColor(this,R.color.dark_bg));
        StatusBarCompat.setStatusBarTextColor(this,false);
    }

    private void initParams(){
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                if(bundle.containsKey(PARAM_COIN)){
                    coin = bundle.getString(PARAM_COIN);
                }
                if(bundle.containsKey(PARAM_SYMBOL)){
                    symbol = bundle.getString(PARAM_SYMBOL);
                }
            }
        }
    }

    private void initIndicators() {
        indicators.clear();
        //k线绑定
        indicators.add(new Kline(this));
        //主图指标
        indicators.add(IndicatorUtil.newIndicatorInstance(SpUtil.getMainIndicator(),this));
        //vol是固定的
        indicators.add(new VOL(this));
        //副图指标
        int index = SpUtil.getSubIndicator();
        indicators.add(IndicatorUtil.newIndicatorInstance(index,this));
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_kline_p;
    }

    @Override
    public boolean useToolbar() {
        return false;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void setKlineDetails(CoinDetailData data) {
        mTvNowPrice.setText(data.getPrice() + "");
        mTvPriceRmb.setText(NumberUtils.formatPriceWithUnitForward((data.getPrice())));
        mTvRate.setText(NumberUtils.formatPercent(data.getRate24h()));
        if (data.getChange24h()>0){
            mTvRate.setTextColor(ArmsUtils.getColor(this,R.color.kline_green));
        }else if (data.getChange24h()<0){
            mTvRate.setTextColor(ArmsUtils.getColor(this,R.color.kline_red));
        }else{
            mTvRate.setTextColor(ArmsUtils.getColor(this,R.color.A4));
        }
        mTvHighPrice.setText(NumberUtils.formatPrice(data.getMax24h()));
        mTvLowPrice.setText(NumberUtils.formatPrice(data.getMin24h()));
        mTv24Vol.setText(NumberUtils.formatValue(data.getVolume()));
    }

    @Override
    public void setKlineData(List<CandleData> data) {
        mLoading.hide();
        mData = data;

        for (Indicator indicator : indicators) {
            indicator.compute(mData);
        }

        mKlineView.setData(data,indicators,true);
    }

    @Override
    public void onCycleClick(Cycle cycle) {
        if(cycle != Cycle.CYCLE_FENSHI){
            mLoading.show();
            mPresenter.getKlineData(null,null, cycle.getValue(),KLINE_SIZE + "",coin);
        }else {
            Toast.makeText(this,"Api没有分时数据！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onIndicatorClick(Indicator indicator) {
        changeIndicator(indicator);
    }

    @Override
    public void onMainIndicatorHide() {

    }

    @Override
    public void onSubIndicatorHide() {

    }

    private void changeIndicator(Indicator indicator){
        for (int i=0;i<indicators.size();i++){
            if(indicators.get(i).getposition() == indicator.getposition()){
                indicators.remove(i);
                indicators.add(indicator);
                break;
            }
            if(i == indicators.size() - 1){
                indicators.add(indicator);
            }
        }
        if(mData == null){
            return;
        }
        for (Indicator i2 : indicators) {
            i2.compute(mData);
        }
        mKlineView.setData(mData, indicators, false);
    }
}

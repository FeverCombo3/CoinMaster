package com.yjq.coinmaster.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.kline.library.bean.CandleData;
import com.yjq.coinmaster.bean.CoinDetailData;
import com.yjq.coinmaster.bean.KLineData;
import com.yjq.coinmaster.mvp.contract.QuoteKlineContract;
import com.yjq.coinmaster.request.CoinDetailDataRequest;
import com.yjq.coinmaster.request.KLineRequest;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class QuoteKlinePresenter extends BasePresenter<QuoteKlineContract.Model, QuoteKlineContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public QuoteKlinePresenter(QuoteKlineContract.Model model, QuoteKlineContract.View view) {
        super(model, view);
    }

    public void getKlineDetails(String coin){
        CoinDetailDataRequest coinDetailDataRequest = new CoinDetailDataRequest();
        coinDetailDataRequest.setCoin(coin);
        mModel.getKlineDetailsData(coinDetailDataRequest)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CoinDetailData>(mErrorHandler) {
                    @Override
                    public void onNext(CoinDetailData coinDetailData) {
                        if(coinDetailData != null){
                            mRootView.setKlineDetails(coinDetailData);
                        }
                    }
                });
    }

    public void getKlineData(String pair,String market,String interval,String number,String coin){
        KLineRequest request = new KLineRequest();
        request.setPair(pair);
        request.setMarket(market);
        request.setInterval(interval);
        request.setNumber(number);
        request.setCoin(coin);

        mModel.getKlineData(request)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<KLineData>>(mErrorHandler) {
                    @Override
                    public void onNext(List<KLineData> kLineData) {
                        if(kLineData != null){
                            List<CandleData> candleData = new ArrayList<>();
                            for (KLineData data : kLineData){
                                CandleData c = new CandleData();
                                c.id = data.getTimeStamp();
                                c.open = data.getOpenPrice();
                                c.high = data.getHighPrice();
                                c.low = data.getLowPrice();
                                c.close = data.getClosePrice();
                                c.vol = data.getVolume();
                                candleData.add(c);
                            }
                            mRootView.setKlineData(candleData);
                        }
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}

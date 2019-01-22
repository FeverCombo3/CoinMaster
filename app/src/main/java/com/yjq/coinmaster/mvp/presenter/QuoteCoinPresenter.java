package com.yjq.coinmaster.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yjq.coinmaster.base.Result;
import com.yjq.coinmaster.bean.CoinData;
import com.yjq.coinmaster.bean.CoinList1;
import com.yjq.coinmaster.bean.CoinListEntity;
import com.yjq.coinmaster.mvp.contract.QuoteCoinContract;
import com.yjq.coinmaster.request.CoinListRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class QuoteCoinPresenter extends BasePresenter<QuoteCoinContract.Model, QuoteCoinContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public QuoteCoinPresenter(QuoteCoinContract.Model model, QuoteCoinContract.View view) {
        super(model, view);
    }

    /**
     * 获取数据
     * @param request
     * @param status(0刷新,1加载更多,2更新数据)
     */
    public void getData(CoinListRequest request, int status) {
        mModel.getCoinListData(request)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Result<CoinData>>(mErrorHandler) {
                    @Override
                    public void onNext(Result<CoinData> coinDataResult) {
                        CoinData coinData = coinDataResult.getData();
                        List<CoinList1> coinList = coinData.getCoinList();
                        List<CoinListEntity> mCoinListEntities = new ArrayList<>();
                        for (CoinList1 coinList1 : coinList) {
                            coinList1.setUrl(coinData.getBaseUrl() + coinList1.getCmcId() + ".png");
                            CoinListEntity coinListEntity = new CoinListEntity(CoinListEntity.TYPE_COIN, coinList1);
                            mCoinListEntities.add(coinListEntity);
                        }
                        switch (status){
                            case 0:
                                mRootView.setNewData(mCoinListEntities);
                                break;
                            case 1:
                                mRootView.setData(mCoinListEntities);
                                break;
                            case 2:
                                mRootView.refreshData(mCoinListEntities);
                                break;
                        }

                    }
                });
    }

    public void refreshData(CoinListRequest request,int status){
        Observable.interval(5,5, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i("yjq", "第 " + aLong + " 次轮询" );
                        getData(request,status);
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

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

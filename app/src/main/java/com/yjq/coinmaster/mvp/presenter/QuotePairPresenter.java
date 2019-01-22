package com.yjq.coinmaster.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yjq.coinmaster.base.Result;
import com.yjq.coinmaster.bean.CoinListEntity;
import com.yjq.coinmaster.bean.PairData;
import com.yjq.coinmaster.bean.PairList1;
import com.yjq.coinmaster.mvp.contract.QuotePairContract;
import com.yjq.coinmaster.request.CoinListRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class QuotePairPresenter extends BasePresenter<QuotePairContract.Model, QuotePairContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public QuotePairPresenter(QuotePairContract.Model model, QuotePairContract.View view) {
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
                .subscribe(new ErrorHandleSubscriber<Result<PairData>>(mErrorHandler) {
                    @Override
                    public void onNext(Result<PairData> pairDataResult) {
                        List<PairList1> pairList = pairDataResult.getData().getPairList();
                        List<CoinListEntity> mCoinListEntities = new ArrayList<>();
                        for (PairList1 pairList1 : pairList) {
                            pairList1.setUrl(pairDataResult.getData().getBaseUrl()+pairList1.getCmcId()+".png");
                            CoinListEntity coinListEntity = new CoinListEntity(CoinListEntity.TYPE_PAIR,pairList1);
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
                                break;
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

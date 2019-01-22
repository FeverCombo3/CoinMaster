package com.yjq.coinmaster.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.yjq.coinmaster.bean.NewsDetail;
import com.yjq.coinmaster.mvp.contract.NewsDetailsContract;

import java.util.HashMap;
import java.util.Map;


@ActivityScope
public class NewsDetailsPresenter extends BasePresenter<NewsDetailsContract.Model, NewsDetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public NewsDetailsPresenter(NewsDetailsContract.Model model, NewsDetailsContract.View rootView) {
        super(model, rootView);
    }

    public void getData(String newsId){
        Map<String ,Object> map = new HashMap<String ,Object>();
        map.put("newsID", newsId);
        String json = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("charset = utf=8"), json);
        mModel.getDetails(requestBody)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<NewsDetail>(mErrorHandler) {
                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        if(newsDetail != null){
                            mRootView.setData(newsDetail);
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

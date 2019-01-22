package com.yjq.coinmaster.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yjq.coinmaster.bean.DeepNewsData;
import com.yjq.coinmaster.mvp.contract.DeepNewsContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DeepNewsPresenter extends BasePresenter<DeepNewsContract.Model,DeepNewsContract.View>{

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private String lastNewsTime;

    @Inject
    public DeepNewsPresenter(DeepNewsContract.Model model, DeepNewsContract.View view) {
        super(model, view);
    }

    public void getData(boolean refresh){
        Map<String ,Object> map = new HashMap<String ,Object>();
        if(refresh){
            lastNewsTime = String.valueOf(System.currentTimeMillis());
        }
        map.put("lastDateV", lastNewsTime);
        map.put("type", "新闻聚焦");
        map.put("from", "app");
        map.put("pageSize", 20);
        String json = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("charset = utf=8"), json);
        mModel.getDeepNewsList(requestBody)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<DeepNewsData>>(mErrorHandler) {
                    @Override
                    public void onNext(List<DeepNewsData> data) {
                        if(data != null ){
                            if(data.size() > 0){
                                lastNewsTime = data.get(data.size() - 1).getCreatetime();
                            }
                            if(refresh){
                                mRootView.refreshData(data);
                            }else {
                                mRootView.setData(data);
                            }
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

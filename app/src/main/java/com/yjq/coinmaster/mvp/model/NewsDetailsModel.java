package com.yjq.coinmaster.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yjq.coinmaster.base.Result;
import com.yjq.coinmaster.bean.NewsDetail;
import com.yjq.coinmaster.mvp.contract.NewsDetailsContract;
import com.yjq.coinmaster.mvp.model.service.NewsService;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;


@ActivityScope
public class NewsDetailsModel extends BaseModel implements NewsDetailsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public NewsDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<NewsDetail> getDetails(RequestBody requestBody) {

        return mRepositoryManager.obtainRetrofitService(NewsService.class)
                .getNewsDetailData(requestBody)
                .flatMap(new Function<Result<NewsDetail>, ObservableSource<NewsDetail>>() {
                    @Override
                    public ObservableSource<NewsDetail> apply(Result<NewsDetail> newsDetailResult) throws Exception {
                        NewsDetail data = newsDetailResult.getData();
                        return Observable.just(data);
                    }
                });
    }
}
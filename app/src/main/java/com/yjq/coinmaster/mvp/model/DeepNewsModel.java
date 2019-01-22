package com.yjq.coinmaster.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yjq.coinmaster.base.Result;
import com.yjq.coinmaster.bean.DeepNewsData;
import com.yjq.coinmaster.mvp.contract.DeepNewsContract;
import com.yjq.coinmaster.mvp.contract.FlashMewsContract;
import com.yjq.coinmaster.mvp.model.service.NewsService;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorld;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorldResult;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;

@ActivityScope
public class DeepNewsModel extends BaseModel implements DeepNewsContract.Model{

    @Inject
    Application mApplication;

    @Inject
    public DeepNewsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<DeepNewsData>> getDeepNewsList(RequestBody requestBody) {
        return mRepositoryManager.obtainRetrofitService(NewsService.class)
                .getDeepNewsData(requestBody)
                .flatMap(new Function<Result<List<DeepNewsData>>, ObservableSource<List<DeepNewsData>>>() {
                    @Override
                    public ObservableSource<List<DeepNewsData>> apply(Result<List<DeepNewsData>> listResult) throws Exception {
                        List<DeepNewsData> data = listResult.getData();
                        return Observable.just(data);
                    }
                });
    }
}

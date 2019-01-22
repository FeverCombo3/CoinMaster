package com.yjq.coinmaster.mvp.model;

import android.app.Application;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.lzy.okgo.model.Response;
import com.yjq.coinmaster.mvp.contract.FlashMewsContract;
import com.yjq.coinmaster.mvp.model.service.NewsService;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorld;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorldResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@ActivityScope
public class FlashNewsModel extends BaseModel implements FlashMewsContract.Model{

    @Inject
    Application mApplication;

    @Inject
    public FlashNewsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<BiWorld>> getBiWorldList(String timeStamp) {
        return mRepositoryManager.obtainRetrofitService(NewsService.class)
                .getBiWorldList(timeStamp)
                .flatMap(new Function<BiWorldResult, ObservableSource<List<BiWorld>>>() {
                    @Override
                    public ObservableSource<List<BiWorld>> apply(BiWorldResult result) throws Exception {
                        List<BiWorld> data = result.data;
                       // ArrayList<BiWorld> biWorlds = (ArrayList<BiWorld>) JSON.parseArray(data, BiWorld.class);
                        return Observable.just(data);
                    }
                });
    }
}

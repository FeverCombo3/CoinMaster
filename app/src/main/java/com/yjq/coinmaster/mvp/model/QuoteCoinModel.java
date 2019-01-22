package com.yjq.coinmaster.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yjq.coinmaster.base.Result;
import com.yjq.coinmaster.bean.CoinData;
import com.yjq.coinmaster.mvp.contract.QuoteCoinContract;
import com.yjq.coinmaster.mvp.model.service.QuoteService;
import com.yjq.coinmaster.request.CoinListRequest;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@ActivityScope
public class QuoteCoinModel extends BaseModel implements QuoteCoinContract.Model{

    @Inject
    Application mApplication;

    @Inject
    public QuoteCoinModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<CoinData>> getCoinListData(CoinListRequest request) {

        return mRepositoryManager.obtainRetrofitService(QuoteService.class)
                .getCoinListData(request)
                .flatMap(new Function<Result<CoinData>, ObservableSource<Result<CoinData>>>() {
                    @Override
                    public ObservableSource<Result<CoinData>> apply(Result<CoinData> coinDataResult) throws Exception {
                        return Observable.just(coinDataResult);
                    }
        });
    }
}

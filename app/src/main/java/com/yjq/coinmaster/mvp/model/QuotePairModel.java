package com.yjq.coinmaster.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yjq.coinmaster.base.Result;
import com.yjq.coinmaster.bean.PairData;
import com.yjq.coinmaster.mvp.contract.QuotePairContract;
import com.yjq.coinmaster.mvp.model.service.QuoteService;
import com.yjq.coinmaster.request.CoinListRequest;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@ActivityScope
public class QuotePairModel extends BaseModel implements QuotePairContract.Model{

    @Inject
    Application mApplication;

    @Inject
    public QuotePairModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<PairData>> getCoinListData(CoinListRequest request) {
        return mRepositoryManager.obtainRetrofitService(QuoteService.class)
                .getPairListData(request)
                .flatMap(new Function<Result<PairData>, ObservableSource<Result<PairData>>>() {
                    @Override
                    public ObservableSource<Result<PairData>> apply(Result<PairData> pairDataResult) throws Exception {
                        return Observable.just(pairDataResult);
                    }
                });
    }
}

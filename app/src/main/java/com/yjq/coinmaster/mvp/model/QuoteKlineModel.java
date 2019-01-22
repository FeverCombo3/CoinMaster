package com.yjq.coinmaster.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yjq.coinmaster.base.Result;
import com.yjq.coinmaster.bean.CoinDetailData;
import com.yjq.coinmaster.bean.KLineData;
import com.yjq.coinmaster.bean.PairData;
import com.yjq.coinmaster.mvp.contract.QuoteKlineContract;
import com.yjq.coinmaster.mvp.contract.QuotePairContract;
import com.yjq.coinmaster.mvp.model.service.QuoteService;
import com.yjq.coinmaster.request.CoinDetailDataRequest;
import com.yjq.coinmaster.request.CoinListRequest;
import com.yjq.coinmaster.request.KLineRequest;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@ActivityScope
public class QuoteKlineModel extends BaseModel implements QuoteKlineContract.Model{

    @Inject
    Application mApplication;

    @Inject
    public QuoteKlineModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<CoinDetailData> getKlineDetailsData(CoinDetailDataRequest coinDetailDataRequest) {
        return mRepositoryManager.obtainRetrofitService(QuoteService.class)
                .getCoinDetail(coinDetailDataRequest)
                .flatMap(new Function<Result<CoinDetailData>, ObservableSource<CoinDetailData>>() {
                    @Override
                    public ObservableSource<CoinDetailData> apply(Result<CoinDetailData> coinDetailDataResult) throws Exception {
                        return Observable.just(coinDetailDataResult.getData());
                    }
                });
    }

    @Override
    public Observable<List<KLineData>> getKlineData(KLineRequest kLineRequest) {
        return mRepositoryManager.obtainRetrofitService(QuoteService.class)
                .getCoinKline(kLineRequest)
                .flatMap(new Function<Result<List<KLineData>>, ObservableSource<List<KLineData>>>() {
                    @Override
                    public ObservableSource<List<KLineData>> apply(Result<List<KLineData>> listResult) throws Exception {
                        return Observable.just(listResult.getData());
                    }
                });
    }
}

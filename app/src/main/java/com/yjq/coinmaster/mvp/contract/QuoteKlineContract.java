package com.yjq.coinmaster.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.kline.library.bean.CandleData;
import com.yjq.coinmaster.bean.CoinDetailData;
import com.yjq.coinmaster.bean.KLineData;
import com.yjq.coinmaster.request.CoinDetailDataRequest;
import com.yjq.coinmaster.request.KLineRequest;

import java.util.List;

import io.reactivex.Observable;

public interface QuoteKlineContract {

    interface View extends IView{
        void setKlineDetails(CoinDetailData data);

        void setKlineData(List<CandleData> data);
    }


    interface Model extends IModel{
        Observable<CoinDetailData> getKlineDetailsData(CoinDetailDataRequest coinDetailDataRequest);

        Observable<List<KLineData>> getKlineData(KLineRequest kLineRequest);
    }


}

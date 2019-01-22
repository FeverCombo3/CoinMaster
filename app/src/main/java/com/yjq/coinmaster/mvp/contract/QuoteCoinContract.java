package com.yjq.coinmaster.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yjq.coinmaster.base.Result;
import com.yjq.coinmaster.bean.CoinData;
import com.yjq.coinmaster.bean.CoinListEntity;
import com.yjq.coinmaster.request.CoinListRequest;

import java.util.List;

import io.reactivex.Observable;

public interface QuoteCoinContract {

    interface Model extends IModel{
        Observable<Result<CoinData>> getCoinListData(CoinListRequest request);
    }

    interface View extends IView{

        void setNewData(List<CoinListEntity> data);

        void setData(List<CoinListEntity> data);

        void refreshData(List<CoinListEntity> data);
    }
}

package com.yjq.coinmaster.mvp.model.service;

import com.yjq.coinmaster.base.Result;
import com.yjq.coinmaster.bean.CoinData;
import com.yjq.coinmaster.bean.CoinDetailData;
import com.yjq.coinmaster.bean.ExchangeRate;
import com.yjq.coinmaster.bean.KLineData;
import com.yjq.coinmaster.bean.PairData;
import com.yjq.coinmaster.constant.Constants;
import com.yjq.coinmaster.request.CoinDetailDataRequest;
import com.yjq.coinmaster.request.CoinListRequest;
import com.yjq.coinmaster.request.KLineRequest;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface QuoteService {

    @Headers({DOMAIN_NAME_HEADER + Constants.Domain.QUOTE})
    @POST("get_coin_list.do")
    Observable<Result<CoinData>> getCoinListData(@Body CoinListRequest coinListRequest);

    @Headers({DOMAIN_NAME_HEADER + Constants.Domain.QUOTE})
    @POST("get_pair_list.do")
    Observable<Result<PairData>> getPairListData(@Body CoinListRequest coinListRequest);

    @Headers({DOMAIN_NAME_HEADER + Constants.Domain.QUOTE})
    @POST("get_exchange_rate.do")
    Observable<Result<ExchangeRate>> getExchangeRate(@Body RequestBody requestBody);

    @Headers({DOMAIN_NAME_HEADER + Constants.Domain.QUOTE})
    @POST("get_coin_index.do")
    Observable<Result<CoinDetailData>> getCoinDetail(@Body CoinDetailDataRequest coinDetailDataRequest);

    @Headers({DOMAIN_NAME_HEADER + Constants.Domain.QUOTE})
    @POST("get_coin_kline.do")
    Observable<Result<List<KLineData>>> getCoinKline(@Body KLineRequest kLineRequest);
}

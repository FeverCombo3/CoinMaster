package com.yjq.coinmaster.mvp.model.service;

import com.yjq.coinmaster.base.Result;
import com.yjq.coinmaster.bean.DeepNewsData;
import com.yjq.coinmaster.bean.NewsDetail;
import com.yjq.coinmaster.constant.Constants;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorldResult;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface NewsService {

    @Headers({
            DOMAIN_NAME_HEADER + Constants.Domain.BIWORLD,
            "user-agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36"
    })
    @GET("newsv17/index?size=20&client=pc")
    Observable<BiWorldResult> getBiWorldList(@Query("timestamp") String timeStamp);


    @Headers({DOMAIN_NAME_HEADER + Constants.Domain.QUOTE})
    @POST("news_all.do")
    Observable<Result<List<DeepNewsData>>> getDeepNewsData(@Body RequestBody body);


    @Headers({DOMAIN_NAME_HEADER + Constants.Domain.QUOTE})
    @POST("news_detail.do")
    Observable<Result<NewsDetail>> getNewsDetailData(@Body RequestBody requestBody);

}

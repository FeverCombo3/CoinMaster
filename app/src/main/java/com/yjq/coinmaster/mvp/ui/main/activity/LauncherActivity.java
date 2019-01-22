package com.yjq.coinmaster.mvp.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.base.Result;
import com.yjq.coinmaster.bean.ExchangeRate;
import com.yjq.coinmaster.constant.Constants;
import com.yjq.coinmaster.mvp.model.service.QuoteService;
import com.yjq.coinmaster.util.SpUtil;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LauncherActivity extends BaseActivity{

    @BindView(R.id.iv_launcher)
    ImageView iv;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_launcher;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getExchange();
        iv.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
                ArmsUtils.startActivity(intent);
                finish();
            }
        },2000);

    }


    private void getExchange(){
        RequestBody requestBody = RequestBody.create(MediaType.parse("charset = utf=8"), "{}");
        ArmsUtils.obtainAppComponentFromContext(this)
                .repositoryManager()
                .obtainRetrofitService(QuoteService.class)
                .getExchangeRate(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(this))
                .subscribe(new ErrorHandleSubscriber<Result<ExchangeRate>>(  ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler()) {
                    @Override
                    public void onNext(Result<ExchangeRate> exchangeRateResult) {
                        ExchangeRate rate = exchangeRateResult.getData();
                        if(rate != null){
                            SpUtil.saveDouble(Utils.getApp(), Constants.RATE_Usdt,rate.getRateUsdt());
                            SpUtil.saveDouble(Utils.getApp(), Constants.RATE_CNY,rate.getRateCny());
                            SpUtil.saveDouble(Utils.getApp(), Constants.RATE_BTC,rate.getRateBtc());
                            SpUtil.saveDouble(Utils.getApp(), Constants.RATE_BTH,rate.getRateEth());
                        }
                    }
                });
    }
}

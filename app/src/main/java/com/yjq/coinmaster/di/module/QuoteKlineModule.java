package com.yjq.coinmaster.di.module;


import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.mvp.contract.QuoteCoinContract;
import com.yjq.coinmaster.mvp.contract.QuoteKlineContract;
import com.yjq.coinmaster.mvp.model.QuoteCoinModel;
import com.yjq.coinmaster.mvp.model.QuoteKlineModel;

import dagger.Module;
import dagger.Provides;

@Module
public class QuoteKlineModule {

    private QuoteKlineContract.View view;

    public QuoteKlineModule(QuoteKlineContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    QuoteKlineContract.View provideQuoteKlineView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    QuoteKlineContract.Model provideQuoteKlineModel(QuoteKlineModel module){
        return module;
    }
}

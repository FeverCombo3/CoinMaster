package com.yjq.coinmaster.di.module;


import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.mvp.contract.QuoteCoinContract;
import com.yjq.coinmaster.mvp.model.QuoteCoinModel;

import dagger.Module;
import dagger.Provides;

@Module
public class QuoteCoinModule {

    private QuoteCoinContract.View view;

    public QuoteCoinModule(QuoteCoinContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    QuoteCoinContract.View provideQuoteView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    QuoteCoinContract.Model provideQuoteModel(QuoteCoinModel module){
        return module;
    }
}

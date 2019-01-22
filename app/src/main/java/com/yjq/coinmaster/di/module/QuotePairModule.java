package com.yjq.coinmaster.di.module;


import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.mvp.contract.QuotePairContract;
import com.yjq.coinmaster.mvp.model.QuotePairModel;

import dagger.Module;
import dagger.Provides;

@Module
public class QuotePairModule {

    private QuotePairContract.View view;

    public QuotePairModule(QuotePairContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    QuotePairContract.View provideQuotePairView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    QuotePairContract.Model provideQuotePairModel(QuotePairModel module){
        return module;
    }
}

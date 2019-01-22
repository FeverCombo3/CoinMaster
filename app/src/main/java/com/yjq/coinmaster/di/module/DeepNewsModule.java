package com.yjq.coinmaster.di.module;


import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.mvp.contract.DeepNewsContract;
import com.yjq.coinmaster.mvp.contract.FlashMewsContract;
import com.yjq.coinmaster.mvp.model.DeepNewsModel;
import com.yjq.coinmaster.mvp.model.FlashNewsModel;

import dagger.Module;
import dagger.Provides;

@Module
public class DeepNewsModule {

    private DeepNewsContract.View view;

    public DeepNewsModule(DeepNewsContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    DeepNewsContract.View provideDeepNewsView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    DeepNewsContract.Model provideDeepMewsModel(DeepNewsModel module){
        return module;
    }
}

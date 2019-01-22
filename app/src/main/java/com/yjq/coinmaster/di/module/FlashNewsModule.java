package com.yjq.coinmaster.di.module;


import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.mvp.contract.FlashMewsContract;
import com.yjq.coinmaster.mvp.model.FlashNewsModel;

import dagger.Module;
import dagger.Provides;

@Module
public class FlashNewsModule {

    private FlashMewsContract.View view;

    public FlashNewsModule(FlashMewsContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    FlashMewsContract.View provideFlashNewsView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    FlashMewsContract.Model provideFlashMewsModel(FlashNewsModel module){
        return module;
    }
}

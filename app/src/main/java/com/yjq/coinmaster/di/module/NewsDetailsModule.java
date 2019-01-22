package com.yjq.coinmaster.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.yjq.coinmaster.mvp.contract.NewsDetailsContract;
import com.yjq.coinmaster.mvp.model.NewsDetailsModel;


@Module
public class NewsDetailsModule {
    private NewsDetailsContract.View view;

    /**
     * 构建NewsDetailsActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NewsDetailsModule(NewsDetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsDetailsContract.View provideNewsDetailsActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsDetailsContract.Model provideNewsDetailsActivityModel(NewsDetailsModel model) {
        return model;
    }
}
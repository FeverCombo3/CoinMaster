package com.yjq.coinmaster.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yjq.coinmaster.di.module.NewsDetailsModule;

import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.mvp.ui.news.NewsDetailsActivity;

@ActivityScope
@Component(modules = NewsDetailsModule.class, dependencies = AppComponent.class)
public interface NewsDetailsComponent {
    void inject(NewsDetailsActivity activity);
}
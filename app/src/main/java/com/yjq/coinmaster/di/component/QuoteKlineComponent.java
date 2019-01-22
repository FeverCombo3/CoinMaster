package com.yjq.coinmaster.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.di.module.QuoteKlineModule;
import com.yjq.coinmaster.mvp.ui.quote.QuoteKlineActivity;

import dagger.Component;

@ActivityScope
@Component(modules = QuoteKlineModule.class,dependencies = AppComponent.class)
public interface QuoteKlineComponent {

    void inject(QuoteKlineActivity activity);
}

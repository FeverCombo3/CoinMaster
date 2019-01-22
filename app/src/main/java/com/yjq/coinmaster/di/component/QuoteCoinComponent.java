package com.yjq.coinmaster.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.di.module.QuoteCoinModule;
import com.yjq.coinmaster.mvp.ui.quote.QuoteCoinFragment;

import dagger.Component;

@ActivityScope
@Component(modules = QuoteCoinModule.class,dependencies = AppComponent.class)
public interface QuoteCoinComponent {

    void inject(QuoteCoinFragment fragment);
}

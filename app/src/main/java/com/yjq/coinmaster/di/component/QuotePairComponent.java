package com.yjq.coinmaster.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.di.module.QuotePairModule;
import com.yjq.coinmaster.mvp.ui.quote.QuotePairFragment;

import dagger.Component;

@ActivityScope
@Component(modules = QuotePairModule.class,dependencies = AppComponent.class)
public interface QuotePairComponent {

    void inject(QuotePairFragment fragment);
}

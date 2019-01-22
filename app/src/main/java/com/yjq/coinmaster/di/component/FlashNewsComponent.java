package com.yjq.coinmaster.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.di.module.FlashNewsModule;
import com.yjq.coinmaster.mvp.ui.news.FlashNewsFragment;

import dagger.Component;

@ActivityScope
@Component(modules = FlashNewsModule.class,dependencies = AppComponent.class)
public interface FlashNewsComponent {

    void inject(FlashNewsFragment fragment);
}

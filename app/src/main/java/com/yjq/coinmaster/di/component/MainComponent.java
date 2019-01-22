package com.yjq.coinmaster.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yjq.coinmaster.di.module.MainModule;

import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.mvp.ui.main.activity.MainActivity;

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
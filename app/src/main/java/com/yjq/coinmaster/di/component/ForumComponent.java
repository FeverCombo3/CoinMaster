package com.yjq.coinmaster.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.di.module.ForumModule;
import com.yjq.coinmaster.mvp.ui.forum.ForumFragment;

import dagger.Component;

@ActivityScope
@Component(modules = ForumModule.class,dependencies = AppComponent.class)
public interface ForumComponent {

    void inject(ForumFragment fragment);
}

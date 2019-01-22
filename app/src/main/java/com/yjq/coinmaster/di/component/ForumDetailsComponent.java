package com.yjq.coinmaster.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.di.module.ForumDetailsModule;
import com.yjq.coinmaster.di.module.ForumModule;
import com.yjq.coinmaster.mvp.ui.forum.ForumDetailsActivity;
import com.yjq.coinmaster.mvp.ui.forum.ForumFragment;

import dagger.Component;

@ActivityScope
@Component(modules = ForumDetailsModule.class,dependencies = AppComponent.class)
public interface ForumDetailsComponent {

    void inject(ForumDetailsActivity activity);
}

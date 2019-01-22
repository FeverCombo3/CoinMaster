package com.yjq.coinmaster.di.module;


import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.mvp.contract.ForumContract;
import com.yjq.coinmaster.mvp.model.ForumModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ForumModule {

    private ForumContract.View view;

    public ForumModule(ForumContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    ForumContract.View provideForumView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ForumContract.Model provideForumModel(ForumModel module){
        return module;
    }
}

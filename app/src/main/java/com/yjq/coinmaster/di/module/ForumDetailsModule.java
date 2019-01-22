package com.yjq.coinmaster.di.module;


import com.jess.arms.di.scope.ActivityScope;
import com.yjq.coinmaster.mvp.contract.ForumContract;
import com.yjq.coinmaster.mvp.contract.ForumDetailsContract;
import com.yjq.coinmaster.mvp.model.ForumDetailsModel;
import com.yjq.coinmaster.mvp.model.ForumModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ForumDetailsModule {

    private ForumDetailsContract.View view;

    public ForumDetailsModule(ForumDetailsContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    ForumDetailsContract.View provideForumDetailsView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ForumDetailsContract.Model provideForumModel(ForumDetailsModel module){
        return module;
    }
}

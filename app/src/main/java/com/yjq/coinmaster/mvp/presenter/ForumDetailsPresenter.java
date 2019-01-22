package com.yjq.coinmaster.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yjq.coinmaster.bean.ForumDetail;
import com.yjq.coinmaster.mvp.contract.ForumDetailsContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class ForumDetailsPresenter extends BasePresenter<ForumDetailsContract.Model,ForumDetailsContract.View>{

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ForumDetailsPresenter(ForumDetailsContract.Model model, ForumDetailsContract.View view){
        super(model,view);
    }

    public void getDetails(String url){
        mModel.getDetails(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<ForumDetail>(mErrorHandler) {
                    @Override
                    public void onNext(ForumDetail forumDetail) {
                        if(forumDetail != null){
                            mRootView.setContent(forumDetail.content);
                            mRootView.setForumTitle(forumDetail.title);
                            mRootView.setComment(forumDetail.comments);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}

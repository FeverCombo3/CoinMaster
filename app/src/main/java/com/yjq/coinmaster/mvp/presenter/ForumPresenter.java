package com.yjq.coinmaster.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yjq.coinmaster.bean.Post;
import com.yjq.coinmaster.mvp.contract.ForumContract;
import com.yjq.common.constant.Constant;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class ForumPresenter extends BasePresenter<ForumContract.Model, ForumContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ForumPresenter(ForumContract.Model model, ForumContract.View view) {
        super(model, view);
    }

    /**
     * 获取数据
     * @param page 页码
     */
    public void getData(int page,boolean refresh) {
        mModel.getPostData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<Post>>(mErrorHandler) {
                    @Override
                    public void onNext(List<Post> posts) {
                        if(posts == null)return;
                        if(refresh){
                            mRootView.refreshData(posts);
                        }else {
                            mRootView.setData(posts);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showLoadSirView(Constant.LoadSir.NO_NETWORK);
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

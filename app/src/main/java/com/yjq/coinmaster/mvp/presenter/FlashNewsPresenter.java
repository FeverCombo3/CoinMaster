package com.yjq.coinmaster.mvp.presenter;

import android.app.Application;

import com.blockin.rvadapter.bean.RcvSectionWrapper;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yjq.coinmaster.mvp.contract.FlashMewsContract;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorld;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorldData;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorldSection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class FlashNewsPresenter extends BasePresenter<FlashMewsContract.Model, FlashMewsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FlashNewsPresenter(FlashMewsContract.Model model, FlashMewsContract.View view) {
        super(model, view);
    }

    /**
     * 获取数据
     * @param timeStamp
     * @param refresh
     */
    public void getData(String timeStamp, boolean refresh) {
        mModel.getBiWorldList(timeStamp)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<BiWorld>>(mErrorHandler) {
                    @Override
                    public void onNext(List<BiWorld> biWorlds) {
                        if(biWorlds != null){
                            buildNewsData((ArrayList<BiWorld>) biWorlds,refresh);
                        }
                    }
                });
    }

    //最晚日期
    private String lastDate = "";

    private void buildNewsData(ArrayList<BiWorld> biWorlds, boolean refresh){
        if(biWorlds == null) return;
        List<RcvSectionWrapper<BiWorldSection, BiWorldData>> list = new ArrayList<>();
        for (BiWorld biWorld : biWorlds){
            List<String> top = biWorld.top;
            if(top != null && top.size() != 0){
                Collections.reverse(top);
                BiWorldSection section = new BiWorldSection();
                String topDate = "";
                for (String s : top){
                    topDate += s + " ";
                }
                if(refresh){
                    section.date = topDate;
                    list.add(new RcvSectionWrapper<BiWorldSection, BiWorldData>(true,section,null));
                    lastDate = topDate;
                }else {
                    if(!lastDate.equals(topDate)){
                        section.date = topDate;
                        list.add(new RcvSectionWrapper<BiWorldSection, BiWorldData>(true,section,null));
                        lastDate = topDate;
                    }
                }
            }


            List<BiWorldData> dataList = biWorld.buttom;
            for (BiWorldData data : dataList){
                list.add(new RcvSectionWrapper<BiWorldSection, BiWorldData>(false, null, data));
            }
        }
        if(refresh){
            mRootView.refreshData(list);
        }else {
            mRootView.setData(list);
        }

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

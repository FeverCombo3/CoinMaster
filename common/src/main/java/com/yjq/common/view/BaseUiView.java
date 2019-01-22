package com.yjq.common.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.jess.arms.mvp.IView;

public interface BaseUiView extends IView{

    void initView(View mRootView);
    int getContentViewId();
    void onInitView();
    boolean useToolbar();
    int getBackgroundRes();
    void setAppContentBackground(int res);
    void setAppNavBackGround(int res);
    void setTitle(String title);
    void onNavLeftClick();
    Activity getActivity();
    View getLoadView();
    void showLoadSirView(String status);
    void onShowTransport(Context context, View view, String status);
    void onViewReload();
}

package com.yjq.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.yjq.common.R;
import com.yjq.common.constant.Constant;
import com.yjq.common.util.StatusBarCompat;
import com.yjq.common.view.BaseUiView;

public abstract class BaseUiActivity<P extends IPresenter> extends BaseActivity<P> implements BaseUiView, IView {
    protected Toolbar toolbar;
    protected LinearLayout mLLContent;
    private LoadService loadService;
    private View baseParent;


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.common_ui_activity;
    }

    @Override
    public void initView(View mRootView) {
        mLLContent = mRootView.findViewById(R.id.base_content);
        baseParent = mRootView.findViewById(R.id.base_parent);
        getLayoutInflater().inflate(getContentViewId(), (LinearLayout) mRootView.findViewById(R.id.base_content), true);
        toolbar = mRootView.findViewById(R.id.toolbar);
        if (useToolbar()) {
            toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initStatusBar();
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID != 0) {
            //如果initView返回0,框架则不会调用setContentView()
            View mRootView = (View) getLayoutInflater().inflate(layoutResID, null);
            setContentView(mRootView);
            initView(mRootView);
            onInitView();
        }
    }

    @Override
    public void onInitView() {
        if (getLoadView() != null) {
            loadService = LoadSir.getDefault().register(getLoadView(), new Callback.OnReloadListener() {
                @Override
                public void onReload(View view) {
                    onViewReload();
                }
            });
            showLoadingStatus();
        }

    }

    @Override
    public void setTitle(String title) {
//        if (useToolbar()) {
//            getSupportActionBar().setTitle(title);
//        } else {
//            toolbar.setTitle(title);
//        }
        if(useToolbar()){
            getSupportActionBar().setTitle("");
        }
        TextView tvTitle= toolbar.findViewById(R.id.tv_title);
        tvTitle.setText(title);
    }

    @Override
    public int getBackgroundRes() {
        return 0;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onNavLeftClick() {
        onBackPressed();
    }

    @Override
    public void setAppContentBackground(int res) {
        if (baseParent != null && res != 0) {
            baseParent.setBackgroundResource(res);
        }
    }

    @Override
    public void setAppNavBackGround(int res) {
        if (toolbar != null && res != 0) {
            toolbar.setBackgroundResource(res);
        }
    }

    @Override
    public View getLoadView() {
        return mLLContent;
    }

    @Override
    public void onViewReload() {

    }

    protected void initStatusBar(){
        StatusBarCompat.setStatusBarTextColor(this,true);
    }

    @Override
    public void showLoadSirView(final String status) {
        if (loadService != null) {
            if (Constant.LoadSir.SUCCESS.equals(status)) {
                //加载完成
                loadService.showSuccess();
                return;
            }
            AppComponent appComponent = ((BaseApplication) getApplication()).getAppComponent();
            if (appComponent.extras().containsKey(status) && appComponent.extras().get(status) instanceof Callback) {
                Callback callback = (Callback) appComponent.extras().get(status);
                loadService.setCallBack(callback.getClass(), new Transport() {
                    @Override
                    public void order(Context context, View view) {
                        onShowTransport(context, view, status);
                    }
                });
                loadService.showCallback(callback.getClass());
            }
        }
    }

    @Override
    public void onShowTransport(Context context, View view, String status) {

    }

    protected void showLoadingStatus() {
        showLoadSirView(Constant.LoadSir.LOADING);
    }

    protected void showSuccessStatus() {
        showLoadSirView(Constant.LoadSir.SUCCESS);
    }
}

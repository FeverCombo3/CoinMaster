package com.yjq.coinmaster.mvp.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.chaychan.library.BottomBarLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yjq.coinmaster.di.component.DaggerMainComponent;
import com.yjq.coinmaster.di.module.MainModule;
import com.yjq.coinmaster.mvp.contract.MainContract;
import com.yjq.coinmaster.mvp.presenter.MainPresenter;

import com.yjq.coinmaster.R;
import com.yjq.coinmaster.mvp.ui.forum.ForumFragment;
import com.yjq.coinmaster.mvp.ui.mine.MineFragment;
import com.yjq.coinmaster.mvp.ui.news.NewsFragment;
import com.yjq.coinmaster.mvp.ui.quote.QuoteCoinFragment;
import com.yjq.coinmaster.mvp.ui.quote.QuoteFragment;
import com.yjq.coinmaster.widget.NoScrollViewPager;
import com.yjq.common.adapter.SimplePagerAdapter;
import com.yjq.common.base.BaseUiActivity;
import com.yjq.common.util.StatusBarCompat;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MainActivity extends BaseUiActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.vp_content)
    NoScrollViewPager mVpContent;

    @BindView(R.id.bottom_bar)
    BottomBarLayout mBottomBarLayout;

    SimplePagerAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        List<Fragment> fragments = new ArrayList<>(4);
        fragments.add(new QuoteFragment());
        fragments.add(new NewsFragment());
        fragments.add(new ForumFragment());
        fragments.add(new MineFragment());

        mAdapter = new SimplePagerAdapter(fragments, getSupportFragmentManager());
        mVpContent.setAdapter(mAdapter);
        mVpContent.setOffscreenPageLimit(fragments.size());
        mBottomBarLayout.setViewPager(mVpContent);
    }


    @Override
    public void onInitView() {
        showSuccessStatus();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean useToolbar() {
        return false;
    }
}

package com.yjq.common.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jess.arms.mvp.IPresenter;
import com.yjq.common.R;
import com.yjq.common.adapter.AdapterViewPager;

import java.util.ArrayList;

public abstract class ViewPagerFragment<P extends IPresenter> extends BaseUiFragment<P> implements ViewPager.OnPageChangeListener{

    protected ViewPager mViewPager;
    protected TabLayout mTabLayout;

    public abstract String[] getTitles();
    public abstract ArrayList<Fragment> getFragment();

    @Override
    public int getContentViewId() {
        return R.layout.common_fragment_pager;
    }

    @Override
    public void findView(View rootView) {
        mTabLayout = rootView.findViewById(R.id.tab_layout);
        mViewPager = rootView.findViewById(R.id.view_pager);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void initView(View mRootView) {
        mViewPager.setAdapter(new AdapterViewPager(getChildFragmentManager(), getFragment(), getTitles()));
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);

        initTabLayout();
    }

    public abstract void initTabLayout();
}

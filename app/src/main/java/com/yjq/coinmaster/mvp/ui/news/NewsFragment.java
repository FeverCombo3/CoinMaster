package com.yjq.coinmaster.mvp.ui.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;

import com.jess.arms.di.component.AppComponent;
import com.yjq.common.base.ViewPagerFragment;

import java.util.ArrayList;

public class NewsFragment extends ViewPagerFragment{

    private String[] titles = {"快讯", "深度"};

    @Override
    public String[] getTitles() {
        return titles;
    }

    @Override
    public ArrayList<Fragment> getFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FlashNewsFragment());
        fragments.add(new DeepNewsFragment());
        return fragments;
    }

    @Override
    public void initTabLayout() {
        mTabLayout.setTabGravity(Gravity.FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor(Color.BLACK);
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onInitView() {

    }

    @Override
    public boolean useToolbar() {
        return false;
    }
}

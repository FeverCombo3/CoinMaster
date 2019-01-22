package com.yjq.coinmaster.mvp.ui.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jess.arms.di.component.AppComponent;
import com.yjq.coinmaster.R;
import com.yjq.common.base.BaseUiFragment;

public class MineFragment extends BaseUiFragment{

    @Override
    public int getContentViewId() {
        return R.layout.fragment_mine;
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
    public void findView(View rootView) {

    }

    @Override
    public void onInitView() {

    }

    @Override
    public boolean useToolbar() {
        return false;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}

package com.yjq.coinmaster.mvp.ui.news.biworld;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


import com.yjq.coinmaster.R;

import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

public class BiWorldRefreshViewHolder extends BGARefreshViewHolder{
    private ImageView mHeaderIcon;
    private TextView mHeaderStatusTv;

    private String mPullDownRefreshText = "下拉刷新";
    private String mReleaseRefreshText = "释放更新";
    private String mRefreshingText = "加载中...";
    private String mLastUpdateTime = "上次更新 ";

    private RotateAnimation mRotateAnim;

    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public BiWorldRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
        initAnim();
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.layout_bi_world_refresh_header, null);
            mHeaderIcon = mRefreshHeaderView.findViewById(R.id.iv_header);
            mHeaderStatusTv = mRefreshHeaderView.findViewById(R.id.tv_header_mid);
            mHeaderStatusTv.setText(mPullDownRefreshText);
        }
        return mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
        mHeaderIcon.setRotation(moveYDistance);
    }

    @Override
    public void changeToIdle() {

    }

    @Override
    public void changeToPullDown() {
        mHeaderStatusTv.setText(mPullDownRefreshText);
    }

    @Override
    public void changeToReleaseRefresh() {
        mHeaderStatusTv.setText(mReleaseRefreshText);
    }

    @Override
    public void changeToRefreshing() {
        mHeaderStatusTv.setText(mRefreshingText);
        mHeaderIcon.startAnimation(mRotateAnim);
    }

    @Override
    public void onEndRefreshing() {
        mHeaderStatusTv.setText(mPullDownRefreshText);
        mHeaderIcon.clearAnimation();
        mHeaderIcon.setRotation(0);

    }

    private void initAnim(){
        mRotateAnim =new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnim.setDuration(500); // 设置动画时间
        mRotateAnim.setInterpolator(new LinearInterpolator()); // 设置插入器
        mRotateAnim.setRepeatCount(-1);
    }
}

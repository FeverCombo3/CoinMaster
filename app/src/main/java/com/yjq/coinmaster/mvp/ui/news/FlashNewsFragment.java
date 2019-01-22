package com.yjq.coinmaster.mvp.ui.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blockin.rvadapter.RcvMultiAdapter;
import com.blockin.rvadapter.bean.RcvSectionWrapper;
import com.blockin.rvadapter.listener.RcvLoadMoreListener;
import com.blockin.rvadapter.ui.RcvDefLoadMoreView;
import com.blockin.rvadapter.ui.RcvStickyLayout;
import com.jess.arms.di.component.AppComponent;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.di.component.DaggerFlashNewsComponent;
import com.yjq.coinmaster.di.module.FlashNewsModule;
import com.yjq.coinmaster.mvp.contract.FlashMewsContract;
import com.yjq.coinmaster.mvp.presenter.FlashNewsPresenter;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorld;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorldData;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorldRefreshViewHolder;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorldSection;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorldSectionAdapter;
import com.yjq.common.base.BaseUiFragment;
import com.yjq.common.constant.Constant;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import timber.log.Timber;

public class FlashNewsFragment extends BaseUiFragment<FlashNewsPresenter> implements FlashMewsContract.View,RcvLoadMoreListener,BGARefreshLayout.BGARefreshLayoutDelegate{

    @BindView(R.id.sr_swipe)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.rv_bi_world)
    RecyclerView mRecyclerView;
    @BindView(R.id.stickyLayout)
    RcvStickyLayout mStickyLayout;

    private long timeStamp;
    private BiWorldSectionAdapter mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_bi_world;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFlashNewsComponent
                .builder()
                .appComponent(appComponent)
                .flashNewsModule(new FlashNewsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initView(View mRootView) {

        showLoadSirView(Constant.LoadSir.LOADING);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new BiWorldSectionAdapter(mContext,null);
        RcvDefLoadMoreView loadMoreView = new RcvDefLoadMoreView.Builder()
                .build(mContext);
        mAdapter.setLoadMoreLayout(loadMoreView);

        mRecyclerView.setAdapter(mAdapter);

        mStickyLayout.attachToRecyclerView(mRecyclerView);

        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BiWorldRefreshViewHolder(mContext,false);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        timeStamp = getSecondTimestamp(new Date());
    }

    @Override
    protected void onFragmentFirstVisible() {
        Timber.i(" onFragmentFirstVisible");
        mPresenter.getData(timeStamp + "",true);
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

    /**
     * 获取精确到秒的时间戳
     * @return
     */
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        timeStamp = getSecondTimestamp(new Date());
        mAdapter.disableLoadMore();
        mPresenter.getData(timeStamp + "",true);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onLoadMoreRequest() {
        mPresenter.getData(timeStamp + "",false);
    }

    @Override
    public void refreshData(List<RcvSectionWrapper<BiWorldSection, BiWorldData>> list) {
        showLoadSirView(Constant.LoadSir.SUCCESS);
        mAdapter.refreshDatas(list);
        mRefreshLayout.endRefreshing();
        //开启加载更多
        mAdapter.enableLoadMore(this);
    }

    @Override
    public void setData(List<RcvSectionWrapper<BiWorldSection, BiWorldData>> list) {
        showLoadSirView(Constant.LoadSir.SUCCESS);
        mAdapter.notifyLoadMoreSuccess(list, true);
    }
}

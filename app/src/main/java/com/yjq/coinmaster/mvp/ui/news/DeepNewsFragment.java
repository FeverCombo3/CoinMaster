package com.yjq.coinmaster.mvp.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.bean.DeepNewsData;
import com.yjq.coinmaster.constant.Constants;
import com.yjq.coinmaster.di.component.DaggerDeepNewsComponent;
import com.yjq.coinmaster.di.module.DeepNewsModule;
import com.yjq.coinmaster.mvp.contract.DeepNewsContract;
import com.yjq.coinmaster.mvp.presenter.DeepNewsPresenter;
import com.yjq.common.base.BaseUiFragment;
import com.yjq.common.constant.Constant;

import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

public class DeepNewsFragment extends BaseUiFragment<DeepNewsPresenter> implements DeepNewsContract.View,BGARefreshLayout.BGARefreshLayoutDelegate,BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.bga_deep_news)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.rv_deep_news)
    RecyclerView mRecyclerView;

    private DeepNewsAdapter mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_deep_news;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDeepNewsComponent.builder()
                .appComponent(appComponent)
                .deepNewsModule(new DeepNewsModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onFragmentFirstVisible() {
        showLoadSirView(Constant.LoadSir.LOADING);
        mPresenter.getData(true);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new DeepNewsAdapter(R.layout.item_deep_news_list,null);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.NEWS_ID, mAdapter.getData().get(position).getNewsID());
                bundle.putString(Constants.NEWS_TITLE, mAdapter.getData().get(position).getTitle());
                bundle.putString(Constants.NEWS_TIME,  mAdapter.getData().get(position).getCreatetime());
                bundle.putString(Constants.NEWS_AUTH, mAdapter.getData().get(position).getAuthor());
                intent.putExtras(bundle);
                ArmsUtils.startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mContext,false);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
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

    @Override
    public void refreshData(List<DeepNewsData> data) {
        showLoadSirView(Constant.LoadSir.SUCCESS);
        mRefreshLayout.endRefreshing();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setNewData(data);
        if(data.size() < 20){
            mAdapter.loadMoreEnd(true);
        }
    }

    @Override
    public void setData(List<DeepNewsData> data) {
        showLoadSirView(Constant.LoadSir.SUCCESS);
        mRefreshLayout.endRefreshing();
        if(data.size() > 0){
            mAdapter.addData(data);
        }
        if(data.size() < 20){
            mAdapter.loadMoreEnd(true);
        }else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getData(true);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getData(false);
    }
}

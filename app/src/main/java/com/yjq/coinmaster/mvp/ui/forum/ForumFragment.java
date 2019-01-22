package com.yjq.coinmaster.mvp.ui.forum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.bean.Post;
import com.yjq.coinmaster.di.component.DaggerForumComponent;
import com.yjq.coinmaster.di.module.ForumModule;
import com.yjq.coinmaster.mvp.contract.ForumContract;
import com.yjq.coinmaster.mvp.presenter.ForumPresenter;
import com.yjq.common.base.BaseUiFragment;

import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

public class ForumFragment extends BaseUiFragment<ForumPresenter> implements ForumContract.View,BaseQuickAdapter.RequestLoadMoreListener,BGARefreshLayout.BGARefreshLayoutDelegate{

    @BindView(R.id.bga_forum)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.sv_forum)
    RecyclerView mRecyclerView;

    private ForumAdapter mAdapter;

    private int page = 1;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_forum;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerForumComponent.builder()
                .appComponent(appComponent)
                .forumModule(new ForumModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration divider = new DividerItemDecoration(Utils.getApp(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(Utils.getApp(),R.drawable.linecolor));
        mRecyclerView.addItemDecoration(divider);
        mAdapter = new ForumAdapter(R.layout.item_forum,null);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Post post = (Post) adapter.getItem(position);
                if(post != null){
                    Intent intent = new Intent(mContext,ForumDetailsActivity.class);
                    intent.putExtra(ForumDetailsActivity.PARAM_URL,post.url);
                    ArmsUtils.startActivity(intent);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mContext,false);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    protected void onFragmentFirstVisible() {
        showLoadingState();
        mPresenter.getData(page,true);
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
    public void onViewReload() {
        showLoadingState();
        mPresenter.getData(page,true);
    }

    @Override
    public void refreshData(List<Post> posts) {
        showSuccessState();
        mRefreshLayout.endRefreshing();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setNewData(posts);
        if(posts.size() < 20){
            mAdapter.loadMoreEnd(true);
        }
    }

    @Override
    public void setData(List<Post> posts) {
        showSuccessState();
        page ++;
        mRefreshLayout.endRefreshing();
        if(posts.size() > 0){
            mAdapter.addData(posts);
        }
        if(posts.size() < 20){
            mAdapter.loadMoreEnd(true);
        }else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getData(page,false);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        mAdapter.setEnableLoadMore(false);
        mPresenter.getData(page,true);

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}

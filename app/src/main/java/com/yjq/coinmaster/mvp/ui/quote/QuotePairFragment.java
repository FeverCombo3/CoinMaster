package com.yjq.coinmaster.mvp.ui.quote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.bean.CoinListEntity;
import com.yjq.coinmaster.di.component.DaggerQuotePairComponent;
import com.yjq.coinmaster.di.module.QuotePairModule;
import com.yjq.coinmaster.mvp.contract.QuotePairContract;
import com.yjq.coinmaster.mvp.presenter.QuotePairPresenter;
import com.yjq.coinmaster.request.CoinListRequest;
import com.yjq.coinmaster.widget.HScrollView;
import com.yjq.common.base.BaseUiFragment;

import java.util.List;

import butterknife.BindView;

public class QuotePairFragment extends BaseUiFragment<QuotePairPresenter> implements QuotePairContract.View,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.hscrollview)
    HScrollView mHScrollview;

    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipeLayout;

    QuoteMultiAdapter mAdapter;

    CoinListRequest request;
    int page = 1;


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerQuotePairComponent.builder()
                .appComponent(appComponent)
                .quotePairModule(new QuotePairModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_pair_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Utils.getApp()));
        DividerItemDecoration divider = new DividerItemDecoration(Utils.getApp(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(Utils.getApp(),R.drawable.linecolor));
        mRecyclerView.addItemDecoration(divider);

        mAdapter = new QuoteMultiAdapter(null);
        mAdapter.addHorizonViews(mHScrollview);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onFragmentFirstVisible() {
        showLoadingState();
        request = new CoinListRequest();
        request.setPageType("page");
        request.setColumn("amount24h");
        request.setPageSize("20");
        request.setPage(String.valueOf(page));
        request.setSort("desc");
        request.setMarket(null);

        mPresenter.getData(request,0);
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
    public void setNewData(List<CoinListEntity> data) {
        showSuccessState();
        mSwipeLayout.setRefreshing(false);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setNewData(data);
        if(data.size() < 20){
            mAdapter.loadMoreEnd();
        }else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void setData(List<CoinListEntity> data) {
        showSuccessState();
        mSwipeLayout.setRefreshing(false);
        mAdapter.addData(data);
        if(data.size() < 20){
            mAdapter.loadMoreEnd();
        }else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        request.setPage(String.valueOf(page));
        mAdapter.setEnableLoadMore(false);
        mPresenter.getData(request,0);
    }

    @Override
    public void onLoadMoreRequested() {
        page ++;
        request.setPage(String.valueOf(page));
        mPresenter.getData(request,1);
    }
}

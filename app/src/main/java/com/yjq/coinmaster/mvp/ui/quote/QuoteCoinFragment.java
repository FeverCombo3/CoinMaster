package com.yjq.coinmaster.mvp.ui.quote;

import android.content.Intent;
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
import com.jess.arms.utils.ArmsUtils;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.bean.CoinListEntity;
import com.yjq.coinmaster.di.component.DaggerQuoteCoinComponent;
import com.yjq.coinmaster.di.module.QuoteCoinModule;
import com.yjq.coinmaster.mvp.contract.QuoteCoinContract;
import com.yjq.coinmaster.mvp.presenter.QuoteCoinPresenter;
import com.yjq.coinmaster.request.CoinListRequest;
import com.yjq.coinmaster.widget.HScrollView;
import com.yjq.common.base.BaseUiFragment;

import java.util.List;

import butterknife.BindView;


public class QuoteCoinFragment extends BaseUiFragment<QuoteCoinPresenter> implements QuoteCoinContract.View,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{

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
    public int getContentViewId() {
        return R.layout.fragment_coin_list;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerQuoteCoinComponent
                    .builder()
                    .appComponent(appComponent)
                    .quoteCoinModule(new QuoteCoinModule(this))
                    .build()
                    .inject(this);
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
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CoinListEntity coinListEntity = (CoinListEntity)adapter.getItem(position);
                if(coinListEntity != null){
                    Intent intent = new Intent(getActivity(),QuoteKlineActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(QuoteKlineActivity.PARAM_COIN,coinListEntity.getCoinList1().getCoin());
                    bundle.putString(QuoteKlineActivity.PARAM_SYMBOL,coinListEntity.getCoinList1().getSymbol());
                    intent.putExtras(bundle);
                    ArmsUtils.startActivity(intent);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mSwipeLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onFragmentFirstVisible() {
        showLoadingState();
        request = new CoinListRequest();
        request.setPageType("page");
        request.setColumn("marketCap");
        request.setPageSize("20");
        request.setPage(String.valueOf(page));
        request.setSort("desc");
        request.setCoinType(null);
        mPresenter.getData(request,0);
    }

    @Override
    public void onResume() {
        super.onResume();
//        CoinListRequest request2 = new CoinListRequest();
//        request2.setPageType("all");
//        request2.setPage(String.valueOf(page));
//        request2.setSort("desc");
//        request2.setColumn("marketCap");
//        request2.setPageSize("20");
//        request2.setCoinType(null);
//        mPresenter.refreshData(request2,2);
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
    public void refreshData(List<CoinListEntity> data) {
//        mAdapter.setNewData(data);
//        mAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        page ++;
        request.setPage(String.valueOf(page));
        mPresenter.getData(request,1);
    }

    @Override
    public void onRefresh() {
        page = 1;
        request.setPage(String.valueOf(page));
        mAdapter.setEnableLoadMore(false);
        mPresenter.getData(request,0);
    }
}

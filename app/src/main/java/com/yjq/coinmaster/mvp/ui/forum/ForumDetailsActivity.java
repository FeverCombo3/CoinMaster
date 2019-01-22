package com.yjq.coinmaster.mvp.ui.forum;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.jess.arms.di.component.AppComponent;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.bean.Comment;
import com.yjq.coinmaster.di.component.DaggerForumDetailsComponent;
import com.yjq.coinmaster.di.module.ForumDetailsModule;
import com.yjq.coinmaster.mvp.contract.ForumDetailsContract;
import com.yjq.coinmaster.mvp.presenter.ForumDetailsPresenter;
import com.yjq.common.base.BaseUiActivity;

import java.util.List;

import butterknife.BindView;

public class ForumDetailsActivity extends BaseUiActivity<ForumDetailsPresenter> implements ForumDetailsContract.View{

    public static final String PARAM_URL = "forum_url";

    @BindView(R.id.rv_forum_details)
    RecyclerView mRecyclerView;

    WebView webView;

    TextView title;

    private String url;

    private ForumDetailsAdapter mAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerForumDetailsComponent.builder()
                .appComponent(appComponent)
                .forumDetailsModule(new ForumDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        url = getIntent().getStringExtra(PARAM_URL);

        setTitle("详情");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divider = new DividerItemDecoration(Utils.getApp(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(Utils.getApp(),R.drawable.linecolor));
        mRecyclerView.addItemDecoration(divider);
        mAdapter = new ForumDetailsAdapter(R.layout.item_forum_details,null);

        initHeader();

        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getDetails(url);
    }

    private void initHeader(){
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_forum_details_header, null);
        title = headerView.findViewById(R.id.tv_forum_details_title);
        webView = headerView.findViewById(R.id.web_forum_details);
        initVebView();
        mAdapter.addHeaderView(headerView);
    }

    private void initVebView(){
        WebSettings webSettings = webView.getSettings();
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        //设置开启javascript支持
        webSettings.setJavaScriptEnabled(true);
        //设置支持缩放
        webSettings.setSupportZoom(false);
        //开启缩放工具（会出现放大缩小的按钮）
        webSettings.setBuiltInZoomControls(false);
        //WebView两种缓存（网页、H5）方式，此处网页不缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //允许JS打开新窗口（默认false）
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //打开本地缓存供JS调用
        webSettings.setDomStorageEnabled(true);
        //H5缓存内存大小（已过时，不必设置已可自动管理） //
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        //H5缓存路径
        String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
        //H5缓存大小
        webSettings.setAppCachePath(absolutePath);
        //是否允许WebView访问内部文件（默认true）
        webSettings.setAllowFileAccess(false);
        //支持存储H5缓存
        webSettings.setAppCacheEnabled(true);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_forum_details;
    }

    @Override
    public boolean useToolbar() {
        return true;
    }


    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void setContent(String content) {
        showSuccessStatus();
        webView.loadDataWithBaseURL(null,content, "text/html", "utf-8", null);
    }

    @Override
    public void setComment(List<Comment> comments) {
        mAdapter.setNewData(comments);
    }

    @Override
    public void setForumTitle(String title) {
        this.title.setText(title);
    }
}

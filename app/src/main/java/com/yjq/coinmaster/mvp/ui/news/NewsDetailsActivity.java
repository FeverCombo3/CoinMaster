package com.yjq.coinmaster.mvp.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yjq.coinmaster.R;
import com.yjq.coinmaster.bean.NewsDetail;
import com.yjq.coinmaster.constant.Constants;
import com.yjq.coinmaster.di.component.DaggerNewsDetailsComponent;
import com.yjq.coinmaster.di.module.NewsDetailsModule;
import com.yjq.coinmaster.mvp.contract.NewsDetailsContract;
import com.yjq.coinmaster.mvp.presenter.NewsDetailsPresenter;
import com.yjq.coinmaster.mvp.ui.news.biworld.DateUtil;
import com.yjq.common.base.BaseUiActivity;
import com.yjq.common.constant.Constant;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class NewsDetailsActivity extends BaseUiActivity<NewsDetailsPresenter> implements NewsDetailsContract.View {

    @BindView(R.id.article_detail_web_view)
    WebView mWebContent;
    @BindView(R.id.time)
    TextView mTvTime;
    @BindView(R.id.from)
    TextView mTvFrom;
    @BindView(R.id.titletext)
    TextView mTvTitle;

    private String newsId;
    private String title;
    private String timestr;
    private String auth ;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNewsDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newsDetailsModule(new NewsDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        initParams();

        initVebView();

        setTitle("新闻详情");

        mPresenter.getData(newsId);
    }

    private void initParams(){
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                if(bundle.containsKey(Constants.NEWS_ID)){
                    newsId = bundle.getString(Constants.NEWS_ID);
                }
                if(bundle.containsKey(Constants.NEWS_TITLE)){
                    title = bundle.getString(Constants.NEWS_TITLE);
                    mTvTitle.setText(Html.fromHtml(title));
                }
                if(bundle.containsKey(Constants.NEWS_AUTH)){
                    auth = bundle.getString(Constants.NEWS_AUTH);
                }
                if(bundle.containsKey(Constants.NEWS_TIME)){
                    timestr = bundle.getString(Constants.NEWS_TIME);
                }
            }
        }
    }

    private void initVebView(){
        WebSettings webSettings = mWebContent.getSettings();
        mWebContent.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebContent.setVerticalScrollBarEnabled(false); //垂直不显示
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
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void setData(NewsDetail newsDetail) {
        showSuccessStatus();
        newsDetail.setContent(newsDetail.getContent());
        mTvTime.setText(DateUtil.formatTime2String(Long.parseLong(timestr)));
        mTvFrom.setText(auth);
        mWebContent.loadDataWithBaseURL(null,getNewContent(newsDetail.getContent()), "text/html", "utf-8", null);
    }

    private String getNewContent(String htmltext){

        Document doc= Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");
        }

        return doc.toString();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_news_details;
    }

    @Override
    public boolean useToolbar() {
        return true;
    }
}

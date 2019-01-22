package com.yjq.coinmaster.mvp.ui.news.biworld;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockin.rvadapter.RcvMultiAdapter;
import com.blockin.rvadapter.bean.RcvSectionWrapper;
import com.blockin.rvadapter.listener.RcvLoadMoreListener;
import com.blockin.rvadapter.ui.RcvDefLoadMoreView;
import com.blockin.rvadapter.ui.RcvStickyLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yjq.coinmaster.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

public class BiWorldActivity extends AppCompatActivity implements RcvLoadMoreListener,BGARefreshLayout.BGARefreshLayoutDelegate{

    private long timeStamp;

    private RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    private RcvStickyLayout mStickyLayout;
    private BiWorldSectionAdapter mAdapter;
    private TextView tvNewData;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            showInAnim();
        }
    };

    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bi_world);

        timeStamp = getSecondTimestamp(new Date());

        mRecyclerView = findViewById(R.id.rv_bi_world);
        mStickyLayout = findViewById(R.id.stickyLayout);
        tvNewData = findViewById(R.id.tv_new_data);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new BiWorldSectionAdapter(this,null);
        RcvDefLoadMoreView loadMoreView = new RcvDefLoadMoreView.Builder()
                .build(this);
        mAdapter.setLoadMoreLayout(loadMoreView);
        mAdapter.setOnChildClickListener(R.id.iv_share, new RcvMultiAdapter.OnChildClickListener<RcvSectionWrapper<BiWorldSection, BiWorldData>>() {
            @Override
            public void onChildClicked(int viewId, View view, RcvSectionWrapper<BiWorldSection, BiWorldData> biWorldSectionBiWorldDataRcvSectionWrapper, int layoutPosition) {
//                ShareImagePopu shareImagePopu = new ShareImagePopu(BiWorldActivity.this);
//                shareImagePopu.setData(biWorldSectionBiWorldDataRcvSectionWrapper.getData());
//                shareImagePopu.showBottom();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mStickyLayout.attachToRecyclerView(mRecyclerView);

        initRefreshLayout();

        mRefreshLayout.beginRefreshing();

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask,1000,10000);
    }

    private void initRefreshLayout(){
        mRefreshLayout = findViewById(R.id.sr_swipe);
        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BiWorldRefreshViewHolder(this,true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    private void getBiWorldNewsData(final boolean refresh){
        //user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36
       // Log.i("yjq","时间戳" + timeStamp);
        OkGo.<String>get("https://www.bishijie.com/api/newsv17/index?size=20&client=pc")
                .headers("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                .params("timestamp",timeStamp)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try{
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            String data = jsonObject.getString("data");

                            ArrayList<BiWorld> biWorlds = (ArrayList<BiWorld>) JSON.parseArray(data, BiWorld.class);

                            // Log.i("yjq","数据长度" + biWorlds.size());

                            buildNewsData(biWorlds,refresh);
                            //改变时间戳
                            timeStamp = biWorlds.get(biWorlds.size() - 1).buttom.get(biWorlds.get(biWorlds.size() - 1).buttom.size() - 1).issue_time;
                        }catch (Exception e){
                            onError(null);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        //super.onError(response);
                        Toast.makeText(BiWorldActivity.this,"出错了！",Toast.LENGTH_SHORT).show();
                        mRefreshLayout.endRefreshing();
                        mAdapter.notifyLoadMoreFail();
                   //     Log.i("yjq","错误");
                    }
                });
    }

    private void showInAnim(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvNewData, "alpha", 0f,1f);
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                tvNewData.setText("有" + new Random().nextInt(10) + "条新快讯");
                tvNewData.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                tvNewData.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BiWorldActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showOutAnim();
                            }
                        });
                    }
                },3000);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private void showOutAnim(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvNewData, "alpha", 1f,0f);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                tvNewData.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.setDuration(500);
        animator.start();
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

    //最晚日期
    private String lastDate = "";

    private void buildNewsData(ArrayList<BiWorld> biWorlds, boolean refresh){
        if(biWorlds == null) return;
        List<RcvSectionWrapper<BiWorldSection, BiWorldData>> list = new ArrayList<>();
        for (BiWorld biWorld : biWorlds){
            List<String> top = biWorld.top;
            if(top != null && top.size() != 0){
                Collections.reverse(top);
                BiWorldSection section = new BiWorldSection();
                String topDate = "";
                for (String s : top){
                    topDate += s + " ";
                }
                if(refresh){
                    section.date = topDate;
                    list.add(new RcvSectionWrapper<BiWorldSection, BiWorldData>(true,section,null));
                    lastDate = topDate;
                }else {
                    if(!lastDate.equals(topDate)){
                        // Log.i("yjq","添加section：" + topDate);
                        section.date = topDate;
                        list.add(new RcvSectionWrapper<BiWorldSection, BiWorldData>(true,section,null));
                        lastDate = topDate;
                    }
                }
            }


            List<BiWorldData> dataList = biWorld.buttom;
            for (BiWorldData data : dataList){
                list.add(new RcvSectionWrapper<BiWorldSection, BiWorldData>(false, null, data));
            }
        }
        if(refresh){
           // Log.i("yjq","刷新数据：" + list.size());
            mAdapter.refreshDatas(list);
            mRefreshLayout.endRefreshing();
            //开启加载更多
            mAdapter.enableLoadMore(this);
        }else {
          //  Log.i("yjq","加载更多：" + list.size());
            mAdapter.notifyLoadMoreSuccess(list, true);
        }

    }

    @Override
    public void onLoadMoreRequest() {
        getBiWorldNewsData(false);
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        timeStamp = getSecondTimestamp(new Date());
        mAdapter.disableLoadMore();
        getBiWorldNewsData(true);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvNewData.clearAnimation();
        timer.cancel();
        timerTask.cancel();
    }
}

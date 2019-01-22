package com.yjq.coinmaster.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kline.library.indicator.BOLL;
import com.kline.library.indicator.Indicator;
import com.kline.library.indicator.KDJ;
import com.kline.library.indicator.MA;
import com.kline.library.indicator.MACD;
import com.kline.library.indicator.RSI;
import com.kline.library.indicator.ZLCP;
import com.kline.library.type.Cycle;
import com.kline.library.util.DisplayUtils;
import com.yjq.coinmaster.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by yjq
 * 2018/5/22
 * Kline上方的ToolBar
 */

public class KlineToolBar extends LinearLayout implements View.OnClickListener{
    private Context mContext;

    private List<Cycle> cycleData = new ArrayList<>();
    private List<Cycle> cycleMoreData = new ArrayList<>();

    private HashMap<Integer,View> cycleMap = new HashMap<>();
    private View funcMore,funcIndicator;

    private OnKlineToolBarClickListener mListener;

    private LinearLayout mMoreContainer,mIndicatorContainer;
    private LinearLayout mMainContainer,mSubContainer;

    private TextView mTvSubHide,mTvMainHide;

    private List<Indicator> indicatorList = new ArrayList<>();

    public KlineToolBar(Context context) {
        super(context);
        init(context);
    }

    public KlineToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KlineToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        this.setOrientation(VERTICAL);
        final LinearLayout top = new LinearLayout(mContext);
        top.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, DisplayUtils.dipToPixel(40)));
        top.setBackgroundColor(mContext.getResources().getColor(R.color.dark_bg));
        addView(top);

        initCycleData();

        for (int i =0;i<cycleData.size();i++){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_kline_cycle,null);
            view.setId(cycleData.get(i).getIndex());
            TextView cycle = view.findViewById(R.id.tv_cycle);
            cycle.setText(cycleData.get(i).getName());
            LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            view.setLayoutParams(params);
            view.setOnClickListener(this);
            cycleMap.put(cycleData.get(i).getIndex(),view);
            top.addView(view);
        }

        for (int i=0;i<2;i++){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_kline_func,null);
            TextView func = view.findViewById(R.id.tv_func);
            if(i == 0){
                func.setText("更多");
                funcMore = view;
            }else {
                func.setText("指标");
                funcIndicator = view;
            }
            LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            view.setLayoutParams(params);
            view.setOnClickListener(this);
            top.addView(view);
        }

        initCycle();

        //添加更多和指标界面
        RelativeLayout bot = new RelativeLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bot.setLayoutParams(params);

        addView(bot);

        mMoreContainer = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_kline_toolbar_more,null);
        mMoreContainer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DisplayUtils.dipToPixel(70)));

        mMoreContainer.setVisibility(GONE);
        bot.addView(mMoreContainer);

        mIndicatorContainer = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_kline_toolbar_indicator, null);
        mIndicatorContainer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DisplayUtils.dipToPixel(120)));
        mIndicatorContainer.setVisibility(GONE);
        bot.addView(mIndicatorContainer);

        mTvMainHide = mIndicatorContainer.findViewById(R.id.tv_main_hide);
        if(SpUtil.getMainIndicator() == -1){
            mTvMainHide.setTextColor(mContext.getResources().getColor(R.color.color_white));
        }

        mTvMainHide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMainContainerText();
                mTvMainHide.setTextColor(mContext.getResources().getColor(R.color.color_white));
                SpUtil.setMainIndicator(-1);
                mIndicatorContainer.setVisibility(GONE);
                if(mListener != null){
                    mListener.onMainIndicatorHide();
                }
            }
        });

        mTvSubHide = mIndicatorContainer.findViewById(R.id.tv_sub_hide);
        if(SpUtil.getSubIndicator() == -1){
            mTvSubHide.setTextColor(mContext.getResources().getColor(R.color.color_white));
        }

        mTvSubHide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSubContainerText();
                mTvSubHide.setTextColor(mContext.getResources().getColor(R.color.color_white));
                SpUtil.setSubIndicator(-1);
                mIndicatorContainer.setVisibility(GONE);
                if(mListener != null){
                    mListener.onSubIndicatorHide();
                }
            }
        });

        //填充指标列表
        indicatorList.add(new MA(mContext));
        indicatorList.add(new BOLL(mContext));
        indicatorList.add(new MACD(mContext));
        indicatorList.add(new KDJ(mContext));
        indicatorList.add(new RSI(mContext));
        indicatorList.add(new ZLCP(mContext));

        mMainContainer = mIndicatorContainer.findViewById(R.id.ll_main_container);
        mSubContainer = mIndicatorContainer.findViewById(R.id.ll_sub_container);
        for (final Indicator indicator : indicatorList){
            final TextView tv = new TextView(mContext);
            LayoutParams pm = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            tv.setPadding(DisplayUtils.dipToPixel(10),0,DisplayUtils.dipToPixel(10),0);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(16);
            tv.setText(indicator.getName());
            if(SpUtil.getSubIndicator() == indicator.getIndex() || SpUtil.getMainIndicator() == indicator.getIndex()){
                tv.setTextColor(mContext.getResources().getColor(R.color.color_white));
            }else {
                tv.setTextColor(mContext.getResources().getColor(R.color.kline_blue));
            }
            tv.setLayoutParams(pm);
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) v;
                    if(indicator.getposition() == 1){ //主图指标
                        clearMainContainerText();
                        mTvMainHide.setTextColor(mContext.getResources().getColor(R.color.kline_blue));
                        tv.setTextColor(mContext.getResources().getColor(R.color.color_white));
                        SpUtil.setMainIndicator(indicator.getIndex());
                    }else {
                        clearSubContainerText();
                        mTvSubHide.setTextColor(mContext.getResources().getColor(R.color.kline_blue));
                        tv.setTextColor(mContext.getResources().getColor(R.color.color_white));
                        SpUtil.setSubIndicator(indicator.getIndex());
                    }
                    mIndicatorContainer.setVisibility(GONE);
                    if(mListener != null){
                        mListener.onIndicatorClick(indicator);
                    }
                }
            });
            if(indicator.getposition() == 1){ //主图
                mMainContainer.addView(tv);
            }else { //副图
                mSubContainer.addView(tv);
            }
        }
    }

    private void initCycleData(){
        cycleData.add(Cycle.CYCLE_FENSHI);
        cycleData.add(Cycle.CYCLE_15MIN);
        cycleData.add(Cycle.CYCLE_30MIN);
        cycleData.add(Cycle.CYCLE_1H);
        cycleData.add(Cycle.CYCLE_1DAY);
        cycleMoreData.add(Cycle.CYCLE_5MIN);
        cycleMoreData.add(Cycle.CYCLE_1WEEK);
        cycleMoreData.add(Cycle.CYCLE_1MON);
        cycleMoreData.add(Cycle.CYCLE_1YEAR);
    }

    private void clearMainContainerText(){
        int count = mMainContainer.getChildCount();
        for (int i=0 ;i<count ;i++){
            TextView tv = (TextView) mMainContainer.getChildAt(i);
            tv.setTextColor(mContext.getResources().getColor(R.color.kline_blue));
        }
    }

    private void clearSubContainerText(){
        int count = mSubContainer.getChildCount();
        for (int i=0 ;i<count ;i++){
            TextView tv = (TextView) mSubContainer.getChildAt(i);
            tv.setTextColor(mContext.getResources().getColor(R.color.kline_blue));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //为什么要延时呢？
        int childCount = mMoreContainer.getChildCount();
        for (int i=0;i<childCount;i++){
            View view = mMoreContainer.getChildAt(i);
            if(view.getVisibility() == INVISIBLE){
                break;
            }
            view.setId(cycleMoreData.get(i).getIndex());
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        handleClick(v);
    }
    
    @SuppressLint("ResourceType")
    private void handleClick(View view){
        if(view == funcMore){
            handleMoreCLick();
        }else if(view == funcIndicator){
            handleIndicatorClick();
        }else if(isCycleIndex(view.getId())){
            handleCycleClick(view);
        }else {
            handleMoreCycleCLick(view);
        }
    }

    private void initCycle(){
        int index = SpUtil.getKlineCycle();
        if(isCycleIndex(index)){
            Iterator iterator = cycleMap.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                int key = (int) entry.getKey();
                View value = (View) entry.getValue();
                TextView cycle = value.findViewById(R.id.tv_cycle);
                View indicator = value.findViewById(R.id.indicator_cycle);
                if(key == index){
                    cycle.setTextColor(mContext.getResources().getColor(R.color.kline_blue_choose));
                    indicator.setVisibility(VISIBLE);
                }else {
                    cycle.setTextColor(mContext.getResources().getColor(R.color.kline_blue));
                    indicator.setVisibility(INVISIBLE);
                }
            }
        }else {
            TextView mt = funcMore.findViewById(R.id.tv_func);
            mt.setTextColor(mContext.getResources().getColor(R.color.color_white));
            mt.setText(Cycle.getCycleFromIndex(index).getName());
            View ml = funcMore.findViewById(R.id.indicator_func);
            ml.setVisibility(VISIBLE);
            funcMore.setBackgroundColor(mContext.getResources().getColor(R.color.dark_bg));
        }
    }

    private boolean isCycleIndex(int index){
        for (Cycle cycle : cycleData){
            if(cycle.getIndex() == index){
                return true;
            }
        }
        return false;
    }

    private void handleMoreCLick(){
        funcMore.setBackgroundColor(mContext.getResources().getColor(R.color.color_black));
        TextView more = funcMore.findViewById(R.id.tv_func);
        more.setTextColor(mContext.getResources().getColor(R.color.color_white));

        funcIndicator.setBackgroundColor(mContext.getResources().getColor(R.color.dark_bg));
        TextView indicator = funcIndicator.findViewById(R.id.tv_func);
        indicator.setTextColor(mContext.getResources().getColor(R.color.kline_blue));

        if(mIndicatorContainer.getVisibility() == VISIBLE){
            mIndicatorContainer.setVisibility(GONE);
        }

        if(mMoreContainer.getVisibility() == VISIBLE){
            mMoreContainer.setVisibility(GONE);
        }else {
            mMoreContainer.setVisibility(VISIBLE);
        }
    }

    private void handleIndicatorClick(){
        funcIndicator.setBackgroundColor(mContext.getResources().getColor(R.color.color_black));
        TextView indicator = funcIndicator.findViewById(R.id.tv_func);
        indicator.setTextColor(mContext.getResources().getColor(R.color.color_white));

        funcMore.setBackgroundColor(mContext.getResources().getColor(R.color.dark_bg));
        TextView more = funcMore.findViewById(R.id.tv_func);
        more.setTextColor(mContext.getResources().getColor(R.color.kline_blue));

        if (mMoreContainer.getVisibility() == VISIBLE){
            mMoreContainer.setVisibility(GONE);
        }

        if(mIndicatorContainer.getVisibility() == VISIBLE){
            mIndicatorContainer.setVisibility(GONE);
        }else {
            mIndicatorContainer.setVisibility(VISIBLE);
        }
    }

    private void handleCycleClick(View view){
        Iterator iterator = cycleMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            int key = (int) entry.getKey();
            View value = (View) entry.getValue();
            TextView cycle = value.findViewById(R.id.tv_cycle);
            View indicator = value.findViewById(R.id.indicator_cycle);
            if(key == view.getId()){
                cycle.setTextColor(mContext.getResources().getColor(R.color.kline_blue_choose));
                indicator.setVisibility(VISIBLE);
                SpUtil.setKlineCycle(key);
                if (mListener != null){
                    mListener.onCycleClick(Cycle.getCycleFromIndex(view.getId()));
                }

            }else {
                cycle.setTextColor(mContext.getResources().getColor(R.color.kline_blue));
                indicator.setVisibility(INVISIBLE);
            }
        }
        clearFunc();
    }

    private void handleMoreCycleCLick(View v){
        TextView tv = (TextView) v;
        TextView mt = funcMore.findViewById(R.id.tv_func);
        mt.setText(tv.getText());
        View ml = funcMore.findViewById(R.id.indicator_func);
        ml.setVisibility(VISIBLE);
        funcMore.setBackgroundColor(mContext.getResources().getColor(R.color.dark_bg));
        mMoreContainer.setVisibility(GONE);
        chooseMoreCycle();
        SpUtil.setKlineCycle(v.getId());
        if(mListener != null){
            mListener.onCycleClick(Cycle.getCycleFromIndex(v.getId()));
        }
    }

    private void clearFunc(){
        funcMore.setBackgroundColor(mContext.getResources().getColor(R.color.dark_bg));
        TextView more = funcMore.findViewById(R.id.tv_func);
        more.setText("更多");
        funcMore.findViewById(R.id.indicator_func).setVisibility(GONE);
        more.setTextColor(mContext.getResources().getColor(R.color.kline_blue));
        funcIndicator.setBackgroundColor(mContext.getResources().getColor(R.color.dark_bg));
        TextView indicator = funcIndicator.findViewById(R.id.tv_func);
        indicator.setTextColor(mContext.getResources().getColor(R.color.kline_blue));
    }

    public void chooseMoreCycle(){
        Iterator iterator = cycleMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            View value = (View) entry.getValue();
            TextView cycle = value.findViewById(R.id.tv_cycle);
            View indicator = value.findViewById(R.id.indicator_cycle);
            cycle.setTextColor(mContext.getResources().getColor(R.color.kline_blue));
            indicator.setVisibility(INVISIBLE);
        }
    }


    public interface OnKlineToolBarClickListener{
        //点击周期
        void onCycleClick(Cycle cycle);

        //点击指标
        void onIndicatorClick(Indicator indicator);

        //隐藏主图指标
        void onMainIndicatorHide();

        //隐藏副图指标
        void onSubIndicatorHide();
    }


    public void setOnKlineToolBarClickListener(OnKlineToolBarClickListener listener){
        this.mListener = listener;
    }
}

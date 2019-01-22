package com.yjq.coinmaster.mvp.ui.news.biworld;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.blockin.rvadapter.RcvSectionAdapter;
import com.blockin.rvadapter.bean.RcvSectionWrapper;
import com.blockin.rvadapter.holder.RcvHolder;
import com.yjq.coinmaster.R;

import java.util.List;


public class BiWorldSectionAdapter extends RcvSectionAdapter<BiWorldSection,BiWorldData> {

    public BiWorldSectionAdapter(Context context, List<RcvSectionWrapper<BiWorldSection, BiWorldData>> datas) {
        super(context, datas);
    }

    @Override
    public int getSectionLayoutId() {
        return R.layout.layout_section_label_bi_world;
    }

    @Override
    public void onBindSectionView(RcvHolder holder, BiWorldSection section, int position) {
        holder.setTvText(R.id.tv_section_label_bi_world,section.date);
    }

    @Override
    public int getDataLayoutId() {
        return R.layout.layout_data_bi_world;
    }

    @Override
    public void onBindDataView(RcvHolder holder, final BiWorldData data, int position) {
        holder.setTvText(R.id.tv_title,data.title);
        final ExpandableTextView tv = holder.findView(R.id.tv_content);
        tv.reset();
        tv.setInterpolator(new OvershootInterpolator());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv.isExpanded()){
                    tv.collapse();
                }else {
                    tv.expand();
                }
            }
        });
        tv.addOnExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(ExpandableTextView view) {
                data.setStatus(StatusType.STATUS_EXPAND);
            }

            @Override
            public void onCollapse(ExpandableTextView view) {
                data.setStatus(StatusType.STATUS_CONTRACT);
            }
        });
        if(!TextUtils.isEmpty(data.content)){
            tv.setText(data.content);
        }else {
            tv.setText("数据源为空");
        }

        if(data.getStatus() == StatusType.STATUS_EXPAND){
            tv.forceExpand();
        }else {
            tv.forceCollapse();
        }
        holder.setTvText(R.id.tv_date, DateUtil.formatDate2(data.issue_time * 1000));

        int rank = data.rank;
        if(rank == 1){
            holder.setImgResource(R.id.iv_circle,R.mipmap.ic_circle_red);
            holder.setTvColor(R.id.tv_title, Color.parseColor("#EE2C2C"));
            holder.setTvColor(R.id.tv_content,Color.parseColor("#EE2C2C"));
            holder.setTvColor(R.id.tv_date,Color.parseColor("#EE2C2C"));
        }else if(rank == 0){
            holder.setImgResource(R.id.iv_circle,R.mipmap.ic_circle_black);
            holder.setTvColor(R.id.tv_title, Color.parseColor("#1F1F1F"));
            holder.setTvColor(R.id.tv_content,Color.parseColor("#1F1F1F"));
            holder.setTvColor(R.id.tv_date,Color.parseColor("#999999"));
        }
    }
}

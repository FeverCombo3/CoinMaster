package com.yjq.coinmaster.mvp.ui.news;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.bean.DeepNewsData;
import com.yjq.coinmaster.mvp.ui.news.biworld.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeepNewsAdapter extends BaseQuickAdapter<DeepNewsData,BaseViewHolder>{


    public DeepNewsAdapter(int layoutResId, @Nullable List<DeepNewsData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeepNewsData item) {
        if (!TextUtils.isEmpty(item.getImg())) {
            Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.item_project_list_iv));
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.item_project_list_title_tv, item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getDescription())) {
            helper.setText(R.id.item_project_list_content_tv, item.getDescription());
        }
        if (!TextUtils.isEmpty(item.getCreatetime())) {
            helper.setText(R.id.item_project_list_time_tv, DateUtil.formatTime2String(Long.parseLong(item.getCreatetime())));
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.item_project_list_author_tv, item.getAuthor());
        }
    }


}

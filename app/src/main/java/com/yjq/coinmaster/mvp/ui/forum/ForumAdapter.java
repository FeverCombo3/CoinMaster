package com.yjq.coinmaster.mvp.ui.forum;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.bean.Post;

import java.util.List;

public class ForumAdapter extends BaseQuickAdapter<Post,BaseViewHolder>{


    public ForumAdapter(int layoutResId, @Nullable List<Post> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Post item) {
        helper.setText(R.id.tv_title,item.title);
        helper.setText(R.id.tv_author,item.author);
        helper.setText(R.id.tv_date,item.date);
        helper.setText(R.id.tv_watch,item.watch);
        helper.setText(R.id.tv_comment,item.comments);
        ImageView ivAvatar = helper.getView(R.id.iv_avatar);
        Glide.with(mContext).load(item.avatar).into(ivAvatar);
    }
}

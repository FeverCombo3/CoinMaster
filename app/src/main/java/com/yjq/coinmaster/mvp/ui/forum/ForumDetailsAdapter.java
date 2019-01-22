package com.yjq.coinmaster.mvp.ui.forum;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.bean.Comment;

import java.util.List;

public class ForumDetailsAdapter extends BaseQuickAdapter<Comment,BaseViewHolder>{

    public ForumDetailsAdapter(int layoutResId, @Nullable List<Comment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        helper.setText(R.id.tv_author,item.author);
        helper.setText(R.id.tv_comment,item.comment);
        Glide.with(mContext).load(item.avatar).into((ImageView) helper.getView(R.id.iv_avatar));
    }
}

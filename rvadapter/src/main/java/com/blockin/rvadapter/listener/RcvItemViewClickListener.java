package com.blockin.rvadapter.listener;


import com.blockin.rvadapter.holder.RcvHolder;

/**
 * Created by LWK
 * TODO 子布局点击事件监听
 * 2017/4/24
 */
public interface RcvItemViewClickListener<T>
{
    void onItemViewClicked(RcvHolder holder, T t, int position);
}

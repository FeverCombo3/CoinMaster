package com.blockin.rvadapter.listener;


import com.blockin.rvadapter.holder.RcvHolder;

/**
 * Created by LWK
 * TODO 子布局长点击事件监听
 * 2017/4/24
 */
public interface RcvItemViewLongClickListener<T>
{
    void onItemViewLongClicked(RcvHolder holder, T t, int position);
}

package com.blockin.rvadapter;

import android.content.Context;

import com.blockin.rvadapter.base.RcvBaseItemView;
import com.blockin.rvadapter.holder.RcvHolder;

import java.util.List;

/**
 * Function:RecycleView通用布局适配器【所有子布局样式统一】
 */
public abstract class RcvSingleAdapter<T> extends RcvMultiAdapter<T>
{
    protected int mLayoutId;

    public RcvSingleAdapter(Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);
        this.mLayoutId = layoutId;
        addItemView(new RcvBaseItemView<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return mLayoutId;
            }

            @Override
            public boolean isForViewType(T item, int position)
            {
                return true;
            }

            @Override
            public void onBindView(RcvHolder holder, T t, int position)
            {
                RcvSingleAdapter.this.onBindView(holder, t, position);
            }
        });
    }

    public abstract void onBindView(RcvHolder holder, T itemData, int position);
}

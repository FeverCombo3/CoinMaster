package com.yjq.coinmaster.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;


public class HScrollView extends HorizontalScrollView {

    private List<HScrollView> hScrollViews = new ArrayList<>();

    public HScrollView(Context context) {
        super(context);
    }

    public HScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void sethScrollViews(List<HScrollView> hScrollViews) {
        this.hScrollViews = hScrollViews;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
//        super.onScrollChanged(x, y, oldx, oldy);
//        Log.e("CoinListAdapter","hScrollViews.size:"+hScrollViews.size());
//        for (HScrollView view:hScrollViews){
//            view.scrollTo(x, y);
////            view.smoothScrollTo(x, y);
//        }
        if (mScrollListener!=null){
            mScrollListener.scroll(x,y);
        }
    }

    public interface ScrollListener {
        void scroll(int x, int y);
    }

    ScrollListener mScrollListener;

    public void setScrollListener(ScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

}

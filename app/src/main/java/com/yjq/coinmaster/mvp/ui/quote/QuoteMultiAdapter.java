package com.yjq.coinmaster.mvp.ui.quote;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.Nullable;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.yjq.coinmaster.bean.CoinListEntity;
import com.yjq.coinmaster.widget.HScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuoteMultiAdapter extends MultipleItemRvAdapter<CoinListEntity,BaseViewHolder> implements HScrollView.ScrollListener{

    public static final int TYPE_COIN = 100;
    public static final int TYPE_PAIR = 200;

    private List<HScrollView> hScrollViews = new ArrayList<>();
    private Map<TextView,ObjectAnimator> mAnimatorMap =new HashMap<>();

    public QuoteMultiAdapter(@Nullable List<CoinListEntity> data) {
        super(data);
        setHasStableIds(true);
        finishInitialize();
    }

    @Override
    protected int getViewType(CoinListEntity coinListEntity) {
        if (coinListEntity.getType()==CoinListEntity.TYPE_COIN){
            return TYPE_COIN;
        }else if (coinListEntity.getType()==CoinListEntity.TYPE_PAIR){
            return TYPE_PAIR;
        }
        return 0;
    }


    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new CoinListProvider(this));
        mProviderDelegate.registerProvider(new PairListProvider(this));
    }

    public void addAnim(TextView view){
        if (!mAnimatorMap.containsKey(view)){
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
            animator.setDuration(1000);
            animator.setInterpolator(new LinearInterpolator());
            mAnimatorMap.put(view,animator);
        }
    }

    public ObjectAnimator getAnimatorMap(TextView view) {
        return mAnimatorMap.get(view);
    }


    public void addHorizonViews(HScrollView view) {
        if (!hScrollViews.contains(view)) {
            if (!hScrollViews.isEmpty()) {
                HScrollView scrollView = hScrollViews.get(0);
                int scrollX = scrollView.getScrollX();
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.scrollTo(scrollX, 0);
                    }
                });
            }
            hScrollViews.add(view);
        }
    }

    @Override
    public void scroll(int x, int y) {
        for (HScrollView hScrollView : hScrollViews) {
            hScrollView.scrollTo(x, y);
        }
    }
}

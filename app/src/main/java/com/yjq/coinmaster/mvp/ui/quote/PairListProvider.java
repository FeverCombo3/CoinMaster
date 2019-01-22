package com.yjq.coinmaster.mvp.ui.quote;

import android.animation.ValueAnimator;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.yjq.coinmaster.R;
import com.yjq.coinmaster.bean.CoinListEntity;
import com.yjq.coinmaster.bean.PairList1;
import com.yjq.coinmaster.constant.Constants;
import com.yjq.coinmaster.util.NumberUtils;
import com.yjq.coinmaster.util.SpUtil;
import com.yjq.coinmaster.widget.HScrollView;

import java.util.Random;

public class PairListProvider extends BaseItemProvider<CoinListEntity,BaseViewHolder>{

    private QuoteMultiAdapter mAdapter;

    public PairListProvider(QuoteMultiAdapter mAdapter){
        this.mAdapter = mAdapter;
    }


    @Override
    public int viewType() {
        return QuoteMultiAdapter.TYPE_PAIR;
    }

    @Override
    public int layout() {
        return R.layout.pair_item_list;
    }

    @Override
    public void convert(BaseViewHolder helper, CoinListEntity data, int position) {
        RelativeLayout relativeLayout = helper.getView(R.id.layout);
        HScrollView view = helper.getView(R.id.hscrollview);
        mAdapter.addHorizonViews(view);
//        mAdapter.addAnim(view);
        view.setScrollListener(mAdapter);
//        view.sethScrollViews(mCoinListAdapter.gethScrollViews());

        ImageView imageView = helper.getView(R.id.image);
        TextView positionView = helper.getView(R.id.position);
        TextView name_1 = helper.getView(R.id.name_1);
        TextView name_2 = helper.getView(R.id.name_2);
        TextView market = helper.getView(R.id.market);
        TextView price = helper.getView(R.id.price);
        TextView price_ = helper.getView(R.id.price_);
        TextView rate = helper.getView(R.id.rate);
        TextView change = helper.getView(R.id.change);
        TextView amount = helper.getView(R.id.amount);
        TextView inflow = helper.getView(R.id.inflow);

        PairList1 pairList1 = data.getPairList1();

//               ValueAnimator colorAnim = mCoinListAdapter.getAnimatorMap(view);
//
//        int upDown = pairList1.getUpDown();
//
//        Random random = new Random();
//        int n = random.nextInt(5)+1;
//        if (position%n==0){
//            if (upDown!=0){
//                if (upDown==1){
//                    if (!colorAnim.isRunning()){
//                        relativeLayout.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shapechange));
//                        colorAnim.start();
//                    }
//                }else if (upDown==2){
//                    if (!colorAnim.isRunning()){
//                        relativeLayout.setBackground(ContextCompat.getDrawable(mContext,R.drawable.colorsecond));
//                        colorAnim.start();
//                    }
//                }
//            }
//        }
//
//        if (!colorAnim.isRunning()){
//            relativeLayout.getBackground().mutate().setAlpha(0);
//        }
//        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                relativeLayout.getBackground().mutate().setAlpha((int)colorAnim.getAnimatedValue());
//            }
//        });

        String s = pairList1.getPair();
        String pair1 = s.substring(0,s.indexOf("/"));
        String pair2 = s.substring(s.indexOf("/"),s.length());

        Glide.with(mContext).load(pairList1.getUrl()).into(imageView);
        positionView.setText(String.valueOf(position+1));
        name_1.setText(pair1);
        name_2.setText(pair2);
        market.setText(pairList1.getName());
        price.setText(NumberUtils.formatPriceWithUnitForward(pairList1.getPrice()));
        price_.setText(NumberUtils.formatCommonPrice(pairList1.getPricePair())+pair2.replace("/",""));

        rate.setText(NumberUtils.formatPercent(pairList1.getRate24h()));
        if (pairList1.getRate24h()>0){
            rate.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_text_rise_list));
        }else if (pairList1.getRate24h()<0){
            rate.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_text_drop_list));
        }else{
            rate.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_text_gray_list));
        }

        change.setText(NumberUtils.formatPrice(pairList1.getChange24h()));

        if (pairList1.getChange24h()>0){
            change.setTextColor(ContextCompat.getColor(mContext,R.color.coin_rise));
        }else if (pairList1.getChange24h()<0){
            change.setTextColor(ContextCompat.getColor(mContext,R.color.coin_drop));
        }else{
            change.setTextColor(ContextCompat.getColor(mContext,R.color.coin_gray));
        }

        amount.setText(NumberUtils.formatAmount(pairList1.getAmount24h()));
        inflow.setText(NumberUtils.formatAmount(pairList1.getNetInflow24h()));

        if (!TextUtils.isEmpty(s)){
            String priceself1 = pair2.replace("/","");
            String nameself ="";
            int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
            if (anInt == Constants.CNY){
                nameself = "CNY";
            }else if (anInt == Constants.ETH){
                nameself = "ETH";
            }else if (anInt == Constants.BTC){
                nameself = "BTC";
            }else if (anInt == Constants.USDT){
                nameself = "USDT";
            }
            if (nameself.equals(pair1)){
                price.setText(NumberUtils.formatPriceWithUnitForwardNoRate(1));
                rate.setText(NumberUtils.formatPercent(0));
                rate.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_text_gray_list));
                change.setText("0");
                change.setTextColor(ContextCompat.getColor(mContext,R.color.coin_gray));
            }else if (nameself.equals(priceself1)){
                price.setText((NumberUtils.formatPriceWithUnitForward((pairList1.getPricePair()))));
            }
        }
        helper.addOnClickListener(R.id.layout);
        helper.addOnClickListener(R.id.inner);
    }
}

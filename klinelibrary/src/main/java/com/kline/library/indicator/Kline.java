package com.kline.library.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.kline.library.R;
import com.kline.library.bean.CandleData;
import com.kline.library.util.DisplayUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * K线
 * <p>
 * Created by 25341 on 2017/5/6.
 */

public class Kline extends Indicator {

    private List<CandleData> kLists;
    private Paint paint;
    private Paint paintLine;
    private Paint TextPaint;
    private Paint rectPaint;


    public Kline(Context context) {
        super(context);
        paint = new Paint();
        //画笔
        paint.setStrokeWidth(DisplayUtils.DENSITY);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        paintLine = new Paint();
        paintLine.setStrokeWidth(2);


        TextPaint = new Paint();
        TextPaint.setTextSize(textSize);

        rectPaint = new Paint();
        rectPaint.setTextSize(textSize);
        rectPaint.setStyle(Paint.Style.FILL);
        kLists = new ArrayList<>();

    }

    @Override
    public String getName() {
        return "k线";
    }

    @Override
    public int getposition() {
        return 0;
    }

    @Override
    public int getIndex() {
        return INDICATOR_INDEX_K;
    }

    @Override
    public void drawText(Canvas canvas, float Rect_L, float Rect_T, int selectedIndex, Paint mPaint) {

    }

    @Override
    public double[] getMaxValue(int start, int stop) {

        double maxMin[] = new double[2];
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        if (kLists.size() != 0) {
            for (int i = start; i <= stop; i++) {
                CandleData kList = kLists.get(i);
                if (kList.high > max) {
                    max = kList.high;
                }
                if (kList.low < min) {
                    min = kList.low;
                }
            }
        }
        maxMin[0] = max;
        maxMin[1] = min;
        return maxMin;
    }


    @Override
    public void compute(List<CandleData> values) {
        if (kLists != null) {
            kLists.clear();
            kLists.addAll(values);
        }
//        String time = null;
//        for(int i = 0;i<kLists.size();i++){
////            String substring = candleData.getTime().substring(0, 2);
//            CandleData candleData = kLists.get(i);
//            if(candleData.getTime()!=null){
//                if (candleData.getTime().startsWith("100")){
//                    String time1 = candleData.getTime().substring(7, 9);
//                    if (time==null){
//                        time = time1;
//                    }else{
//                        if (!time.equals(time1)){
//                            if (candleData.getTime().length()<=11){
//                                candleData.setTime(candleData.getTime()+time1);
//                                time = time1;
//                            }
//                        }
//                    }
//                }
//                if (candleData.getTime().startsWith("200")){
//                    String time1 = candleData.getTime().substring(7, 9);
//                    if (time==null){
//                        time = time1;
//                        candleData.setTime(candleData.getTime()+time1);
//                    }else{
//                        if (!time.equals(time1)){
//                            if (Integer.valueOf(time1)%4==0){
//                                if (candleData.getTime().length()<=11){
//                                    candleData.setTime(candleData.getTime()+time1);
//                                    time = time1;
//                                }
//                            }
//                        }
//                    }
//                }
//                if (candleData.getTime().startsWith("300")){
//                    String time1 = candleData.getTime().substring(5, 7);
//                    if (time==null){
//                        time = time1;
//                        candleData.setTime(candleData.getTime()+time1);
//                    }else{
//                        if (!time.equals(time1)){
//                            if (candleData.getTime().length()<=11){
//                                candleData.setTime(candleData.getTime()+time1);
//                                time = time1;
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    @Override
    public void draw(Context context, Canvas canvas, List<KLineParam> kLineParams, Paint mPaint, float l, float t, float r, float b, float mScaleX, double mMainMaxValue, double mMainMinValue) {

        if (kLineParams == null || kLineParams.size() == 0) {
            return;
        }

        float startY = 0;
        float endY = 0;
        double max = Float.MIN_VALUE;
        double min = Float.MAX_VALUE;
        int maxIndex = 0;
        int minIndex = 0;
        for (int i = 0; i < kLineParams.size(); i++) {
            CandleData kList = kLineParams.get(i).candelData;
            if (kList.high > max) {
                max = kList.high;
                maxIndex = i;
            }
            if (kList.low < min) {
                min = kList.low;
                minIndex = i;
            }
        }

        TextPaint.setColor(mContext.getResources().getColor(R.color.kline_blue_choose));
        double rate = ((b - t) / (mMainMaxValue - mMainMinValue));
//        paintLine.setColor(mContext.getResources().getColor(R.color.klineview_border));
//        rectPaint.setColor(mContext.getResources().getColor(R.color.stock_list_number));
        float yx = 0;
        for (int i = 0; i < kLineParams.size(); i++) {
            KLineParam kLineParam = kLineParams.get(i);
            CandleData candelData = kLineParam.candelData;
//            String time = candelData.getTime();
//            if (time.length()>11){
//                if (time.startsWith("100")||time.startsWith("200")||time.startsWith("300")){
//                String substring = time.substring(3, 11);
//                StringBuilder builder = new StringBuilder(substring);
//                builder.insert(4,"-");
//                builder.insert(7,"-");
//                    if (yx == 0){
//                        yx = kLineParam.yx;
//                        canvas.drawLine(kLineParam.yx,t,kLineParam.yx,b,paintLine);
//    //                    canvas.drawRect(kLineParam.yx-getTextWidth(String.valueOf(builder),TextPaint)/2,
//    //                            b/*+DisplayUtils.dipToPixel(10)-getTextHeight(TextPaint)/2*/,
//    //                            kLineParam.yx+getTextWidth(String.valueOf(builder),TextPaint)/2,
//    //                            b+DisplayUtils.dipToPixel(20)/*+getTextHeight(TextPaint)/2*/,
//    //                            rectPaint);
//                        canvas.drawText(String.valueOf(builder),kLineParam.yx-getTextWidth(String.valueOf(builder),TextPaint)/2,b+DisplayUtils.dipToPixel(20)-getTextHeight(TextPaint)/2,TextPaint);
//                    }else{
//                        boolean b1 = (kLineParam.yx - yx) > ((r - l) / 6);
//                        if (b1){
//                            canvas.drawLine(kLineParam.yx,t,kLineParam.yx,b,paintLine);
//    //                        canvas.drawRect(kLineParam.yx-getTextWidth(String.valueOf(builder),TextPaint)/2,
//    //                                b/*+DisplayUtils.dipToPixel(10)-getTextHeight(TextPaint)/2*/,
//    //                                kLineParam.yx+getTextWidth(String.valueOf(builder),TextPaint)/2,
//    //                                b+DisplayUtils.dipToPixel(20)/*+getTextHeight(TextPaint)/2*/,
//    //                                rectPaint);
//                            canvas.drawText(String.valueOf(builder),kLineParam.yx-getTextWidth(String.valueOf(builder),TextPaint)/2,b+DisplayUtils.dipToPixel(20)-getTextHeight(TextPaint)/2,TextPaint);
//                            yx = kLineParam.yx;
//                        }
//                    }
//                }
//            }

            float openY = (float) (b - ((candelData.open - mMainMinValue) * rate));
            float highY = (float) (b - ((candelData.high - mMainMinValue) * rate));
            float lowY = (float) (b - ((candelData.low - mMainMinValue) * rate));
            float closeY = (float) (b - ((candelData.close - mMainMinValue) * rate));

            if (candelData.open > candelData.close) {
                startY = openY;
                endY = closeY;
                paint.setColor(context.getResources().getColor(R.color.kline_red));
            } else if (candelData.close > candelData.open) {
                startY = closeY;
                endY = openY;
                paint.setColor(context.getResources().getColor(R.color.kline_green));
            }
            // 中轴
            canvas.drawLine(kLineParam.yx, highY, kLineParam.yx, lowY, paint);

            if (Math.abs(endY - startY) < 3) {// +2避免数据一样 看不到线
                endY = endY + 3;
            }
            if (kLineParam.r - kLineParam.l > 3) {
                // 柱子
                canvas.drawRect(kLineParam.l, startY, kLineParam.r, endY, paint);
            }
        }


        float x;
        paint.setColor(context.getResources().getColor(R.color.color_white));
        x = kLineParams.get(maxIndex).yx;
        float y = (float) (b - ((max - mMainMinValue) * rate));
        float width = r - l;
        paint.setTextSize(DisplayUtils.sp2px(12));
        if (x > width / 2) {
            canvas.drawLine(x - DisplayUtils.dipToPixel(20), y, x, y, paint);
            canvas.drawText(max + "", x - DisplayUtils.dipToPixel(20) - paint.measureText(max + ""),
                    y + getTextHeight(paint) / 2, paint);

        } else {
            canvas.drawLine(x, y, x + DisplayUtils.dipToPixel(20), y, paint);

            canvas.drawText(max + "", x + DisplayUtils.dipToPixel(20), y + getTextHeight(paint) / 2, paint);
        }
        x = kLineParams.get(minIndex).yx;
        y = (float) (b - ((min - mMainMinValue) * rate));

        if (x > width / 2) {
            canvas.drawLine(x - DisplayUtils.dipToPixel(20), y, x, y, paint);
            canvas.drawText(min + "", x - DisplayUtils.dipToPixel(20) - paint.measureText(min + ""), y + getTextHeight(paint) / 2, paint);
        } else {
            canvas.drawLine(x, y, x + DisplayUtils.dipToPixel(20), y , paint);
            canvas.drawText(min + "", x + DisplayUtils.dipToPixel(20), y+ getTextHeight(paint) / 2, paint);
        }
    }
}

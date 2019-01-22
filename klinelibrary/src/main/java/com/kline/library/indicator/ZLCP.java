package com.kline.library.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;

import com.kline.library.R;
import com.kline.library.bean.CandleData;
import com.kline.library.util.DisplayUtils;
import com.kline.library.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 主力操盘
 *
 *
 */

public class ZLCP extends Indicator{
    List<Float> WZ;
    List<Float> FJ;
    private Paint textPaint;
    private Paint wzPaint;
    private Paint fjPaint;
    private PathEffect effects;
    private Path pathSd;
    private Path pathCd;


    public ZLCP(Context context) {
        super(context);
        WZ = new ArrayList<>();
        FJ = new ArrayList<>();
        textPaint = new Paint();
        textPaint.setTextSize(textSize);

        wzPaint = new Paint();
        wzPaint.setStyle(Paint.Style.STROKE);
        wzPaint.setColor(context.getResources().getColor(R.color.color_F562C4_fenshi_pink));
        wzPaint.setStrokeWidth(DisplayUtils.DENSITY);

        fjPaint = new Paint();
        fjPaint.setStyle(Paint.Style.STROKE);
        fjPaint.setColor(context.getResources().getColor(R.color.color_467DEB_fenshi_light_blue));
        fjPaint.setStrokeWidth(DisplayUtils.DENSITY);

        effects = new DashPathEffect(new float[]{15,5,15,5},0);

        pathSd = new Path();
        pathCd = new Path();

    }

    @Override
    public String getName() {
        return  "ZLCP";
    }

    @Override
    public int getposition() {
        return 3;
    }

    @Override
    public int getIndex() {
        return INDICATOR_INDEX_ZLCP;
    }

    @Override
    public void drawText(Canvas canvas, float Rect_L, float Rect_T, int selectedIndex, Paint mPaint) {
        String s ;
        String s1 ;
        if (WZ!=null&&!WZ.isEmpty()){
            Float aFloat = WZ.get(selectedIndex);
            s = UIHelper.formatVolumn(aFloat);
        }else{
            s="--";
        }

        if (FJ!=null&&!FJ.isEmpty()){
            Float aFloat = FJ.get(selectedIndex);
            s1 = UIHelper.formatVolumn(aFloat);
        }else{
            s1="--";
        }

        float currentX = Rect_L;
        textPaint.setColor(mContext.getResources().getColor(R.color.color_F562C4_fenshi_pink));
        canvas.drawText(getName() + "  舞庄" + s, currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
        currentX = currentX + getTextWidth(getName() + "  舞庄" + s, textPaint) + textPaint.measureText("  ");

        textPaint.setColor(mContext.getResources().getColor(R.color.color_467DEB_fenshi_light_blue));
        canvas.drawText("伏击:" + s1 /*"舞：60.00  伏：40.00"*/, currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);

        currentX = currentX + getTextWidth("伏击:" + s1, textPaint) + textPaint.measureText("  ");
        textPaint.setColor(mContext.getResources().getColor(R.color.color_7A7A7A_second_important_text_2));
        canvas.drawText("舞:60.00" /*"舞：60.00  伏：40.00"*/, currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);


        currentX = currentX + getTextWidth("舞:60.00", textPaint) + textPaint.measureText("  ");
        textPaint.setColor(mContext.getResources().getColor(R.color.color_7A7A7A_second_important_text_2));
        canvas.drawText("伏:40.00" /*"舞：60.00  伏：40.00"*/, currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);


    }

    @Override
    public void compute(List<CandleData> kLists) {

//        M:=55;N:=21;
//        SAN:=100*(HHV(HIGH,M)-CLOSE)/(HHV(HIGH,M)-LLV(LOW,M)),COLORFFFF00,LINETHICK1;
//        RSV:=(CLOSE-LLV(LOW,N))/(HHV(HIGH,N)-LLV(LOW,N))*100;
//        K:=SMA(RSV,3,1);
//        D:=SMA(K,3,1);
//        J:=3*K-2*D;
//        舞庄:EMA(J,4),COLORFF00FF,LINETHICK1;
//        PHSH:=SMA(SAN,3,1);
//        伏击:MA(PHSH,2),COLORCYAN,LINETHICK1;
//        舞:60,COLORWHITE,LINETHICK1;
//        伏:40,COLORWHITE,LINETHICK1;

        if (kLists == null || kLists.size() == 0/*||kLists.size()<var1*/) {
            return;
        }

        WZ.clear();
        FJ.clear();

        List<Float> highList = new ArrayList<>();
        List<Float> lowList = new ArrayList<>();
        List<Float> SAN = new ArrayList<>();
        List<Float> RSV = new ArrayList<>();
        List<Float> K = new ArrayList<>();
        List<Float> D = new ArrayList<>();
        List<Float> J = new ArrayList<>();
        List<Float> PUSH = new ArrayList<>();
        for (int i = 0;i<kLists.size();i++){
            CandleData candleData = kLists.get(i);
            highList.add((float) candleData.high);
            lowList.add((float) candleData.low);
        }
        List<Float> hhv = HHV(highList, 55);
        List<Float> hhv2 = HHV(highList, 21);
        List<Float> llv = LLV(lowList, 55);
        List<Float> llv2 = LLV(lowList, 21);

        for (int i = 0;i<kLists.size();i++){
            if ((hhv.get(i)-llv.get(i)==0)){
                SAN.add(0.0f);
            }else{
                SAN.add((float) (100*(hhv.get(i)-kLists.get(i).close)/(hhv.get(i)-llv.get(i))));
            }
            if ((hhv2.get(i)-llv2.get(i))==0){
                RSV.add(0.0f);
            }else{
                RSV.add((float) (100*(kLists.get(i).close - llv2.get(i))/(hhv2.get(i)-llv2.get(i))));
            }
        }
        K.addAll(SMA(RSV,3,1));
        D.addAll(SMA(K,3,1));
        for (int i = 0;i<kLists.size();i++){
            J.add(K.get(i)*3-D.get(i)*2);
        }
        WZ.addAll(EMA(J, 4));//舞庄
        PUSH.addAll(SMA(SAN,3,1));
        FJ.addAll(MA(PUSH, 2));//伏击
    }

    @Override
    public double[] getMaxValue(int start, int stop) {
        double maxMin[] = new double[2];
        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;
        for (int i = start;i<=stop;i++){
            float k = WZ.get(i);
            float d = FJ.get(i);
            if (k > max) {
                max = k;
            }
            if (d > max) {
                max = d;
            }
            if (k < min) {
                min = k;
            }
            if (d < min) {
                min = d;
            }
        }
        maxMin[0] = max;
        maxMin[1] = min;

        return maxMin;
    }

    @Override
    public void draw(Context context, Canvas canvas, List<KLineParam> mDisplayKlines, Paint paint, float l, float t, float r, float b, float mScaleX, double max, double min) {
        if (mDisplayKlines.size() == 0) {
            return;
        }
        int firstIndex = mDisplayKlines.get(0).index;
        int lastIndex = mDisplayKlines.get(mDisplayKlines.size() - 1).index;
        List<Float> wzList = WZ.subList(firstIndex, lastIndex + 1);
        List<Float> fjList = FJ.subList(firstIndex, lastIndex + 1);
        float scale = (float) ((b - t) / (max - min));
        for (int i = 0; i < wzList.size(); i++) {
            if (i>0){
                Float aDouble = wzList.get(i - 1);
                if (aDouble!=0){
                    canvas.drawLine(mDisplayKlines.get(i-1).yx,  (b - (aDouble - (float)min)
                            * scale),mDisplayKlines.get(i).yx,  (b - (wzList.get(i) - (float)min)
                            * scale),wzPaint);
                }
            }
            if (i>0){
                Float aDouble = fjList.get(i - 1);
                if (aDouble!=0){
                    canvas.drawLine(mDisplayKlines.get(i-1).yx,  (b - (aDouble - (float)min)
                            * scale),mDisplayKlines.get(i).yx,  (b - (fjList.get(i) - (float)min)
                            * scale),fjPaint);
                }
            }
        }
        pathSd.reset();
        pathSd.moveTo(l, (float) (b - (60 - min) * scale));
        pathSd.lineTo(r, (float) (b - (60 - min) * scale));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(mContext.getResources().getColor(R.color.color_E6E6E6_one_px_divider));
        paint.setStrokeWidth(1);
        paint.setPathEffect(effects);
        canvas.drawPath(pathSd, paint);

        pathCd.reset();
        pathCd.moveTo(l, (float) (b - (40 - min) * scale));
        pathCd.lineTo(r, (float) (b - (40 - min) * scale));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(mContext.getResources().getColor(R.color.color_E6E6E6_one_px_divider));
        paint.setStrokeWidth(1);
        paint.setPathEffect(effects);
        canvas.drawPath(pathCd, paint);
    }
}

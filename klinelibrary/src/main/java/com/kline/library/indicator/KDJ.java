package com.kline.library.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.kline.library.R;
import com.kline.library.bean.CandleData;
import com.kline.library.util.DisplayUtils;
import com.kline.library.util.UIHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * KDJ指标
 *
 * @author user
 */
public class KDJ extends Indicator implements Serializable {

    private static final long serialVersionUID = 1L;

    // 周期
    private int default_k = 9;
    private int default_d = 3;
    private int default_j = 3;

    private List<Float> ks = new ArrayList<>();
    private List<Float> ds = new ArrayList<>();
    private List<Float> js = new ArrayList<>();
    private Paint textPaint;
    private Paint kPaint;
    private Paint dPaint;
    private Paint jPaint;


    public KDJ(Context context) {
        super(context);


        textPaint = new Paint();
        kPaint = new Paint();
        dPaint = new Paint();
        jPaint = new Paint();

        textPaint.setTextSize(textSize);

        kPaint.setStyle(Paint.Style.STROKE);
        kPaint.setColor(context.getResources().getColor(R.color.kline_yellow));
        kPaint.setStrokeWidth(DisplayUtils.DENSITY);

        dPaint.setStyle(Paint.Style.STROKE);
        dPaint.setColor(context.getResources().getColor(R.color.kline_qing));
        dPaint.setStrokeWidth(DisplayUtils.DENSITY);

        jPaint.setStyle(Paint.Style.STROKE);
        jPaint.setColor(context.getResources().getColor(R.color.color_F562C4_fenshi_pink));
        jPaint.setStrokeWidth(DisplayUtils.DENSITY);

    }

    private List<Float> getKs() {
        return ks;
    }

    public void setKs(List<Float> ks) {
        this.ks = ks;
    }

    private List<Float> getDs() {
        return ds;
    }

    public void setDs(List<Float> ds) {
        this.ds = ds;
    }

    public List<Float> getJs() {
        return js;
    }

    public void setJs(List<Float> js) {
        this.js = js;
    }

    /**
     * 计算KDJ
     *
     */
    public void compute(List<CandleData> values) {

         if (values == null || values.size() == 0) {
            return;
        }
        ks.clear();
        ds.clear();
        js.clear();

        float k = 50.0f;
        float d = 50.0f;
        if (values.size() > 0){
            for (int i = 0; i < values.size(); i++) {
                CandleData value = values.get(i);

                // k天的KlineValue
                CandleData[] kvalues = getSubKlineValue(values, i, default_k);
                float high = getHigh(kvalues); // 最高价
                float low = getLow(kvalues); // 最低价

                if (high - low == 0.0) {
                    ks.add(100.0f);
                    ds.add(100.0f);
                    js.add(100.0f);
                } else {
                    float rsv = (float) ((value.close - low) / (high - low) * 100.0f);
                    k = k * 2 / 3 + rsv * 1 / 3;
                    d = d * 2 / 3 + k * 1 / 3;
                    float j = 3 * k - 2 * d;

                    ks.add(k);
                    ds.add(d);
                    js.add(j);
                }
            }
        }
    }

    public double[] getMaxValue(int start,int stop){
        double maxMin[] = new double[2];
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        for (int i = start;i<=stop;i++){
            float k = ks.get(i);
            float d = ds.get(i);
            float j = js.get(i);
            if (k > max) {
                max = k;
            }
            if (d > max) {
                max = d;
            }
            if (j > max) {
                max = j;
            }
            if (k < min) {
                min = k;
            }
            if (d < min) {
                min = d;
            }
            if (j < min) {
                min = j;
            }
        }
        maxMin[0] = max;
        maxMin[1] = min;

    return maxMin;
    }

    @Override
    public void draw(Context context, Canvas canvas, List<KLineParam> CandleDataList, Paint mPaint, float rect2_L, float rect2_T, float rect2_R, float rect2_B, float mScaleX, double mMainMaxValue, double mMainMinValue) {
        if (CandleDataList.size() == 0) {
            return;
        }


        int firstIndex = CandleDataList.get(0).index;
        int lastIndex = CandleDataList.get(CandleDataList.size() - 1).index;

        List<Float> ks = getKs().subList(firstIndex, lastIndex + 1);
        List<Float> ds = getDs().subList(firstIndex, lastIndex + 1);
        List<Float> js = getJs().subList(firstIndex, lastIndex + 1);


        float scale = (float) ((rect2_B - rect2_T) / (mMainMaxValue - mMainMinValue));

        Path path = new Path();
        path.moveTo(CandleDataList.get(0).yx, (float) (rect2_B -(ks.get(0) - mMainMinValue)* scale));
        for (int i = 1; i < ks.size(); i++) {
            path.lineTo(CandleDataList.get(i).yx, (float) (rect2_B - (ks.get(i) - mMainMinValue)
                                            * scale));
        }
        canvas.drawPath(path, kPaint);

        // 画d线
        path.reset();
        path.moveTo(CandleDataList.get(0).yx, (float) (rect2_B - (ds.get(0) - mMainMinValue) * scale));
        for (int i = 1; i < ds.size(); i++) {
            path.lineTo(CandleDataList.get(i).yx, (float) (rect2_B - (ds.get(i) - mMainMinValue)
                                            * scale));
        }
        canvas.drawPath(path, dPaint);

        // 画j线
        path.reset();
        path.moveTo(CandleDataList.get(0).yx, (float) (rect2_B - (js.get(0) - mMainMinValue)* scale));
        for (int i = 1; i < js.size(); i++) {
            path.lineTo(CandleDataList.get(i).yx, (float) (rect2_B - (js.get(i) - mMainMinValue) * scale));
        }
        canvas.drawPath(path, jPaint);
    }

    /**
     * 获取n日的klinevalue
     *
     */
    private CandleData[] getSubKlineValue(List<CandleData> values, int index, int n) {
        int size = n;
        int firstIndex = index - n + 1;
        if (firstIndex < 0) {
            size += firstIndex;
        }
        CandleData[] rs = new CandleData[size];
        for (int i = 0; i < rs.length; i++) {
            rs[i] = values.get(index - size + 1 + i);
        }
        return rs;
    }

    /**
     * 获取最高价
     *
     */
    private float getHigh(CandleData[] values) {
        double high =  values[0].high;
        for (CandleData value : values) {
            if (value.high > high) {
                high = value.high;
            }
        }
        return (float) high;
    }

    /**
     * 获取最低价
     *
     */
    private float getLow(CandleData[] values) {
        double low =  values[0].low;
        for (CandleData value : values) {
            if (value.low < low) {
                low = value.low;
            }
        }
        return (float) low;
    }


    @Override
    public String getName() {
        return "KDJ";
    }

    @Override
    public int getposition() {
        return 3;
    }


    @Override
    public int getIndex() {
        return INDICATOR_INDEX_KDJ;
    }

    @Override
    public void drawText(Canvas canvas, float Rect_L, float Rect_T, int selectedIndex, Paint mPaint) {
        float currentX = Rect_L;
        textPaint.setColor(mContext.getResources().getColor(R.color.kline_yellow));
        canvas.drawText("KDJ:K9: " + UIHelper.formatVolumn(ks.get(selectedIndex)), currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
        currentX = currentX + getTextWidth("KDJ:K9: " + UIHelper.formatVolumn(ks.get(selectedIndex)), textPaint) + + textPaint.measureText("  ");
        textPaint.setColor(mContext.getResources().getColor(R.color.kline_qing));
        canvas.drawText("D: " + UIHelper.formatVolumn(ds.get(selectedIndex)), currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
        currentX = currentX + getTextWidth("D: " + UIHelper.formatVolumn(ds.get(selectedIndex)), textPaint) + + textPaint.measureText("  ");
        textPaint.setColor(mContext.getResources().getColor(R.color.color_F562C4_fenshi_pink));
        canvas.drawText("J: " + UIHelper.formatVolumn(js.get(selectedIndex)), currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
    }
}

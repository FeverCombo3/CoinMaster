package com.kline.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.kline.library.R;
import com.kline.library.bean.CandleData;
import com.kline.library.indicator.Indicator;
import com.kline.library.indicator.KLineParam;
import com.kline.library.util.DisplayUtils;
import com.kline.library.util.NumberFormatUtil;
import com.kline.library.util.UIHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yjq
 * 2018/5/20.
 * K线图
 */

public class KlineView extends ScrollScaleView {

    public enum KlineType {
        KLINE_DOUBLE, //主图加一个附图模式
        KLINE_TRIPLE; //主图加两个附图模式
    }

    private float RECT_LATITUDE_NUM = 3;

    private float RECT1_LONGITUDE_NUM = 2;
    private float RECT1_LATITUDE_NUM = 3;

    private float RECT2_LONGITUDE_NUM = 0;
    private float RECT2_LATITUDE_NUM = 1;

    private float RECT_MIDDLE = DisplayUtils.dipToPixel(69 / 3);
    private float RECT_T_PADDING = DisplayUtils.dipToPixel(55 / 3);//上边宽度
    private float RECT_L_PADDING = DisplayUtils.dipToPixel(6);// 左边宽度
    private float RECT_R_PADDING = DisplayUtils.dipToPixel(6);// 右边宽度
    private float RECT_B_PADDING = DisplayUtils.dipToPixel(1);// 底部宽度

    private float RECT1contentPADDINGT = DisplayUtils.dipToPixel(18 / 3);
    private float RECT1contentPADDINGB = DisplayUtils.dipToPixel(16 / 3);

    private float RECT2contentPADDINGT = DisplayUtils.dipToPixel(0 / 3);
    private float RECT2contentPADDINGB = DisplayUtils.dipToPixel(0 / 3);

    private Paint mPaint;
    private Paint mCrossPaint;
    private Paint mCrossTextPaint;
    private Paint mTimeTextPaint;
    private Paint mTextPaint1;
    private Context mContext;

    private float rect1_width;

    //主图坐标范围
    private float rect1_L;
    private float rect1_R;
    private float rect1_T;
    private float rect1_B;

    //副图1坐标范围
    private float rect2_L;
    private float rect2_R;
    private float rect2_T;
    private float rect2_B;

    //副图2坐标范围
    private float rect3_L;
    private float rect3_R;
    private float rect3_T;
    private float rect3_B;

    //主图绘制内容范围
    private float rect1_T_content;
    private float rect1_B_content;

    //副图1绘制内容范围
    private float rect2_T_content;
    private float rect2_B_content;

    //副图2绘制内容范围
    private float rect3_T_content;
    private float rect3_B_content;

    private int borderWidth;
    private float RECT_1_WEIGHT = 3;
    private float RECT_2_WEIGHT = 1;
    private float RECT_3_WEIGHT = 1;

    //一个点的宽度
    protected int mPointWidth;
    private int mItemCount;
    private List<KLineParam> mDisplayKlines = new ArrayList<>();
    private List<Indicator> indicatorList;
    protected double mMainMinValue;
    protected double mMainMaxValue;
    protected double mSecondMinValue;
    protected double mSecondMaxValue;
    protected double mThirdMinValue;
    protected double mThirdMaxValue;
    float v_left; //左侧偏移量
    float v_right; //右侧偏移量
    private Paint paint;

    private Paint RECTPaint;
    private Paint RECTPaintB;
    private boolean isType2 = false;
    private KlineType klineType = KlineType.KLINE_TRIPLE;


    public KlineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public KlineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public KlineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.KlineView);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.KlineView_first_weight) {
                RECT_1_WEIGHT = typedArray.getFloat(attr, 6);
            } else if (attr == R.styleable.KlineView_second_weight) {
                RECT_2_WEIGHT = typedArray.getFloat(attr, 2);
            } else if (attr == R.styleable.KlineView_third_weight) {
                RECT_3_WEIGHT = typedArray.getFloat(attr, 2);
            } else if (attr == R.styleable.KlineView_border_line_width) {
                borderWidth = typedArray.getInt(attr, DisplayUtils.dipToPixel(1));
            } else if (attr == R.styleable.KlineView_padding_right) {
                RECT_R_PADDING = typedArray.getFloat(attr, RECT_R_PADDING);
            } else if (attr == R.styleable.KlineView_rect1_content_padding_t) {
                RECT1contentPADDINGT = typedArray.getDimension(attr, RECT1contentPADDINGT);
            } else if (attr == R.styleable.KlineView_rect1_content_padding_b) {
                RECT1contentPADDINGB = typedArray.getDimension(attr, RECT1contentPADDINGB);
            } else if (attr == R.styleable.KlineView_rect2_content_padding_b) {
                RECT2contentPADDINGB = typedArray.getDimension(attr, RECT2contentPADDINGB);
            } else if (attr == R.styleable.KlineView_rect2_content_padding_t) {
                RECT2contentPADDINGT = typedArray.getDimension(attr, RECT2contentPADDINGT);
            } else if (attr == R.styleable.KlineView_rect_t_padding) {
                RECT_T_PADDING = typedArray.getDimension(attr, RECT_T_PADDING);
                isType2 = true;
            }
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int result = 200;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case View.MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case View.MeasureSpec.AT_MOST:
                result = Math.min(result, specSize);
                break;
            case View.MeasureSpec.EXACTLY:
                result = specSize;
                break;
            default:
                break;
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 200;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case View.MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case View.MeasureSpec.AT_MOST:
                result = Math.min(result, specSize);
                break;
            case View.MeasureSpec.EXACTLY:
                result = specSize;
                break;
            default:
                break;
        }
        return result;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        calculateXY();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mCrossPaint = new Paint();
        mCrossPaint.setAntiAlias(true);
        mCrossTextPaint = new Paint();
        mCrossTextPaint.setTextSize(DisplayUtils.sp2px(11));
        mCrossTextPaint.setColor(mContext.getResources().getColor(R.color.kline_blue));

        mTextPaint1 = new Paint();
        paint = new Paint();
        mPointWidth = DisplayUtils.dipToPixel(10);

        mCrossPaint.setTextSize(DisplayUtils.sp2px(11));
        mCrossPaint.setStyle(Paint.Style.STROKE);
        mCrossPaint.setColor(mContext.getResources().getColor(R.color.color_white));

        mTimeTextPaint = new Paint();
        mTimeTextPaint.setTextSize(DisplayUtils.sp2px(11));
        mTimeTextPaint.setStyle(Paint.Style.STROKE);
        mTimeTextPaint.setColor(mContext.getResources().getColor(R.color.color_white));

        mTextPaint1.setTextSize(DisplayUtils.sp2px(11));
        mTextPaint1.setStyle(Paint.Style.STROKE);
        mTextPaint1.setColor(mContext.getResources().getColor(R.color.color_white));

        RECTPaint = new Paint();
        RECTPaint.setStyle(Paint.Style.FILL);


        RECTPaintB = new Paint();
        RECTPaintB.setStyle(Paint.Style.STROKE);
    }

    private void calculateXY(){
        int viewRight = getRight();
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        Log.i("YJQ","viewHeight: " + viewHeight);

        klineType = KlineType.KLINE_TRIPLE;

        if(klineType == KlineType.KLINE_DOUBLE){
            //TYPE_DOUBLE
            rect1_L = RECT_L_PADDING;
            rect1_R = viewRight - RECT_R_PADDING;

            rect1_T = RECT_T_PADDING;
            rect1_B = RECT_T_PADDING + (viewHeight - RECT_T_PADDING - RECT_B_PADDING - RECT_MIDDLE) * RECT_1_WEIGHT / (RECT_1_WEIGHT + RECT_2_WEIGHT);

            rect1_T_content = rect1_T + RECT1contentPADDINGT;
            rect1_B_content = rect1_B - RECT1contentPADDINGB;

            rect2_L = RECT_L_PADDING;
            rect2_R = viewRight - RECT_R_PADDING;

            rect2_T = rect1_B + RECT_MIDDLE;
            rect2_B = rect2_T + (viewHeight - RECT_T_PADDING - RECT_B_PADDING - RECT_MIDDLE) * RECT_2_WEIGHT / (RECT_1_WEIGHT + RECT_2_WEIGHT);

            rect2_T_content = rect2_T + RECT2contentPADDINGT;
            rect2_B_content = rect2_B - RECT2contentPADDINGB;
        }else if(klineType == KlineType.KLINE_TRIPLE){
            //TYPE_TRIPLE
            rect1_L = RECT_L_PADDING;
            rect1_R = viewRight - RECT_R_PADDING;

            rect1_T = RECT_T_PADDING;
            rect1_B = RECT_T_PADDING + (viewHeight - RECT_T_PADDING - RECT_B_PADDING - RECT_MIDDLE * 2) * RECT_1_WEIGHT / (RECT_1_WEIGHT + RECT_2_WEIGHT + RECT_3_WEIGHT);

            rect1_T_content = rect1_T + RECT1contentPADDINGT;
            rect1_B_content = rect1_B - RECT1contentPADDINGB;

            rect2_L = RECT_L_PADDING;
            rect2_R = viewRight - RECT_R_PADDING;

            rect2_T = rect1_B + RECT_MIDDLE;
            rect2_B = rect2_T + (viewHeight - RECT_T_PADDING - RECT_B_PADDING - RECT_MIDDLE * 2) * RECT_2_WEIGHT / (RECT_1_WEIGHT + RECT_2_WEIGHT + RECT_3_WEIGHT);

            rect2_T_content = rect2_T + RECT2contentPADDINGT;
            rect2_B_content = rect2_B - RECT2contentPADDINGB;

            rect3_L = RECT_L_PADDING;
            rect3_R = viewRight - RECT_R_PADDING;

            rect3_T = rect2_B + RECT_MIDDLE;
            rect3_B = viewHeight - RECT_B_PADDING;

            rect3_T_content = rect3_T + RECT2contentPADDINGT;
            rect3_B_content = rect3_B - RECT2contentPADDINGB;
        }


        rect1_width = viewWidth - RECT_L_PADDING - RECT_R_PADDING;
    }


    public void setData(List<CandleData> kLists, List<Indicator> indicatorList, boolean refresh) {

        this.indicatorList = new ArrayList<>(indicatorList);
        this.kLists = kLists;
        mItemCount = kLists.size();
        if (refresh) {
            mScrollX = 0;
            mScroller.forceFinished(true);
        }else {
            calculateXY();
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGird(canvas);//画表格
        if (mItemCount != 0) {
            try {
                calculateValue();//计算
                drawK(canvas);//画K线指标
                drawText(canvas);//画文字
                drawCrossLine(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void drawCrossLine(Canvas canvas) {
        if (isShowCrossLine && mDisplayKlines.size() != 0) {
            int index = mDisplayKlines.get(0).index;
            KLineParam kLineParam = mDisplayKlines.get(mSelectedIndex - index);
            CandleData candelData = kLineParam.candelData;
            if (dataListener != null) {
                dataListener.setdata(candelData);
            }
            mPaint.setColor(mContext.getResources().getColor(R.color.color_white));
            mPaint.setStrokeWidth(DisplayUtils.DENSITY);
            RECTPaintB.setStrokeWidth(1);
            RECTPaintB.setColor(mContext.getResources().getColor(R.color.color_white));
            RECTPaint.setColor(mContext.getResources().getColor(R.color.dark_bg));

            double scale1 = (rect1_B_content - rect1_T_content) / (mMainMaxValue - mMainMinValue);

            float yx = kLineParam.yx;

            Paint ppp = new Paint(Paint.ANTI_ALIAS_FLAG);
            ppp.setColor(mContext.getResources().getColor(R.color.kline_blue_darker_alpha));
            ppp.setStyle(Paint.Style.FILL);

            //竖线
            canvas.drawRect(kLineParam.l + 2,rect1_T,kLineParam.r - 2,getMeasuredHeight() - RECT_B_PADDING,ppp);

            //主图横线
            float close = (float) (rect1_B_content - (((float) candelData.close - mMainMinValue) * scale1));//收盘价坐标
            canvas.drawLine(rect1_L, close, rect1_R, close, mPaint);

            mCrossTextPaint.setColor(Color.WHITE);
            //十字线的价格
            String sClose = UIHelper.formatPrice((float) candelData.close);
            RectF rectF1 = new RectF();//矩形价格
            float rectFWidth = /*价格的宽度+边距*/ mCrossTextPaint.measureText(sClose) + DisplayUtils.dipToPixel(15 / 3) * 2;

            if (close < (rect1_B + rect1_T) / 2) {
                rectF1.set(rect1_L, close, rect1_L + rectFWidth, close + DisplayUtils.dipToPixel(52 / 3));
            } else {
                rectF1.set(rect1_L, close - DisplayUtils.dipToPixel(52 / 3), rect1_L + rectFWidth, close);
            }
            canvas.drawRect(rectF1, RECTPaint);
            canvas.drawRect(rectF1, RECTPaintB);
            canvas.drawText(sClose, rectF1.centerX() - mCrossTextPaint.measureText(sClose) / 2, rectF1.centerY() + getTextHeight(sClose, mCrossTextPaint) / 2, mCrossTextPaint);

            mCrossTextPaint.setColor(mContext.getResources().getColor(R.color.kline_blue));
            //画数据方框
            RectF rectF2 = new RectF();
            float rectF2FWidth = DisplayUtils.dipToPixel(120);
            float padding = DisplayUtils.dipToPixel(4);
            float rectF2FHeight = 6 * getTextHeight("开",mCrossTextPaint) + 7 * padding;
            float rectF2_L = 0;
            float rectF2_R = 0;
            float rectF2_T = 0;
            float rectF2_B = 0;

            if(yx < rect1_width / 2){
                rectF2_L = rect1_R - rectF2FWidth;
                rectF2_R = rect1_R;
                rectF2_T = rect1_T;
                rectF2_B = rect1_T + rectF2FHeight;
            }else {
                rectF2_L = rect1_L;
                rectF2_R = rect1_L + rectF2FWidth;
                rectF2_T = rect1_T;
                rectF2_B = rect1_T + rectF2FHeight;
            }
            RECTPaintB.setStrokeWidth(2);
            RECTPaintB.setColor(mContext.getResources().getColor(R.color.kline_blue_choose));
            RECTPaint.setColor(Color.parseColor("#cc131F2F"));
            rectF2.set(rectF2_L,rectF2_T,rectF2_R,rectF2_B);
            canvas.drawRect(rectF2, RECTPaint);
            canvas.drawRect(rectF2, RECTPaintB);


            float tx = getTextHeight("开",mCrossTextPaint);
            float hR = padding + rect1_T + tx;
            canvas.drawText("开",rectF2_L + padding,hR,mCrossTextPaint);
            canvas.drawText(NumberFormatUtil.getFourDecimal(candelData.open),rectF2_R - mCrossTextPaint.measureText(NumberFormatUtil.getFourDecimal(candelData.open)) - padding,hR,mCrossTextPaint);

            hR = hR + tx + padding;
            canvas.drawText("高",rectF2_L + padding,hR,mCrossTextPaint);
            canvas.drawText(NumberFormatUtil.getFourDecimal(candelData.high),rectF2_R - mCrossTextPaint.measureText(NumberFormatUtil.getFourDecimal(candelData.high)) - padding,hR,mCrossTextPaint);

            hR = hR + tx + padding;
            canvas.drawText("低",rectF2_L + padding,hR,mCrossTextPaint);
            canvas.drawText(NumberFormatUtil.getFourDecimal(candelData.low),rectF2_R - mCrossTextPaint.measureText(NumberFormatUtil.getFourDecimal(candelData.low)) - padding,hR,mCrossTextPaint);

            hR = hR + tx + padding;
            canvas.drawText("收",rectF2_L + padding,hR,mCrossTextPaint);
            canvas.drawText(NumberFormatUtil.getFourDecimal(candelData.close),rectF2_R - mCrossTextPaint.measureText(NumberFormatUtil.getFourDecimal(candelData.close)) - padding,hR,mCrossTextPaint);

            hR = hR + tx + padding;
            canvas.drawText("成交量",rectF2_L + padding,hR,mCrossTextPaint);
            canvas.drawText(NumberFormatUtil.getFourDecimal(candelData.vol),rectF2_R - mCrossTextPaint.measureText(NumberFormatUtil.getFourDecimal(candelData.vol)) - padding,hR,mCrossTextPaint);

            hR = hR + tx + padding;
            canvas.drawText("日期",rectF2_L + padding,hR,mCrossTextPaint);
            canvas.drawText(NumberFormatUtil.transDate(candelData.id),rectF2_R - mCrossTextPaint.measureText(NumberFormatUtil.transDate(candelData.id)) - padding,hR,mCrossTextPaint);
        }
    }

    @Override
    public int getTextHeight(String s, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(s, 0, s.length(), rect);
        return rect.height();
    }

    private void drawText(Canvas canvas) {
        int height = getTextHeight(String.valueOf(mMainMaxValue), mTextPaint1);
        if (mItemCount != 0) {
            mTextPaint1.setColor(mContext.getResources().getColor(R.color.kline_blue_choose));
            String value1 = UIHelper.formatMainValue(mMainMaxValue);
            canvas.drawText(value1, rect1_R - mTextPaint1.measureText(value1), rect1_T_content + height * 1.1f, mTextPaint1);
            String value2 = UIHelper.formatMainValue(mMainMinValue);
            canvas.drawText(value2, rect1_R - mTextPaint1.measureText(value2), rect1_B_content - height * 0.1f, mTextPaint1);
            String value3 = UIHelper.formatMainValue(mMainMaxValue - (mMainMaxValue - mMainMinValue) / 3);
            canvas.drawText(value3, rect1_R - mTextPaint1.measureText(value3), rect1_B - (rect1_B - rect1_T ) / 3 * 2 + height + DisplayUtils.dipToPixel(5), mTextPaint1);
            String value4 = UIHelper.formatMainValue(mMainMinValue + (mMainMaxValue - mMainMinValue) / 3);
            canvas.drawText(value4, rect1_R - mTextPaint1.measureText(value4),rect1_B - (rect1_B - rect1_T ) / 3 + height , mTextPaint1);
        }
    }


    private String dealTime(String s) {
        String date;
        if (s.length() == 10) {
            date = s.substring(2, s.length());
            StringBuilder builder = new StringBuilder(date);
            builder.insert(2, "-").insert(5, " ").insert(8, ":");
            date = String.valueOf(builder);
        } else {
            date = s;
            StringBuilder builder = new StringBuilder(date);
            builder.insert(4, "-").insert(7, "-");
            date = String.valueOf(builder);
        }
        return date;
    }

    private void drawK(Canvas canvas) {
        for (Indicator indicator : indicatorList) {
            switch (indicator.getposition()) {
                case 0:
                case 1:
                    indicator.draw(mContext, canvas, mDisplayKlines, mPaint, rect1_L, rect1_T_content/*+DisplayUtils.dipToPixel(5)*/, rect1_R, rect1_B_content/*-DisplayUtils.dipToPixel(5)*/, mScaleX, mMainMaxValue, mMainMinValue);
                    indicator.drawText(canvas,rect1_L,RECT_T_PADDING / 2,mSelectedIndex,mPaint);
                    break;
                case 2:
                    indicator.draw(mContext, canvas, mDisplayKlines, mPaint, rect2_L, rect2_T_content, rect2_R, rect2_B_content, mScaleX, mSecondMaxValue, mSecondMinValue);
                    indicator.drawText(canvas,rect2_L,rect1_B + RECT_MIDDLE / 2,mSelectedIndex,mPaint);
                    break;
                case 3:
                    indicator.draw(mContext, canvas, mDisplayKlines, mPaint, rect3_L, rect3_T_content, rect3_R, rect3_B_content, mScaleX, mThirdMaxValue, mThirdMinValue);
                    indicator.drawText(canvas,rect3_L,rect2_B + RECT_MIDDLE / 2,mSelectedIndex,mPaint);
                    break;
                default:
                    break;
            }
        }
    }

    private void drawGird(Canvas canvas) {

        mPaint.reset();
        mPaint.setColor(mContext.getResources().getColor(R.color.kline_blue_darker));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(borderWidth);

        // 画上矩形
        canvas.drawLine(rect1_L, rect1_T, rect1_R, rect1_T, mPaint);
        canvas.drawLine(rect1_L, rect1_B, rect1_R, rect1_B, mPaint);

        // 画上矩形横线
        float height1 = (rect1_B_content - rect1_T_content) / (RECT1_LONGITUDE_NUM + 1);
        for (int i = 1; i < (RECT1_LONGITUDE_NUM + 1); i++) {

            Path path = new Path();
            path.moveTo(rect1_L, i * height1 + rect1_T_content);
            path.lineTo(rect1_R, i * height1 + rect1_T_content);
           // PathEffect effects = new DashPathEffect(new float[]{15, 5, 15, 5}, 0);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(mContext.getResources().getColor(R.color.kline_blue_darker));
            paint.setStrokeWidth(1);
           // paint.setPathEffect(effects);
            canvas.drawPath(path, paint);
        }

        // 画中矩形
//        canvas.drawLine(rect2_L, rect2_T, rect2_R, rect2_T, mPaint);
        canvas.drawLine(rect1_L, rect2_B, rect1_R, rect2_B, mPaint);

        // 画中矩形中的横线
        float height2 = (rect1_B_content - rect1_T_content) / (RECT2_LONGITUDE_NUM + 1);

        for (int i = 1; i < (RECT2_LONGITUDE_NUM + 1); i++) {

            Path path = new Path();
            path.moveTo(rect1_L, rect1_T_content + height2 * i);
            path.lineTo(rect1_R, rect1_T_content + height2 * i);
            canvas.drawPath(path, paint);
        }

        if(klineType == KlineType.KLINE_TRIPLE){
//            canvas.drawLine(rect3_L, rect3_T, rect3_R, rect3_T, mPaint);
            canvas.drawLine(rect3_L, rect3_B, rect3_R, rect3_B, mPaint);
        }


        //画上矩形的竖线
        float weight = (rect1_R - rect1_L) / (RECT1_LATITUDE_NUM + 1);
        for (int i = 1; i < (RECT1_LATITUDE_NUM + 1); i++) {
            canvas.drawLine(rect1_L + i * weight, 0, rect1_L + i * weight, getHeight(), mPaint);
        }
    }

    private void calculateValue() {
        if (!isShowCrossLine) {
            mSelectedIndex = mItemCount - 1;
        }
        checkAndFixScrollX();
        mMainMinValue = Double.MAX_VALUE;
        mMainMaxValue = Double.MIN_VALUE;

        mSecondMinValue = Double.MAX_VALUE;
        mSecondMaxValue = Double.MIN_VALUE;

        mThirdMinValue = Double.MAX_VALUE;
        mThirdMaxValue = Double.MIN_VALUE;

        mDisplayKlines.clear();
        if (mItemCount != 0) {
            if (!isSinglePointer && (kLists.size() * mPointWidth + (kLists.size() - 1) * KLINE_SPEC) > rect1_width) {
                float focusX = mScaleDetector.getFocusX();
                if (mScaleX >= 1) {
                    v_left = (rect1_R - focusX) * (mScaleX - 1);
                    v_right = (focusX - rect1_L) * (mScaleX - 1);
                } else {
                    v_left = (rect1_R - focusX) * (mScaleX - 1) * 3;
                    v_right = (focusX - rect1_L) * (mScaleX - 1) * 3;
                }
            }
        }

        float startX = mScrollX - rect1_width + v_right;
        float stopx = mScrollX - v_left;

        int mStartIndex = (int) (startX / mPointWidth + mItemCount - 1);
        int mStopIndex = (int) stopx / mPointWidth + mItemCount - 1;

        if (mStartIndex < 0) {
            mStartIndex = 0;
        }
        if (mStopIndex > mItemCount - 1) {
            mStopIndex = mItemCount - 1;
        }

        if (mItemCount != 0 && kLists != null && kLists.size() > 0) {
            if ((kLists.size() * mPointWidth + (kLists.size() - 1) * KLINE_SPEC) < rect1_width) {
                for (int i = mStartIndex; i <= mStopIndex; i++) {
                    KLineParam kLineParam = new KLineParam();
                    CandleData hqItemData = kLists.get(i);
                    kLineParam.index = i;
                    kLineParam.yx = (i) * (mPointWidth + KLINE_SPEC) + rect1_L + mPointWidth / 2;
                    kLineParam.r = kLineParam.yx + mPointWidth / 2;
                    kLineParam.l = kLineParam.yx - mPointWidth / 2;
                    kLineParam.candelData = hqItemData;
                    mDisplayKlines.add(kLineParam);
                }
            } else {
                float v2 = (rect1_width - (mStopIndex - mStartIndex) * KLINE_SPEC) / (mStopIndex - mStartIndex + 1);
                for (int i = mStartIndex; i <= mStopIndex; i++) {
                    KLineParam kLineParam = new KLineParam();
                    CandleData hqItemData = kLists.get(i);
                    int i1 = i - mStartIndex;
                    kLineParam.index = i;
                    kLineParam.yx = (i1) * (v2 + KLINE_SPEC) + rect1_L + v2 / 2;
                    kLineParam.r = kLineParam.yx + v2 / 2;
                    kLineParam.l = kLineParam.yx - v2 / 2;
                    kLineParam.candelData = hqItemData;
                    mDisplayKlines.add(kLineParam);
                }
            }
            //计算主图的最大值最小值
            if (indicatorList.size() != 0) {
                for (Indicator indicator : indicatorList) {
                    if (indicator.getposition() == 0 || indicator.getposition() == 1) {
                        double[] maxValue = indicator.getMaxValue(mStartIndex, mStopIndex);
                        mMainMaxValue = Math.max(mMainMaxValue, maxValue[0]);
                        mMainMinValue = Math.min(mMainMinValue, maxValue[1]);
                    } else if (indicator.getposition() == 2) {
                        double[] maxValue = indicator.getMaxValue(mStartIndex, mStopIndex);
                        mSecondMaxValue = Math.max(mSecondMaxValue, maxValue[0]);
                        mSecondMinValue = Math.min(mSecondMinValue, maxValue[1]);
                    } else if (indicator.getposition() == 3) {
                        double[] maxValue = indicator.getMaxValue(mStartIndex, mStopIndex);
                        mThirdMaxValue = Math.max(mThirdMaxValue, maxValue[0]);
                        mThirdMinValue = Math.min(mThirdMinValue, maxValue[1]);
                    }
                }
                double abs = Math.abs(mMainMaxValue - mMainMinValue);
                if (abs < 0.01) {
                    mMainMaxValue = mMainMaxValue + 0.1f;
                    mMainMinValue = mMainMinValue - 0.1f;
                }
            }
        }
    }

    private void calculateSelectedX(float x) {
        for (int i = 0; i < mDisplayKlines.size(); i++) {
            KLineParam kLineParam = mDisplayKlines.get(i);
            float yx = kLineParam.yx;
            float l = kLineParam.l;
            float r = kLineParam.r;
            float v = (r - l) / 2 + KLINE_SPEC / 2;
            if (Math.abs(x - yx) < v) {
                mSelectedIndex = kLineParam.index;
                break;
            }
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        isShowCrossLine = !isShowCrossLine;
        touchY = e.getY();
        touchX = e.getX();
        calculateSelectedX(e.getX());
        if (isShowCrossLine) {
            if (!mAnimator.isRunning()) {
                mAnimator.start();
            }
        } else {
            if (mAnimator.isRunning()) {
                mAnimator.cancel();
            }
            if (dataListener != null) {
                dataListener.cancle();
            }
        }
        invalidate();
        return true;
    }

    float touchY;
    float touchX;

    @Override
    public void onLongPress(MotionEvent e) {

        if (isShowCrossLine) {
            if (mAnimator.isRunning()) {
                mAnimator.cancel();
            }
        } else {
            isShowCrossLine = true;
        }
        touchY = e.getY();
        touchX = e.getX();
        calculateSelectedX(e.getX());
        isCancle = false;
        invalidate();

    }

    @Override
    public void onLeftSide() {
        if (!isShowCrossLine) {
            if (leftOrRightListener != null && (kLists.size() * mPointWidth + (kLists.size() - 1) * KLINE_SPEC) > rect1_width) {
                leftOrRightListener.left();
            }
        }
    }

    @Override
    public float getMaxScrollX() {
        return 0 + v_left;
    }

    @Override
    public void onRightSide() {
        if (!isShowCrossLine) {
            if (leftOrRightListener != null && (kLists.size() * mPointWidth + (kLists.size() - 1) * KLINE_SPEC) > rect1_width) {
                leftOrRightListener.right();
            }
        }

    }

    @Override
    public float getMinScrollX() {
        if ((kLists.size() * mPointWidth + (kLists.size() - 1) * KLINE_SPEC) <= rect1_width) {
            return 0;
        } else {
            return (int) (rect1_R - mPointWidth * mItemCount - v_right);
        }
    }


    public interface LeftOrRightListener {
        void left();

        void right();
    }

    protected void checkAndFixScrollX() {
        float minScrollX = getMinScrollX();
        if (mRound != 0) {
            if (mScrollX < minScrollX) {
                mScrollX = minScrollX;
                onLeftSide();
                mScroller.forceFinished(true);
            } else if (mScrollX > getMaxScrollX()) {
                mScrollX = getMaxScrollX();
                onRightSide();
                mScroller.forceFinished(true);
            }
        }
    }

    public boolean getRefreshState() {
        return mScrollX == getMaxScrollX() && !isShowCrossLine;
    }

    private LeftOrRightListener leftOrRightListener;

    public void setLeftOrRightListener(LeftOrRightListener leftOrRightListener) {
        this.leftOrRightListener = leftOrRightListener;
    }
}

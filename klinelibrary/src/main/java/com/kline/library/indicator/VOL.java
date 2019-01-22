package com.kline.library.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.kline.library.R;
import com.kline.library.bean.CandleData;
import com.kline.library.util.DisplayUtils;
import com.kline.library.util.UIHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 成交量
 * 
 * @author User
 *
 */
public class VOL extends Indicator implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CandleData> kLists;

	private List<Float> ma5;
	private List<Float> ma10;
	private List<Float> ma20;

	private Paint mTextPaint = new Paint();
	private Paint ma5Paint = new Paint();
	private Paint ma10Paint = new Paint();
	private Paint ma20Paint = new Paint();
	private Paint maRectPaint = new Paint();

	public List<Float> getMa10() {
		return ma10;
	}

	public List<Float> getMa20() {
		return ma20;
	}

	public List<Float> getMa5() {
		return ma5;
	}

	public VOL(Context context) {
		super(context);
		mTextPaint = new Paint();
		ma5Paint = new Paint();

		ma10Paint = new Paint();
		ma20Paint = new Paint();
		maRectPaint = new Paint();

		mTextPaint.setTextSize(textSize);


		ma5Paint.setStyle(Paint.Style.STROKE);
		ma5Paint.setColor(context.getResources().getColor(R.color.color_4077E6_fenshi_now));
		ma5Paint.setStrokeWidth(DisplayUtils.DENSITY);

		ma10Paint.setStyle(Paint.Style.STROKE);
		ma10Paint.setColor(context.getResources().getColor(R.color.color_FF9320_orange));
		ma10Paint.setStrokeWidth(DisplayUtils.DENSITY);

		ma20Paint.setStyle(Paint.Style.STROKE);
		ma20Paint.setColor(context.getResources().getColor(R.color.color_F562C4_fenshi_pink));
		ma20Paint.setStrokeWidth(DisplayUtils.DENSITY);

		maRectPaint.setStyle(Paint.Style.FILL);
		maRectPaint.setStrokeWidth(DisplayUtils.DENSITY);
	}

	@Override
	public String getName() {
		return "VOL";
	}

	@Override
	public int getposition() {
		return 2;
	}

	@Override
	public void compute(List<CandleData> values) {

		this.kLists = values;

		if (values == null || values.size() == 0) {
			return;
		}

		ma5 = new ArrayList<>();
		ma10 = new ArrayList<>();
		ma20 = new ArrayList<>();

		for (int i = 0; i < values.size(); i++) {

			// 5日均线
			float sum5 = 0.0f;
			if (i < 4) {
				ma5.add(Float.NaN);
			} else {
				for (int m = i - 4; m <= i; m++) {
					sum5 += values.get(m).vol;
				}
				ma5.add(sum5 / 5);
			}

			// 10日均线
			float sum10 = 0.0f;
			if (i < 9) {
				ma10.add(Float.NaN);
			} else {
				for (int m = i - 9; m <= i; m++) {
					sum10 += values.get(m).vol;
				}
				ma10.add(sum10 / 10);
			}

			// 20日均线
			float sum20 = 0.0f;
			if (i < 19) {
				ma20.add(Float.NaN);
			} else {
				for (int m = i - 19; m <= i; m++) {
					sum20 += values.get(m).vol;
				}
				ma20.add(sum20 / 20);
			}
		}
	}


	@Override
	public double[] getMaxValue(int start, int stop) {
		double maxMin[] = new double[2];
		double max = Float.MIN_VALUE;
		double min =0.0f;

		if(kLists!=null&&!kLists.isEmpty()){
			for (int i = start;i<=stop;i++){

				double v = kLists.get(i).vol;
				if (v > max) {
					max = v;
				}

				float ma5v = ma5.get(i);
				float ma10v = ma10.get(i);
				float ma20v = ma20.get(i);
				if (ma5v > max) {
					max = ma5v;
				}
				if (ma10v > max) {
					max = ma10v;
				}
				if (ma20v > max) {
					max = ma20v;
				}
				if (ma5v < min) {
					min = ma5v;
				}
				if (ma10v < min) {
					min = ma10v;
				}
				if (ma20v < min) {
					min = ma20v;
				}
			}
		}

		maxMin[0] = max;
		maxMin[1] = min;
		return maxMin;
	}

	@Override
	public void draw(Context context, Canvas canvas, List<KLineParam> mDisplayKlines, Paint mPaint, float rect2_L, float rect2_T, float rect2_R, float rect2_B, float mScaleX, double mMainMaxValue, double mMainMinValue) {

		if (ma5!=null&&!ma5.isEmpty()&&ma10!=null&&!ma10.isEmpty()&&ma20!=null&&!ma20.isEmpty()){
			if (mDisplayKlines.size() <= 0) {
				return;
			}
			int firstIndex = mDisplayKlines.get(0).index;
			int lastIndex = mDisplayKlines.get(mDisplayKlines.size() - 1).index;

			List<Float> ma5 = getMa5().subList(firstIndex, lastIndex + 1);
			List<Float> ma10 = getMa10().subList(firstIndex, lastIndex + 1);
			List<Float> ma20 = getMa20().subList(firstIndex, lastIndex + 1);

			double scale = (rect2_B - rect2_T) / (mMainMaxValue - mMainMinValue);
			for (int i = 0; i < mDisplayKlines.size(); i++) {
				KLineParam kLineParam = mDisplayKlines.get(i);

				float open = (float) kLineParam.candelData.open;
				float close = (float) kLineParam.candelData.close;
				float v = (float) kLineParam.candelData.vol;

				if (close > open
						|| (close == open && i != 0 && close >= mDisplayKlines
						.get(i - 1).candelData.close)) { // 涨
					maRectPaint.setColor(context.getResources().getColor(R.color.kline_green));
				} else if (close < open){
					maRectPaint.setColor(context.getResources().getColor(R.color.kline_red));
				}else{
					if (kLineParam.candelData.open >= kLineParam.candelData.close){
//					startY = closeY;
//					endY = openY;
						maRectPaint.setColor(context.getResources().getColor(R.color.kline_green));
					}else{
//					startY = closeY;
//					endY = openY;
						maRectPaint.setColor(context.getResources().getColor(R.color.kline_red));
					}
//				maRectPaint.setColor(context.getResources().getColor(R.color.color_07B351_common_green));
				}
				float l = kLineParam.l;
				float r = kLineParam.r;
				if (r - l < 1.0f) {
					r += 1.0f;
				}
				canvas.drawRect( l, (float) (rect2_B - v * scale), r, rect2_B,
						maRectPaint);
			}

			for (int i = 1; i < ma5.size(); i++) {
				canvas.drawLine(mDisplayKlines.get(i-1).yx, (float) (rect2_B - ma5.get(i-1) * scale),mDisplayKlines.get(i).yx, (float) (rect2_B - ma5.get(i) * scale),ma5Paint);
			}
			for (int i = 1; i < ma10.size(); i++) {
				canvas.drawLine(mDisplayKlines.get(i-1).yx, (float) (rect2_B - ma10.get(i-1) * scale),mDisplayKlines.get(i).yx, (float) (rect2_B - ma10.get(i) * scale),ma10Paint);
			}
			for (int i = 1; i < ma20.size(); i++) {
				canvas.drawLine(mDisplayKlines.get(i-1).yx, (float) (rect2_B - ma20.get(i-1) * scale),mDisplayKlines.get(i).yx, (float) (rect2_B - ma20.get(i) * scale), ma20Paint);
			}
		}

	}

	@Override
	public void drawText(Canvas canvas, float Rect_L, float Rect_T, int selectedIndex, Paint mPaint) {
		if (ma5!=null&&!ma5.isEmpty()&&ma10!=null&&!ma10.isEmpty()&&ma20!=null&&!ma20.isEmpty()){

			float currentX = Rect_L;
			mTextPaint.setColor(mContext.getResources().getColor(R.color.color_4077E6_fenshi_now));
			canvas.drawText("VOL:5: " + UIHelper.formatVolumn(ma5.get(selectedIndex)), currentX, Rect_T+getTextHeight(mTextPaint)/2, mTextPaint);
			currentX = currentX + getTextWidth("VOL:5: " + UIHelper.formatVolumn(ma5.get(selectedIndex)), mTextPaint) +  mTextPaint.measureText("  ");
			mTextPaint.setColor(mContext.getResources().getColor(R.color.color_FF9320_orange));
			canvas.drawText("10: " + UIHelper.formatVolumn(ma10.get(selectedIndex)), currentX, Rect_T+getTextHeight(mTextPaint)/2, mTextPaint);
			currentX = currentX + getTextWidth("10: " + UIHelper.formatVolumn(ma10.get(selectedIndex)), mTextPaint) + mTextPaint.measureText("  ");
			mTextPaint.setColor(mContext.getResources().getColor(R.color.color_F562C4_fenshi_pink));
			canvas.drawText("20: " + UIHelper.formatVolumn(ma20.get(selectedIndex)), currentX, Rect_T+getTextHeight(mTextPaint)/2, mTextPaint);
		}

	}
	@Override
	public int getIndex() {
		return INDICATOR_INDEX_VOL;
	}

}

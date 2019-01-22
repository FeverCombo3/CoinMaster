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
 * RSI指标
 *
 */
public class RSI extends Indicator implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Float> rsi6 = new ArrayList<>();
	private List<Float> rsi12 = new ArrayList<>();
	private List<Float> rsi20 = new ArrayList<>();

	private Paint textPaint;
	private Paint r6Paint;
	private Paint r12Paint;
	private Paint r20Paint;

	public RSI(Context context) {
		super(context);
		textPaint = new Paint();
		r6Paint = new Paint();
		r12Paint = new Paint();
		r20Paint = new Paint();


		textPaint.setTextSize(textSize);

		r6Paint.setStyle(Paint.Style.STROKE);
		r6Paint.setColor(context.getResources().getColor(R.color.kline_purple));
		r6Paint.setStrokeWidth(DisplayUtils.DENSITY);

		r12Paint.setStyle(Paint.Style.STROKE);
		r12Paint.setColor(context.getResources().getColor(R.color.color_4077E6_fenshi_now));
		r12Paint.setStrokeWidth(DisplayUtils.DENSITY);

		r20Paint.setStyle(Paint.Style.STROKE);
		r20Paint.setColor(context.getResources().getColor(R.color.color_F562C4_fenshi_pink));
		r20Paint.setStrokeWidth(DisplayUtils.DENSITY);
	}

	public List<Float> getRsi6() {
		return rsi6;
	}

	public void setRsi6(List<Float> rsi6) {
		this.rsi6 = rsi6;
	}

	public List<Float> getRsi12() {
		return rsi12;
	}

	public void setRsi12(List<Float> rsi12) {
		this.rsi12 = rsi12;
	}

	public List<Float> getRsi20() {
		return rsi20;
	}

	public void setRsi20(List<Float> rsi20) {
		this.rsi20 = rsi20;
	}

	/**
	 * 计算RSI
	 * 
	 */
	public void compute(List<CandleData> values) {
		if (values == null || values.size() == 0) {
			return;
		}
		rsi6.clear();
		rsi12.clear();
		rsi20.clear();

		float rsif;
		float max_f;
		float abs_f;
		float rsia6;
		float rsia12;
		float rsia20;
		float rsib6;
		float rsib12;
		float rsib20;

		List<Float> SMAa6 = new ArrayList<>();
		List<Float> SMAa12 = new ArrayList<>();
		List<Float> SMAa20 = new ArrayList<>();
		List<Float> SMAb6 = new ArrayList<>();
		List<Float> SMAb12 = new ArrayList<>();
		List<Float> SMAb20 = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			CandleData value = values.get(i);
			if (i == 0) {
				rsif = 0;
				max_f = Math.max(rsif, 0);
				abs_f = Math.abs(rsif);

				rsia6 = max_f;
				rsia12 = max_f;
				rsia20 = max_f;

				rsib6 = abs_f;
				rsib12 = abs_f;
				rsib20 = abs_f;
			} else {
				rsif = (float) (value.close - values.get(i - 1).close);
				max_f = Math.max(rsif, 0);
				abs_f = Math.abs(rsif);

				rsia6 = (1 * max_f + (6 - 1) * SMAa6.get(i - 1)) / 6;
				rsia12 = (1 * max_f + (12 - 1) * SMAa12.get(i - 1)) / 12;
				rsia20 = (1 * max_f + (20 - 1) * SMAa20.get(i - 1)) / 20;

				rsib6 = (1 * abs_f + (6 - 1) * SMAb6.get(i - 1)) / 6;
				rsib12 = (1 * abs_f + (12 - 1) * SMAb12.get(i - 1)) / 12;
				rsib20 = (1 * abs_f + (20 - 1) * SMAb20.get(i - 1)) / 20;

			}

			SMAa6.add(rsia6);
			SMAa12.add(rsia12);
			SMAa20.add(rsia20);

			SMAb6.add(rsib6);
			SMAb12.add(rsib12);
			SMAb20.add(rsib20);

			if (rsib6 == 0) {
				rsi6.add(50.0f);
			} else {
				rsi6.add(rsia6 / rsib6 * 100);
			}
			if (rsib12 == 0) {
				rsi12.add(50.0f);
			} else {
				rsi12.add(rsia12 / rsib12 * 100);
			}

			if (rsib20 == 0) {
				rsi20.add(50.0f);
			} else {
				rsi20.add(rsia20 / rsib20 * 100);
			}
		}
	}

	@Override
	public double[] getMaxValue(int start, int stop) {
		double maxMin[] = new double[2];
		maxMin[0] = 100;
		maxMin[1] = 0;
		return maxMin;
	}

	@Override
	public void draw(Context context, Canvas canvas, List<KLineParam> hqItemDataList, Paint paint, float rect2_L, float rect2_T, float rect2_R, float rect2_B, float mScaleX, double mMainMaxValue, double mMainMinValue) {
		if (hqItemDataList.size() == 0) {
			return;
		}

		int firstIndex = hqItemDataList.get(0).index;
		int lastIndex = hqItemDataList.get(hqItemDataList.size() - 1).index;

		List<Float> rsi6 = getRsi6().subList(firstIndex, lastIndex + 1);
		List<Float> rsi12 = getRsi12().subList(firstIndex, lastIndex + 1);
		List<Float> rsi20 = getRsi20().subList(firstIndex, lastIndex + 1);

		float max = 100.0f;
		float min = 0.0f;
		float scale = (rect2_B - rect2_T) / (max - min);
		Path path = new Path();
		path.moveTo(hqItemDataList.get(0).yx, (float) (rect2_B - (rsi6.get(0) - min)
				* scale));
		for (int i = 0; i < rsi6.size(); i++) {
			path.lineTo(hqItemDataList.get(i).yx, (float) (rect2_B - (rsi6.get(i) - min)
					* scale));
		}
		canvas.drawPath(path, r6Paint);

		path.reset();
		path.moveTo(hqItemDataList.get(0).yx, (float) (rect2_B - (rsi12.get(0) - min)
				* scale));
		for (int i = 0; i < rsi12.size(); i++) {
			path.lineTo(hqItemDataList.get(i).yx, (float) (rect2_B
					- (rsi12.get(i) - min) * scale));
		}
		canvas.drawPath(path, r12Paint);

		path.reset();
		path.moveTo(hqItemDataList.get(0).yx, (float) (rect2_B - (rsi20.get(0) - min)
				* scale));
		for (int i = 0; i < rsi20.size(); i++) {
			path.lineTo(hqItemDataList.get(i).yx, (float) (rect2_B
					- (rsi20.get(i) - min) * scale));
		}
		canvas.drawPath(path, r20Paint);
	}
	@Override
	public String getName() {
		return "RSI";
	}

	@Override
	public int getposition() {
		return 3;
	}


	@Override
	public int getIndex() {
		return INDICATOR_INDEX_RSI;
	}

	@Override
	public void drawText(Canvas canvas, float Rect_L, float Rect_T, int selectedIndex, Paint mPaint) {
		float currentX = Rect_L;
		textPaint.setColor(mContext.getResources().getColor(R.color.kline_purple));
		canvas.drawText("RSI 6:" + UIHelper.formatVolumn(rsi6.get(selectedIndex)), currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
		currentX = currentX + getTextWidth("RSI 6:" + UIHelper.formatVolumn(rsi6.get(selectedIndex)), textPaint) + textPaint.measureText("  ");
		textPaint.setColor(mContext.getResources().getColor(R.color.color_4077E6_fenshi_now));
		canvas.drawText("12:" + UIHelper.formatVolumn(rsi12.get(selectedIndex)), currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
		currentX = currentX + getTextWidth("12:" + UIHelper.formatVolumn(rsi12.get(selectedIndex)), textPaint) + textPaint.measureText("  ");
		textPaint.setColor(mContext.getResources().getColor(R.color.color_F562C4_fenshi_pink));
		canvas.drawText("20:" + UIHelper.formatVolumn(rsi20.get(selectedIndex)), currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
	}
}

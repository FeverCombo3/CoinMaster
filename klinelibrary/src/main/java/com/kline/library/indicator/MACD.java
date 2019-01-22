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
 * MACD指标
 * 
 */
public class MACD extends Indicator implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private float default_short = 12;
	private float default_long = 26;
	private float default_mid = 9;

	private List<Float> difs = new ArrayList<>();
	private List<Float> dems = new ArrayList<>();
	private List<Float> oscs = new ArrayList<>();

	private Paint difPaint;
	private Paint demPaint;
	private Paint oscsPaint;
	private Paint textPaint;

	
	public MACD(Context context) {
		super(context);
		difPaint = new Paint();
		demPaint = new Paint();
		oscsPaint = new Paint();
		textPaint = new Paint();

		textPaint.setTextSize(textSize);

		difPaint.setStyle(Paint.Style.STROKE);
		difPaint.setColor(context.getResources().getColor(R.color.kline_yellow));
		difPaint.setStrokeWidth(DisplayUtils.DENSITY);

		demPaint.setStyle(Paint.Style.STROKE);
		demPaint.setColor(context.getResources().getColor(R.color.kline_qing));
		demPaint.setStrokeWidth(DisplayUtils.DENSITY);

		oscsPaint.setStyle(Paint.Style.STROKE);
		oscsPaint.setStrokeWidth(DisplayUtils.DENSITY);


	}


    public List<Float> getDifs() {
		return difs;
	}

	public void setDifs(List<Float> difs) {
		this.difs = difs;
	}

	public List<Float> getDems() {
		return dems;
	}

	public void setDems(List<Float> dems) {
		this.dems = dems;
	}

	public List<Float> getOscs() {
		return oscs;
	}

	public void setOscs(List<Float> oscs) {
		this.oscs = oscs;
	}

	/**
	 * 计算MACD
	 * 
	 */
	@Override
	public void compute(List<CandleData> values) {
		if (values == null || values.size() == 0) {
			return;
		}
		difs.clear();
		oscs.clear();
		dems.clear();
		
		float emasdata = 0.0f;
		float emaldata = 0.0f;
		float difdata = 0.0f;
		float deadata = 0.0f;
		float macddata = 0.0f;
		List<Float> emas = new ArrayList<>();
		List<Float> emal = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			CandleData kList = values.get(i);

			// 短日ema计算
			// 长日ema计算
			if (i == 0) {
				emasdata = (float) kList.close;
				emaldata = (float) kList.close;

			} else {
				emasdata = (float) ((2 * kList.close + (12 - 1) * emas.get(i - 1))
                                        / (12 + 1));
				emaldata = (float) ((2 * kList.close + (26 - 1) * emal.get(i - 1))
                                        / (26 + 1));
			}
			emas.add(emasdata);
			emal.add(emaldata);
			// dif计算
			difdata = emasdata - emaldata;
			difs.add(difdata);
		}
		// dea、macd计算

		if (difs.size() > 0) {
			for (int i = 0; i < difs.size(); i++) {
				if (i == 0) {
					deadata = difs.get(i);
				} else {
					deadata = (2 * difs.get(i) + (9 - 1) * dems.get(i - 1))
							/ (9 + 1);
				}
				// macd计算
				macddata = 2 * (difs.get(i) - deadata);
				oscs.add(macddata);
				// dea
				dems.add(deadata);
			}
		}
	}

	@Override
	public double[] getMaxValue(int start, int stop) {
		double maxMin[] = new double[2];
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		for (int i = start;i<=stop;i++){
			if (difs.size()!=0&&dems.size()!=0&&oscs.size()!=0){
				float dif = difs.get(i);
				float dem = dems.get(i);
				float osc = oscs.get(i);
				if (dif > max) {
					max = dif;
				}
				if (dem > max) {
					max = dem;
				}
				if (osc > max) {
					max = osc;
				}
				if (dif < min) {
					min = dif;
				}
				if (dem < min) {
					min = dem;
				}
				if (osc < min) {
					min = osc;
				}
			}
		}
		maxMin[0] = max;
		maxMin[1] = min;
		return maxMin;
	}

	@Override
	public void draw(Context context, Canvas canvas, List<KLineParam> mDisplayKlines, Paint mPaint, float rect2_L, float rect2_T, float rect2_R, float rect2_B, float mScaleX, double mMainMaxValue, double mMainMinValue) {
		if (mDisplayKlines.size() == 0) {
			return;
		}


		int firstIndex = mDisplayKlines.get(0).index;
		int lastIndex = mDisplayKlines.get(mDisplayKlines.size() - 1).index;

		List<Float> difs = getDifs().subList(firstIndex, lastIndex + 1);
		List<Float> dems = getDems().subList(firstIndex, lastIndex + 1);
		List<Float> oscs = getOscs().subList(firstIndex, lastIndex + 1);

		float scale = (float) ((rect2_B - rect2_T) / (mMainMaxValue - mMainMinValue));
		Path path = new Path();
		path.moveTo(mDisplayKlines.get(0).yx, (float) (rect2_B - (difs.get(0) - mMainMinValue)
				* scale));
		for (int i = 1; i < difs.size(); i++) {
			path.lineTo(mDisplayKlines.get(i).yx, (float) (rect2_B - (difs.get(i) - mMainMinValue)
					* scale));
		}
		canvas.drawPath(path, difPaint);

		// 画dem
		path.reset();
		path.moveTo(mDisplayKlines.get(0).yx, (float) (rect2_B - (dems.get(0) - mMainMinValue)
				* scale));
		for (int i = 1; i < dems.size(); i++) {
			path.lineTo(mDisplayKlines.get(i).yx, (float) (rect2_B - (dems.get(i) - mMainMinValue)
					* scale));
		}
		canvas.drawPath(path, demPaint);

		// 画osc
		float middle = (float) (rect2_B - (0 - mMainMinValue) * scale);
		for (int i = 1; i < oscs.size(); i++) {
			float osc = oscs.get(i);
			if (osc > 0.0f) {
				oscsPaint.setColor(mContext.getResources().getColor(R.color.kline_green));
			} else {
				oscsPaint.setColor(mContext.getResources().getColor(R.color.kline_red));
			}
			canvas.drawLine(mDisplayKlines.get(i).yx, (float) (rect2_B - (osc - mMainMinValue)
                                * scale), mDisplayKlines.get(i).yx,  middle, oscsPaint);
		}
	}

	@Override
	public String getName() {
		return "MACD";
	}

	@Override
	public int getposition() {
		return 3;
	}

	@Override
	public int getIndex() {
		return INDICATOR_INDEX_MACD;
	}

	@Override
	public void drawText(Canvas canvas, float Rect_L, float Rect_T, int selectedIndex, Paint mPaint) {
		float currentX = Rect_L;
		textPaint.setColor(mContext.getResources().getColor(R.color.kline_yellow));
		canvas.drawText("MACD:DIFF:" + UIHelper.formatVolumn(difs.get(selectedIndex)), currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
		currentX = currentX + getTextWidth("MACD:DIFF:" + UIHelper.formatVolumn(difs.get(selectedIndex)), textPaint) + + textPaint.measureText("  ");
		textPaint.setColor(mContext.getResources().getColor(R.color.kline_qing));
		canvas.drawText("DEA:" + UIHelper.formatVolumn(dems.get(selectedIndex)), currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
		currentX = currentX + getTextWidth("DEA:" + UIHelper.formatVolumn(dems.get(selectedIndex)), textPaint) + + textPaint.measureText("  ");
		if (oscs.get(selectedIndex)> 0.0f) {
			textPaint.setColor(mContext.getResources().getColor(R.color.kline_green));
		} else {
			textPaint.setColor(mContext.getResources().getColor(R.color.kline_red));
		}
		canvas.drawText("MACD:" + UIHelper.formatVolumn(oscs.get(selectedIndex)), currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
	}
}

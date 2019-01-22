package com.kline.library.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.kline.library.bean.CandleData;
import com.kline.library.util.DisplayUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 指标,所有指标需要继承该类
 * @author User
 *
 */
public abstract class Indicator implements Serializable {

	public static final int INDICATOR_INDEX_K = 0;
	public static final int INDICATOR_INDEX_VOL = 1;
	public static final int INDICATOR_INDEX_KDJ = 2;
	public static final int INDICATOR_INDEX_MACD = 3;
	public static final int INDICATOR_INDEX_RSI = 4;
	public static final int INDICATOR_INDEX_PSY = 5;
	public static final int INDICATOR_INDEX_MA = 6;
	public static final int INDICATOR_INDEX_DKTS = 7;
	public static final int INDICATOR_INDEX_CCI = 8;
	public static final int INDICATOR_INDEX_CPKX = 9;
	public static final int INDICATOR_INDEX_ZJQS = 10;
	public static final int INDICATOR_INDEX_FHPG = 11;
	public static final int INDICATOR_INDEX_WMT0 = 12;
	public static final int INDICATOR_INDEX_ZLCP = 13;
	public static final int INDICATOR_INDEX_BOLL = 14;

	public int textSize = DisplayUtils.sp2px(35 / 3);

	private static final long serialVersionUID = 1L;

	protected Context mContext;

	public Indicator(Context context) {
		this.mContext = context;
	}


	/**
	 * 指标名称
	 *
	 * @return String
	 */
	public abstract String getName();

	/**
	 * 指标位置
	 *
	 * @return String
	 */
	public abstract int getposition();

	/**
	 * 获取指标序号
	 *
	 * @return int
	 */
	public abstract int getIndex();

	public abstract void drawText(Canvas canvas, float Rect_L, float Rect_T, int selectedIndex, Paint mPaint);


	/**
	 * 获取文字宽度
	 *
	 * @param text  text
	 * @param paint paint
	 */
	public float getTextWidth(String text, Paint paint) {
		return paint.measureText(text);
	}

	//	/**
//	 * 获取文字高度
//	 */
//	public float getTextHeight(Paint paint) {
//
//		Paint.FontMetrics fontMetrics = paint.getFontMetrics();
//		return (float) Math.ceil(fontMetrics.descent - fontMetrics.ascent);
//	}
	public int getTextHeight(Paint paint) {
		Rect rect = new Rect();
		String s = "OK";
		paint.getTextBounds(s, 0, s.length(), rect);
		return rect.height();
	}


	/**
	 * 指标计算
	 *
	 * @param kLists kLists
	 */
	public abstract void compute(List<CandleData> kLists);

	public abstract double[] getMaxValue(int start, int stop);

	/**
	 * 画
	 */
	public abstract void draw(Context context, Canvas canvas, List<KLineParam> hqItemDataList, Paint paint, float l, float t, float r, float b, float mScaleX, double mMainMaxValue, double mMainMinValue);


	/**
	 * ABS
	 */
	protected List<Float> ABS(List<Float> values) {
		List<Float> ABS = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			float v = values.get(i);
			ABS.add(Math.abs(v));
		}
		return ABS;
	}

	/**
	 * SUM
	 *
	 * @return list
	 */
	protected List<Float> SUM(List<Float> values, int n) {
		List<Float> SUM = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			float sum = 0.0f;
			for (int m = i; m > i - n && m >= 0; m--) {
				float v1 = values.get(m);
				sum += v1;
			}
			SUM.add(sum);
		}
		return SUM;
	}

	/**
	 * REF
	 */
	protected List<Float> REF(List<Float> datas, float n) {
		List<Float> refs = new ArrayList<>();
		for (int i = 0; i < datas.size(); i++) {
			if (Float.isNaN(n)) {
				refs.add(Float.NaN);
			} else {
				if (i < n) {
					refs.add(datas.get(0));
				} else {
					refs.add(datas.get(i - (int) n));
				}
			}
		}
		return refs;
	}

	/**
	 * EMA
	 */
	protected List<Float> EMA(List<Float> values, int n) {
		List<Float> emas = new ArrayList<>();

		for (int i = 0; i < values.size(); i++) {
			float value = values.get(i);
			if (Float.isNaN(value)) {
				// value = 0.0f;
				emas.add(Float.NaN);
				continue;
			}
			if (i == 0) {
				emas.add(value);
				continue;
			}

			float pre = emas.get(i - 1);

			if (Float.isNaN(pre)) {
				emas.add(value);
				continue;
			}

			float ema = pre * (n - 1) / (n + 1) + value * 2 / (n + 1);
			emas.add(ema);
		}
		return emas;
	}

	/**
	 * LLV
	 */
	protected List<Float> LLV(List<Float> values, float n) {
		List<Float> llvs = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			float low = values.get(i);
			if (Float.isNaN(low)) {
				llvs.add(low);
				continue;
			}
			if (n == 0) {
				for (int m = i; m >= 0; m--) {
					float v = values.get(m);
					if (v < low) {
						low = v;
					}
				}
			} else {
				for (int m = i, index = 0; m >= 0 && index < n; m--, index++) {
					float v = values.get(m);
					if (v < low) {
						low = v;
					}
				}
			}
			llvs.add(low);
		}
		return llvs;
	}

	/**
	 * HHV
	 */
	protected List<Float> HHV(List<Float> values, float n) {
		List<Float> hhvs = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			float high = values.get(i);
			if (Float.isNaN(high)) {
				hhvs.add(high);
				continue;
			}
			if (n == 0) {
				for (int m = i; m >= 0; m--) {
					float v = values.get(m);
					if (v > high) {
						high = v;
					}
				}
			} else {
				for (int m = i, index = 0; m >= 0 && index < n; m--, index++) {
					float v = values.get(m);
					if (v > high) {
						high = v;
					}
				}
			}

			hhvs.add(high);
		}
		return hhvs;
	}

	/**
	 * SMA
	 */
	protected List<Float> SMA(List<Float> datas, int n, int m) {
		List<Float> smas = new ArrayList<>();
		boolean init = false;
		for (int i = 0; i < datas.size(); i++) {
			float x = datas.get(i);
			if (Float.isNaN(x)) {
				smas.add(Float.NaN);
				continue;
			}

			if (!init) {
				init = true;
				smas.add(x);
				continue;
			}

			float pre = smas.get(i - 1);
			float y = (m * x + (n - m) * pre) / n;
			smas.add(y);
		}
		return smas;
	}

	/**
	 * MAX
	 *
	 * @return
	 */
	protected List<Float> MAX(List<Float> values, float v2) {
		List<Float> MAXS = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			float v = values.get(i);
			float max = Math.max(v, v2);
			MAXS.add(max);
		}
		return MAXS;
	}

	/**
	 * CROSS
	 */
	protected List<Float> CROSS(List<Float> datas, float data2) {
		List<Float> crosses = new ArrayList<>();
		for (int i = 0; i < datas.size(); i++) {
			float d1 = datas.get(i);
			if (Float.isNaN(d1) || Float.isNaN(data2)) {
				crosses.add(Float.NaN);
			} else if (i == 0) {
				crosses.add(Float.NaN);
			} else if (d1 > data2) {
				if (datas.get(i - 1) < data2) {
					crosses.add(1.0f);
				} else {
					crosses.add(0.0f);
				}
			} else {
				crosses.add(0.0f);
			}
		}
		return crosses;
	}

	/**
	 * CROSS
	 */
	protected List<Float> CROSS(float data1, List<Float> datas2) {
		List<Float> crosses = new ArrayList<>();
		for (int i = 0; i < datas2.size(); i++) {
			float d2 = datas2.get(i);
			if (Float.isNaN(data1) || Float.isNaN(d2)) {
				crosses.add(Float.NaN);
			} else if (i == 0) {
				crosses.add(Float.NaN);
			} else if (data1 > d2) {
				if (data1 < datas2.get(i - 1)) {
					crosses.add(1.0f);
				} else {
					crosses.add(0.0f);
				}
			} else {
				crosses.add(0.0f);
			}
		}
		return crosses;
	}

	/**
	 * MA
	 *
	 * @param values values
	 * @param n      n
	 * @return list
	 */
	protected List<Float> MA(List<Float> values, float n) {
		List<Float> mas = new ArrayList<>();

		for (int i = 0; i < values.size(); i++) {
			if (i < n - 1) {
				mas.add(Float.NaN);
			} else {
				float v = 0.0f;
				for (int m = (int) (i - n + 1); m <= i; m++) {
					v += values.get(m);
				}
				mas.add(v / n);
			}
		}
		return mas;
	}

	/**
	 * STD
	 * @param values
	 * @param n
	 * @return
	 */
	public List<Float> STD(List<Float> values, int n){
		List<Float> std = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			if (i < n - 1) {
				std.add(Float.NaN);
			} else {
				float v = 0.0f;
				for (int m = (i - n + 1); m <= i; m++) {
					v += values.get(m);
				}
				float m = v / n;
				float v1 = 0.0f;
				for (int j =  (i - n + 1); j <= i; j++){
					v1 += (Math.pow(values.get(j)-m,2))/n;
				}
				std.add((float) Math.sqrt(v1));
			}
		}
		return std;
	}
}

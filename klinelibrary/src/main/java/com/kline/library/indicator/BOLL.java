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
 * 均线
 *
 * @author
 *
 */
public class BOLL extends Indicator implements Serializable {

	private int params1 = 20;
	private int params2 = 2;

	private int params3 = 3;
	private int params4 = 6;
	private int params5 = 12;
	private int params6 = 24;

	private List<Float> boll = new ArrayList<>();
	private List<Float> bollup = new ArrayList<>();
	private List<Float> bolldown = new ArrayList<>();

	private Paint upPaint;
	private Paint downPaint;
	private Paint midPaint;
	private Paint textPaint;

	private Path path1;
	private Path path2;
	private Path path3;

	public BOLL(Context context) {
		super(context);
		upPaint = new Paint();
		downPaint = new Paint();
		midPaint = new Paint();

		textPaint = new Paint();

		textPaint.setTextSize(textSize);

		upPaint.setStyle(Paint.Style.STROKE);
		upPaint.setColor(context.getResources().getColor(R.color.kline_qing));
		upPaint.setStrokeWidth(DisplayUtils.DENSITY);

		downPaint.setStyle(Paint.Style.STROKE);
		downPaint.setColor(context.getResources().getColor(R.color.kline_purple));
		downPaint.setStrokeWidth(DisplayUtils.DENSITY);

		midPaint.setStyle(Paint.Style.STROKE);
		midPaint.setStrokeWidth(DisplayUtils.DENSITY);
		midPaint.setColor(context.getResources().getColor(R.color.kline_yellow));

		path1 = new Path();
		path2 = new Path();
		path3 = new Path();
	}

	/**
	 * 计算均线
	 *
	 */
	public void compute(List<CandleData> values) {

		boll.clear();
		bollup.clear();
		bolldown.clear();

		//收盘价
		List<Float> closes = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			closes.add((float) values.get(i).close);
		}
		List<Float> ma24 = MA(closes, 20);
		boll.addAll(ma24);
		List<Float> std = STD(closes, params1);
		for (int i = 0; i < std.size(); i++) {
			bollup.add(boll.get(i)+std.get(i)*2);
			bolldown.add(boll.get(i)-std.get(i)*2);
		}
	}

	@Override
	public double[] getMaxValue(int firstIndex, int lastIndex) {
		double maxMin[] = new double[2];
		float max = Float.MIN_VALUE;
		float min = Float.MAX_VALUE;
		for (int i = firstIndex;i<=lastIndex;i++){
			if (boll.size()!=0&&bollup.size()!=0&&bolldown.size()!=0){
				float dif = boll.get(i);
				float dem = bollup.get(i);
				float osc = bolldown.get(i);
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
	public void draw(Context context, Canvas canvas, List<KLineParam> hqItemDataList, Paint mPaint, float rect1_L, float rect1_T, float rect1_R, float rect1_B, float mScaleX, double mMainMaxValue, double mMainMinValue) {
		if (hqItemDataList.size() == 0) {
			return;
		}
		path1.reset();
		path2.reset();
		path3.reset();

		int firstIndex = hqItemDataList.get(0).index;
		int lastIndex = hqItemDataList.get(hqItemDataList.size() - 1).index;

		List<Float> bol = boll.subList(firstIndex, lastIndex + 1);
		List<Float> bolup= bollup.subList(firstIndex, lastIndex + 1);
		List<Float> boldown = bolldown.subList(firstIndex, lastIndex + 1);

		float scale = (float) ((rect1_B - rect1_T) / (mMainMaxValue - mMainMinValue));

		for (int i = 0; i < bol.size(); i++) {
			if (i==0){
				path1.moveTo(hqItemDataList.get(i).yx, (float) (rect1_B - (bol.get(i) - mMainMinValue) * scale));
				path2.moveTo(hqItemDataList.get(i).yx, (float) (rect1_B - (bolup.get(i) - mMainMinValue) * scale));
				path3.moveTo(hqItemDataList.get(i).yx, (float) (rect1_B - (boldown.get(i) - mMainMinValue) * scale));
			}else {
				path1.lineTo(hqItemDataList.get(i).yx, (float) (rect1_B - (bol.get(i) - mMainMinValue) * scale));
				path2.lineTo(hqItemDataList.get(i).yx, (float) (rect1_B - (bolup.get(i) - mMainMinValue) * scale));
				path3.lineTo(hqItemDataList.get(i).yx, (float) (rect1_B - (boldown.get(i) - mMainMinValue) * scale));
			}
		}
		canvas.drawPath(path1,midPaint);
		canvas.drawPath(path2,upPaint);
		canvas.drawPath(path3,downPaint);
	}


	@Override
	public String getName() {
		return "BOLL";
	}

	@Override
	public int getposition() {
		return 1;
	}


	@Override
	public int getIndex() {
		return INDICATOR_INDEX_BOLL;
	}

	@Override
	public void drawText(Canvas canvas, float Rect_L, float Rect_T, int selectedIndex, Paint mPaint) {

		float currentX = Rect_L;
		textPaint.setColor(mContext.getResources().getColor(R.color.kline_yellow));
		canvas.drawText("BOLL:" + UIHelper.formatVolumn(boll.get(selectedIndex)), currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
		currentX = currentX + getTextWidth("BOLL:" + UIHelper.formatVolumn(boll.get(selectedIndex)), textPaint) + + textPaint.measureText("  ");
		textPaint.setColor(mContext.getResources().getColor(R.color.kline_qing));
		canvas.drawText("UB:" + UIHelper.formatVolumn(bollup.get(selectedIndex)), currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
		currentX = currentX + getTextWidth("UB:" + UIHelper.formatVolumn(bollup.get(selectedIndex)), textPaint) + + textPaint.measureText("  ");
		textPaint.setColor(mContext.getResources().getColor(R.color.kline_purple));
		canvas.drawText("LB:" + UIHelper.formatVolumn(bolldown.get(selectedIndex)), currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
	}
}

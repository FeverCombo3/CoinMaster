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
 * 均线
 *
 * @author
 *
 */
public class MA extends Indicator implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<List<Float>> junxianlist = new ArrayList<>();

	private int[] array;

	private Paint textPaint;
	private Paint MAPaint;

	private List<Paint> mPaintList = new ArrayList<>();

	public MA(Context context) {
		super(context);
		array = context.getResources().getIntArray(R.array.ma_color);
		textPaint = new Paint();
		MAPaint = new Paint();
		textPaint.setTextSize(textSize);
	}

	/**
	 * 计算均线
	 *
	 */
	public void compute(List<CandleData> values) {

		junxianlist.clear();

		if (values == null || values.size() == 0) {
			return;
		}

		float close;
		float junClose;

		for (int j = 0;j<3;j++){

			List<Float> jun = new ArrayList<>();

			int value = 5*(j+1) ;//假的周期
			if (j==2){
				value = 20;
			}
			for (int i = 0; i < values.size(); i++){

				if (i >= value-1) {

					close = 0f;

					for (int n = i; n > i - value; n--) {

						close += values.get(n).close;
					}
					junClose = close / value;

					jun.add(junClose);

				}else{
					jun.add(Float.NaN);
				}
			}
			junxianlist.add(jun);
		}
	}

	@Override
	public double[] getMaxValue(int firstIndex, int lastIndex) {
		double maxMin[] = new double[2];
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		for (int j = 0; j < junxianlist.size(); j++) {
			List<Float> list = junxianlist.get(j);
			for (int i = firstIndex; i <= lastIndex; i++) {
				float j5 = list.get(i);
				if (j5 != Float.NaN) {
					if (j5 > max) {
						max = j5;
					}
					if (j5 < min) {
						min = j5;
					}
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
		double scale = (rect1_B - rect1_T) / (mMainMaxValue - mMainMinValue);
		int firstIndex = hqItemDataList.get(0).index;
		int lastIndex = hqItemDataList.get(hqItemDataList.size() - 1).index;



		for (int j = 0;j<junxianlist.size();j++){
			List<Float> junjia  = junxianlist.get(j);

			for (int i = firstIndex +1; i<=lastIndex;i++){

				MAPaint.setStrokeWidth(DisplayUtils.DENSITY);

				MAPaint.setStyle(Paint.Style.STROKE);

				MAPaint.setColor(array[j]);

				canvas.drawLine(hqItemDataList.get(i-firstIndex-1).yx,(float) (rect1_B - ((junjia.get(i - 1) - mMainMinValue) * scale)),
						hqItemDataList.get(i-firstIndex).yx,(float) (rect1_B - (junjia.get(i) - mMainMinValue) * scale),MAPaint);

			}
		}
	}


	@Override
	public String getName() {
		return "MA";
	}

	@Override
	public int getposition() {
		return 1;
	}


	@Override
	public int getIndex() {
		return INDICATOR_INDEX_MA;
	}

	@Override
	public void drawText(Canvas canvas, float Rect_L, float Rect_T, int selectedIndex, Paint mPaint) {

		float currentX = Rect_L;
		try{
			for (int i=0;i<junxianlist.size();i++){
				int value = 5*(i+1) ;//假的周期
				if (i==2){
					value = 20;
				}
				List<Float> list= junxianlist.get(i);
				textPaint.setColor(array[i]);
				String s;
				if (Float.isNaN(list.get(selectedIndex))){
					s = "--";
				}else{
					s = UIHelper.formatVolumn(list.get(selectedIndex));

				}
				canvas.drawText("MA"+value+":" + s, currentX, Rect_T+getTextHeight(textPaint)/2, textPaint);
				currentX = currentX + getTextWidth("MA"+value+":" + s, textPaint) + textPaint.measureText("  ");
			}
		}catch (IndexOutOfBoundsException e){
		}
	}
}

package com.kline.library.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class UIHelper {

	// 全部界面
	public static List<Activity> activities = new ArrayList<>();

	public static void exitApp() {
		for (int i = 0; i < activities.size(); i++) {
			Activity activity = activities.get(i);
			if (null != activity) {
				activity.finish();
			}
		}
		activities.clear();
	}

	public static void showToast(Context context, int resId) {
		String text = context.getString(resId);
		showToast(context, text);
	}

	private static Toast mToast = null;

	public static void showToast(Context context, String text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}

		mToast.show();
	}

	public static String formatPrice(float price) {
		DecimalFormat df = new DecimalFormat("#0.00");
		String str_price = price + "";
		if (Math.abs(price) > 100000000) {
			if (price / 100000000 > 100) {
				str_price = df.format(price / 100000000.0) + "亿";
			} else {
				str_price = df.format(price / 100000000.0) + "亿";
			}
		} else if (Math.abs(price) > 10000) {
			str_price = df.format(price / 10000.0) + "万";
		} else {
			str_price = df.format(price);
		}
		return str_price;
	}
	public static String formatPrice2(float price) {
		DecimalFormat df = new DecimalFormat("#0.00");
		String str_price = price + "";
		str_price = df.format(price);
		return str_price;
	}

	public static String formatPricexf(float price) {
		DecimalFormat df = new DecimalFormat("#0.00");
		DecimalFormat df2 = new DecimalFormat("#0");
		String str_price = price + "";
		if (Math.abs(price) > 100000000) {
				str_price = df.format(price / 100000000.0f) + "亿";

		} else if (Math.abs(price) > 1000) {
			str_price = df2.format(price / 10000.0) + "万";
		} else {
			str_price = df2.format(price);
		}
		return str_price;
	}

	public static String formatPriceN(float price) {
		DecimalFormat df = new DecimalFormat("#0.00");
		DecimalFormat dfint = new DecimalFormat("#");
		String str_price = price + "";
		if (Math.abs(price) > 100000000) {
			if (price / 100000000 > 100) {
				str_price = dfint.format(price / 1000000000.0f) + "十亿";
			} else {
				str_price = df.format(price / 10000000.0f) + "亿";
			}
		} else if (Math.abs(price) > 10000) {
			str_price = df.format(price / 10000.0) + "万";
		} else {
			str_price = df.format(price);
		}
		return str_price;
	}

	public static String formatPrice(long price) {
		DecimalFormat df = new DecimalFormat("#0.00");
		DecimalFormat dfint = new DecimalFormat("#");
		String str_price = price + "";
		if (Math.abs(price) > 100000000) {
			if (price / 100000000 > 100) {
				str_price = dfint.format(price / 100000000.0f) + "亿";
			} else {
				str_price = df.format(price / 100000000.0f) + "亿";
			}
		} else if (Math.abs(price) > 10000) {
			str_price = df.format(price / 10000.0) + "万";
		}
		return str_price;
	}

	public static String formatPriceN(long price) {
		DecimalFormat df = new DecimalFormat("#0.00");
		DecimalFormat dfint = new DecimalFormat("#");
		String str_price = price + "";
		if (Math.abs(price) > 100000000) {
			if (price / 100000000 > 100) {
				str_price = dfint.format(price / 1000000000.0f) + "十亿";
			} else {
				str_price = df.format(price / 100000000.0f) + "亿";
			}
		} else if (Math.abs(price) > 10000) {
			str_price = df.format(price / 10000.0) + "万";
		}
		return str_price;
	}

	public static String formatVolumn(long vol) {
		DecimalFormat df = new DecimalFormat("#0.00");
		DecimalFormat dfint = new DecimalFormat("#");
		String str_price = vol + "";
		if (Math.abs(vol) > 100000000) {
			if (vol / 100000000 > 100) {
				str_price = dfint.format(vol / 100000000.0) + "亿";
			} else {
				str_price = df.format(vol / 100000000.0f) + "亿";
			}
		} else if (Math.abs(vol) > 10000) {
			str_price = df.format(vol / 10000.0f) + "万";
		} else {
			str_price = dfint.format(vol);
		}
		return str_price;
	}

	public static String formatIntVolumn(int vol) {
		DecimalFormat dfint = new DecimalFormat("#0.00");
		String str_price = vol + "";
		if (Math.abs(vol) > 100000000) {
			if (vol / 100000000 > 100) {
				str_price = dfint.format(vol / 100000000.0f) + "亿";
			} else {
				str_price = dfint.format(vol / 100000000.0f) + "亿";
			}
		} else if (Math.abs(vol) > 10000) {
			str_price = dfint.format(vol / 10000.0f) + "万";
		} else {
			str_price = vol + "";
		}
		return str_price;
	}
	public static String formatIntVolumn(long vol) {
		DecimalFormat dfint = new DecimalFormat("#0.00");
		String str_price = vol + "";
		if (Math.abs(vol) > 100000000) {
			if (vol / 100000000 > 100) {
				str_price = dfint.format(vol / 100000000.0f) + "亿";
			} else {
				str_price = dfint.format(vol / 100000000.0f) + "亿";
			}
		} else if (Math.abs(vol) > 10000) {
			str_price = dfint.format(vol / 10000.0f) + "万";
		} else {
			str_price = vol + "";
		}
		return str_price;
	}

	public static String formatTradeVolumn(double vol) {
		DecimalFormat df = new DecimalFormat("#0.00");
		DecimalFormat dfint = new DecimalFormat("#");
		String str_price = vol + "";
		if (Math.abs(vol) > 100000000) {
			if (vol / 100000000 > 100) {
				str_price = dfint.format(vol / 100000000.0f) + "亿";
			} else {
				str_price = df.format(vol / 100000000.0f) + "亿";
			}
		} else if (Math.abs(vol) > 10000) {
			str_price = dfint.format(vol / 10000.0f) + "万";
		} else {
			str_price = dfint.format(vol);
		}
		return str_price;
	}

	public static String formatVolumn(double vol) {
		DecimalFormat df = new DecimalFormat("#0.00");
		String str_price = "";
		if (Double.isNaN(vol)){
			str_price = "--";
		}else{
			if (Math.abs(vol) > 100000000) {
				if (vol / 100000000 > 10000) {
					str_price = df.format(vol / 1000000000000.0f) + "万亿";
				} else {
					str_price = df.format(vol / 100000000.0f) + "亿";
				}
			} else if (Math.abs(vol) > 10000) {
				str_price = df.format(vol / 10000.0f) + "万";
			} else {
				str_price = df.format(vol);
			}
		}
		return str_price;
	}

	public static String formatMainValue(double value){
		DecimalFormat df = new DecimalFormat("#0.0000");
		return df.format(value);
	}

	public static String getRateValue(double nowClose, double preClose){
		double rate = (nowClose - preClose) / preClose * 100;
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(rate);

	}

	public static String formatMAVolumn(double vol) {
		DecimalFormat df = new DecimalFormat("#0.00");
		DecimalFormat dfint = new DecimalFormat("#");
		String str_price = "";
		if (Math.abs(vol) > 100000000) {
			if (vol / 100000000 > 10000) {
				str_price = df.format(vol / 1000000000000.0f) + "万亿";
			} else {
				str_price = df.format(vol / 100000000.0f) + "亿";
			}
		} else if (Math.abs(vol) > 10000) {
			str_price = df.format(vol / 10000.0f) + "万";
		} else {
			str_price = dfint.format(vol);
		}
		return str_price;
	}

	/**
	 * 数据格式化
	 * 
	 * @param data
	 * @return
	 */
	public static String formatdouble(double data) {
		String str_price;
		DecimalFormat format = new DecimalFormat("#0.00");
		if (Math.abs(data) > 100000000) {
			str_price = format.format(data / 100000000.0f) + "亿";
		} else if (Math.abs(data) > 10000) {
			str_price = format.format(data / 10000.0f) + "万";
		} else {
			str_price = format.format(data);
		}
		return str_price;
	}

	/**
	 *
	 * @param data
	 * @return 到万元
	 */

	public static String formatdoubleByWan(double data) {
		String str_price;
		DecimalFormat format = new DecimalFormat("#0.00");
		if (Math.abs(data) > 10000) {
			str_price = format.format(data / 10000.0f);
		} else {
			str_price = format.format(data);
		}
		return str_price;
	}

	public static String formatFloat(float data) {
		String str_price;
		DecimalFormat format = new DecimalFormat("#0.00");
		if (Math.abs(data) > 100000000) {
			str_price = format.format(data / 100000000.0f) + "亿";
		} else if (Math.abs(data) > 10000) {
			str_price = format.format(data / 10000.0f) + "万";
		} else {
			str_price = format.format(data*100)+"%";
		}
		return str_price;
	}

}

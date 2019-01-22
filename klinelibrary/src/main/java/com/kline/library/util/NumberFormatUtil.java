package com.kline.library.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yjq
 * 2018/5/23.
 */

public class NumberFormatUtil {

    /**
     * 将数据保留四位小数
     */
    public static String getFourDecimal(double num) {
        DecimalFormat dFormat=new DecimalFormat("0.0000");
        return dFormat.format(num);
    }

    /**
     * 将数据保留2位小数
     */
    public static String getTwoDecimal(double num) {
        DecimalFormat dFormat=new DecimalFormat("0.00");
        return dFormat.format(num);
    }

    /**
     * 美元兑人民币(理论上是实时变动的，这里先写死 1美元 = 6.71人民币)
     * @param value 美元
     * @return
     */
    public static String transToCny(double value){
        double cny = value * 6.71;
        return getTwoDecimal(cny);
    }

    /**
     * 将时间戳转为日期
     * @param second 秒
     * @return
     */
    public static String transDate(long second){
        Date date = new Date(second);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(date);
    }
}

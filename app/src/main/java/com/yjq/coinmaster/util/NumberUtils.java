package com.yjq.coinmaster.util;

/**
 * @author jjx
 * @date
 */

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.yjq.coinmaster.constant.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtils {

    public static final double d = 0.123456789;

    private static final Double MILLION = 10000.0;
    private static final Double THOUSAND = 1000.0;
    private static final Double MILLIONS = 1000000.0;
    private static final Double BILLION = 100000000.0;
    private static final String MILLION_UNIT = "万";
    private static final String BILLION_UNIT = "亿";


    /**
     * 将数字转换成以万为单位或者以亿为单位，因为在前端数字太大显示有问题
     */
    public static String amountConversion(double amount) {
        //最终返回的结果值
        String result = String.valueOf(amount);
        //四舍五入后的值
        double value = 0;
        //转换后的值
        double tempValue = 0;
        //余数
        double remainder = 0;

        //金额大于1百万小于1亿
        if (amount > MILLIONS && amount < BILLION) {
            tempValue = amount / MILLION;
            remainder = amount % MILLION;

            //余数小于5000则不进行四舍五入
            if (remainder < (MILLION / 2)) {
                value = formatNumber(tempValue, 2, false);
            } else {
                value = formatNumber(tempValue, 2, true);
            }
            //如果值刚好是10000万，则要变成1亿
            if (value == MILLION) {
                result = zeroFill(value / MILLION) + BILLION_UNIT;
            } else {
                result = zeroFill(value) + MILLION_UNIT;
            }
        }
        //金额大于1亿
        else if (amount > BILLION) {
            tempValue = amount / BILLION;
            remainder = amount % BILLION;

            //余数小于50000000则不进行四舍五入
            if (remainder < (BILLION / 2)) {
                value = formatNumber(tempValue, 2, false);
            } else {
                value = formatNumber(tempValue, 2, true);
            }
            result = zeroFill(value) + BILLION_UNIT;
        } else {
            result = zeroFill(amount);
        }
        return result;
    }


    /**
     * 对数字进行四舍五入，保留2位小数
     */
    public static Double formatNumber(double number, int decimal, boolean rounding) {
        BigDecimal bigDecimal = new BigDecimal(number);

        if (rounding) {
            return bigDecimal.setScale(decimal, RoundingMode.HALF_UP).doubleValue();
        } else {
            return bigDecimal.setScale(decimal, RoundingMode.DOWN).doubleValue();
        }
    }

    /**
     * 对四舍五入的数据进行补0显示，即显示.00
     */
    public static String zeroFill(double number) {
        String value = String.valueOf(number);

        if (!value.contains(".")) {
            value = value + ".00";
        } else {
            String decimalValue = value.substring(value.indexOf(".") + 1);
            if (decimalValue.length() < 2) {
                value = value + "0";
            }
        }
        return value;
    }

    public static String formatDouble2(double d) {

        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = null;
        if (d > 1000) {
            bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        } else if (d < 1000 && d > 1) {
            bg = new BigDecimal(d).setScale(4, RoundingMode.UP);
        } else if (d < 1 && d > 0) {
            bg = new BigDecimal(d).setScale(7, RoundingMode.UP);
        }
        if (bg != null) {
            return bg.doubleValue() + "";
        }
        return "";
    }


    /**
     * 处理价格 显示规则
     * <p>
     * 1,000≤number 	保留2位小数
     * 1≤number＜1,000 	保留4位小数
     * number＜1 	    保留7位小数
     */
    public static String formatToolPrice(double price) {
        String uname = "";
        long l = System.currentTimeMillis();

        if (price == d) {
            return "--";
        }

        int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
        double rate = 1;
        if (anInt == Constants.CNY) {
            uname = " CNY";
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_CNY, 1);
        } else if (anInt == Constants.ETH) {
            uname = " ETH";

            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTH, 1);
        } else if (anInt == Constants.BTC) {
            uname = " BTC";

            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTC, 1);
        } else if (anInt == Constants.USD) {
            uname = " USDT";
        } else {
            uname = " USDT";
        }
        price = price * rate;
        Log.e("sputils", "formatPrice time:" + (System.currentTimeMillis() - l));
        if (Math.abs(price) >= MILLION) {
            return keep2DecimalThousandthCharacter(price) + uname;
        } else if (Math.abs(price) >= THOUSAND) {
            return keep2DecimalThousandthCharacter(price) + uname;
        } else if (Math.abs(price) >= 1) {
            return keep4DecimalThousandthCharacter(price) + uname;
        } else {
            return keep8DecimalThousandthCharacter(price) + uname;
        }
    }


    /**
     * 处理价格 显示规则
     * <p>
     * 1,000≤number 	保留2位小数
     * 1≤number＜1,000 	保留4位小数
     * number＜1 	    保留7位小数
     */
    public static String formatToolPrice1(double price) {
        long l = System.currentTimeMillis();

        if (price == d) {
            return "--";
        }
        price = price;
        if (Math.abs(price) >= MILLION) {
            return keep2DecimalThousandthCharacter(price);
        } else if (Math.abs(price) >= THOUSAND) {
            return keep2DecimalThousandthCharacter(price);
        } else if (Math.abs(price) >= 1) {
            return keep4DecimalThousandthCharacter(price);
        } else {
            return keep8DecimalThousandthCharacter(price);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 处理价格 显示规则  什么都不处理 只处理小数位
     * <p>
     * 1,000≤number 	保留2位小数
     * 1≤number＜1,000 	保留4位小数
     * number＜1 	    保留7位小数
     */
    public static String formatCommonPrice(double price) {
        long l = System.currentTimeMillis();

        if (price == d) {
            return "--";
        }
        if (Math.abs(price) >= MILLION) {
            return keep2Decimal(price);
        } else if (Math.abs(price) >= THOUSAND) {
            return keep2Decimal(price);
        } else if (Math.abs(price) >= 1) {
            return keep4Decimal(price);
        } else {
            return keep8Decimal(price);
        }
    }


    public static String formatCommonPriceThousandthCharacter(double price) {
        long l = System.currentTimeMillis();

        if (price == d) {
            return "--";
        }
        if (Math.abs(price) >= MILLION) {
            return keep2DecimalThousandthCharacter(price);
        } else if (Math.abs(price) >= THOUSAND) {
            return keep2DecimalThousandthCharacter(price);
        } else if (Math.abs(price) >= 1) {
            return keep4DecimalThousandthCharacter(price);
        } else {
            return keep8DecimalThousandthCharacter(price);
        }
    }

    public static String formatCommonPriceThousandthCharacterWithUnit(double price) {
        long l = System.currentTimeMillis();

        if (price == d) {
            return "--";
        }
        int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
        double rate = 1;
        String s = "";
        if (anInt == Constants.CNY) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_CNY, 1);
//            s = "￥";
        } else if (anInt == Constants.ETH) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTH, 1);
//            s = "E";
        } else if (anInt == Constants.BTC) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTC, 1);
//            s = "B";
        } else if (anInt == Constants.USD) {
//            s = "$";
        } else if (anInt == Constants.USDT) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_Usdt, 1);
        }
        price = price * rate;
        if (Math.abs(price) >= MILLION) {
            return keep2DecimalThousandthCharacter(price);
        } else if (Math.abs(price) >= THOUSAND) {
            return keep2DecimalThousandthCharacter(price);
        } else if (Math.abs(price) >= 1) {
            return keep4DecimalThousandthCharacter(price);
        } else {
            return keep8DecimalThousandthCharacter(price);
        }
    }


    /**
     * 处理金额
     * <p>
     * 10,000≤number＜100,000,000 	已万作为单位，保留两位小数（例：23.45万）
     * 100,000,000≤number 	已亿作为单位，保留两位小数（例：23.45亿）
     */
    public static String formatAmount(double amount) {
        long l = System.currentTimeMillis();
        if (amount == d) {
            return "--";
        }

        int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
        double rate = 1;
        String s = "";
        if (anInt == Constants.CNY) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_CNY, 1);
//            s = "￥";
        } else if (anInt == Constants.ETH) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTH, 1);
//            s = "E";
        } else if (anInt == Constants.BTC) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTC, 1);
//            s = "B";
        } else if (anInt == Constants.USD) {
//            s = "$";
        } else if (anInt == Constants.USDT) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_Usdt, 1);

//            s = "$";
        } else if (anInt == Constants.USDT) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_Usdt, 1);
        }

        amount = amount * rate;
        Log.e("sputils", "formatAmount time:" + (System.currentTimeMillis() - l));
        if (Math.abs(amount) >= BILLION) {
            return s + keep2Decimal(amount / BILLION) + BILLION_UNIT;
        } else if (Math.abs(amount) >= MILLION) {
            return s + keep2Decimal(amount / MILLION) + MILLION_UNIT;
        } else {
            return s + keep2Decimal(amount);
        }

    }

    public static String formatValue(double value) {
        long l = System.currentTimeMillis();
        if (value == d) {
            return "--";
        }
        if (Math.abs(value) >= BILLION) {
            return keep2Decimal(value / BILLION) + BILLION_UNIT;
        } else if (Math.abs(value) >= MILLION) {
            return keep2Decimal(value / MILLION) + MILLION_UNIT;
        } else {
            return keep2Decimal(value);
        }
    }

    /**
     * 处理价格 显示规则
     * <p>
     * 1,000≤number 	保留2位小数
     * 1≤number＜1,000 	保留4位小数
     * number＜1 	    保留7位小数
     */
    public static String formatPrice(double price) {
        long l = System.currentTimeMillis();

        if (price == d) {
            return "--";
        }

        int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
        double rate = 1;
        String s = "";
        if (anInt == Constants.CNY) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_CNY, 1);
//            s = "￥";
        } else if (anInt == Constants.ETH) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTH, 1);
//            s = "E";
        } else if (anInt == Constants.BTC) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTC, 1);
//            s = "B";
        } else if (anInt == Constants.USDT) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_Usdt, 1);

//            s = "$";
        } else if (anInt == Constants.USD) {
//            s = "$";
        }
        price = price * rate;
        Log.e("sputils", "formatPrice time:" + (System.currentTimeMillis() - l));
        if (Math.abs(price) >= MILLION) {
            return s + keep2Decimal(price);
        } else if (Math.abs(price) >= THOUSAND) {
            return s + keep2Decimal(price);
        } else if (Math.abs(price) >= 1) {
            return s + keep4Decimal(price);
        } else {
            return s + keep8Decimal(price);
        }
    }

    /**
     * 处理价格 显示规则
     * <p>
     * 1,000≤number 	保留2位小数
     * 1≤number＜1,000 	保留4位小数
     * number＜1 	    保留7位小数
     */
    public static String formatPriceWithUnitForward(double price) {
        long l = System.currentTimeMillis();

        if (price == d) {
            return "--";
        }

        int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
        double rate = 1;
        String s = "";
        if (anInt == Constants.CNY) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_CNY, 1);
            s = "￥";
        } else if (anInt == Constants.ETH) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTH, 1);
            s = "E";
        } else if (anInt == Constants.BTC) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTC, 1);
            s = "B";
        } else if (anInt == Constants.USD) {
            s = "$";
        } else if (anInt == Constants.USDT) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_Usdt, 1);
            s = "U";
        }
        price = price * rate;
        Log.e("sputils", "formatPrice time:" + (System.currentTimeMillis() - l));
        if (Math.abs(price) >= MILLION) {
            return s + keep2Decimal(price);
        } else if (Math.abs(price) >= THOUSAND) {
            return s + keep2Decimal(price);
        } else if (Math.abs(price) >= 1) {
            return s + keep4Decimal(price);
        } else {
            return s + keep8Decimal(price);
        }
    }

    public static String formatPriceWithUnitForwardNoRate(double price) {

        if (price == d) {
            return "--";
        }

        int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
        double rate = 1;
        String s = "";
        if (anInt == Constants.CNY) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_CNY, 1);
            s = "￥";
        } else if (anInt == Constants.ETH) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTH, 1);
            s = "E";
        } else if (anInt == Constants.BTC) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTC, 1);
            s = "B";
        } else if (anInt == Constants.USD) {
            s = "$";
        } else if (anInt == Constants.USDT) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_Usdt, 1);
            s = "U";
        }
        return s + price;
//        price = price*rate;
//        Log.e("sputils","formatPrice time:"+(System.currentTimeMillis()-l));
//        if(Math.abs(price)>=MILLION){
//            return s+keep2Decimal(price);
//        }else if (Math.abs(price)>=THOUSAND){
//            return s+keep2Decimal(price);
//        }else if (Math.abs(price) >= 1){
//            return s+keep4Decimal(price);
//        }else {
//            return s+keep8Decimal(price);
//        }
    }

    /**
     * 处理金额
     * <p>
     * 10,000≤number＜100,000,000 	已万作为单位，保留两位小数（例：23.45万）
     * 100,000,000≤number 	已亿作为单位，保留两位小数（例：23.45亿）
     */
    public static String formatAmountWithUnitForward(double amount) {
        long l = System.currentTimeMillis();
        if (amount == d) {
            return "--";
        }

        int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
        double rate = 1;
        String s = "";
        if (anInt == Constants.CNY) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_CNY, 1);
            s = "￥";
        } else if (anInt == Constants.ETH) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTH, 1);
            s = "E";
        } else if (anInt == Constants.BTC) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTC, 1);
            s = "B";
        } else if (anInt == Constants.USD) {
            s = "$";
        } else if (anInt == Constants.USDT) {
            rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_Usdt, 1);
        }
        amount = amount * rate;
        Log.e("sputils", "formatAmount time:" + (System.currentTimeMillis() - l));
        if (Math.abs(amount) >= BILLION) {
            return s + keep2Decimal(amount / BILLION) + BILLION_UNIT;
        } else if (Math.abs(amount) >= MILLION) {
            return s + keep2Decimal(amount / MILLION) + MILLION_UNIT;
        } else {
            return s + keep2Decimal(amount);
        }

    }


    /**
     * 保留几位小数
     */
    public static String formatNumber(double d, int number) {
        if (number == d) {
            return "--";
        }

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(number);
        nf.setRoundingMode(RoundingMode.UP);
        return nf.format(d);
    }

    /**
     * 保留两位小数
     */
    public static String keep2Decimal(double num) {

        if (num == d) {
            return "--";
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        return subZeroAndDot(df.format(num));
    }

    /**
     * 保留两位小数
     */
    public static String keep2DecimalThousandthCharacter(double num) {

        if (num == d) {
            return "--";
        }
        DecimalFormat df = new DecimalFormat(",###,##0.00");
        return subZeroAndDot(df.format(num));
    }

    /**
     * 保留四位小数
     */
    public static String keep4Decimal(double num) {
        if (num == d) {
            return "--";
        }

        DecimalFormat df = new DecimalFormat("#0.0000");
        return subZeroAndDot(df.format(num));
    }

    /**
     * 保留四位小数
     */
    public static String keep4DecimalThousandthCharacter(double num) {
        if (num == d) {
            return "--";
        }

        DecimalFormat df = new DecimalFormat(",###,##0.0000");
        return subZeroAndDot(df.format(num));
    }

    /**
     * 保留七位小数
     */
    public static String keep8Decimal(double num) {

        if (num == d) {
            return "--";
        }
        DecimalFormat df = new DecimalFormat("#0.00000000");
        return subZeroAndDot(df.format(num));
    }

    /**
     * 保留七位小数
     */
    public static String keep8DecimalThousandthCharacter(double num) {

        if (num == d) {
            return "--";
        }
        DecimalFormat df = new DecimalFormat(",###,##0.00000000");
        return subZeroAndDot(df.format(num));
    }

    /**
     * 处理百分数 保留两位小数
     */
    public static String formatPercent(double d) {
        return keep2Decimal(d) + "%";
    }

    /**
     * 结尾 加单位

     */

//    public static SpannableStringBuilder formateStringEndWithUnit(String name){
//        String uname = "";
//        int anInt = SpUtil.getInt(Utils.getApp(), Constant.RATE_CHOICE, 0);
//        if (anInt==Constant.CNY){
//            uname = " CNY";
//        }else if (anInt==Constant.USD){
//            uname = " USD";
//        }else if (anInt==Constant.BTC){
//            uname = " BTC";
//        }else if (anInt==Constant.ETH){
//            uname = " ETH";
//        }else if (anInt==Constant.USDT){
//            uname = " USDT";
//        }
//        String str = name + uname;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }

    /**
     * 结尾 加单位
     */

//    public static SpannableStringBuilder formateStringEndWithUnitTool(String name){
//        String uname = "";
//        int anInt = SpUtil.getInt(Utils.getApp(), Constant.RATE_CHOICE, 0);
//        if (anInt==Constant.CNY){
//            uname = " CNY";
//        }else if (anInt==Constant.USD){
//            uname = " USDT";
//        }else if (anInt==Constant.BTC){
//            uname = " BTC";
//        }else if (anInt==Constant.ETH){
//            uname = " ETH";
//        }else if (anInt==Constant.USDT){
//            uname = " USDT";
//        }
//        String str = name + uname;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }
    public static SpannableStringBuilder formateStringEndWithUnit(String name, int color) {
        String uname = "";
        int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
        if (anInt == Constants.CNY) {
            uname = " CNY";
        } else if (anInt == Constants.USD) {
            uname = " USD";
        } else if (anInt == Constants.BTC) {
            uname = " BTC";
        } else if (anInt == Constants.ETH) {
            uname = " ETH";
        } else if (anInt == Constants.USDT) {
            uname = " USDT";
        }
        String str = name + uname;
        final SpannableStringBuilder sp = new SpannableStringBuilder(str);
        sp.setSpan(new ForegroundColorSpan(color), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        return sp;
    }

    /**
     * 结尾 加单位
     */

    public static String formateStringEndWithUnit1(String name) {
        String uname = "";
        int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
        if (anInt == Constants.CNY) {
            uname = " CNY";
        } else if (anInt == Constants.USD) {
            uname = " USD";
        } else if (anInt == Constants.BTC) {
            uname = " BTC";
        } else if (anInt == Constants.ETH) {
            uname = " ETH";
        } else if (anInt == Constants.USDT) {
            uname = " USDT";
        }
        //        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        return name + uname;
    }

    /**
     * 结尾 加单位
     */

    public static String formateStringEndWithUnit1Tool(String name) {
        String uname = "";
        int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
        if (anInt == Constants.CNY) {
            uname = " CNY";
        } else if (anInt == Constants.USD) {
            uname = " USDT";
        } else if (anInt == Constants.BTC) {
            uname = " BTC";
        } else if (anInt == Constants.ETH) {
            uname = " ETH";
        } else if (anInt == Constants.USDT) {
            uname = " USDT";
        }
        String str = name + uname;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        return str;
    }


    /**
     * 结尾 加单位 并换行
     */
//    public static SpannableStringBuilder formateStringEndWithUnitTab(String name){
//        String uname = "";
//        int anInt = SpUtil.getInt(Utils.getApp(), Constant.RATE_CHOICE, 0);
//        if (anInt==Constant.CNY){
//            uname = " CNY";
//        }else if (anInt==Constant.USD){
//            uname = " USD";
//        }else if (anInt==Constant.BTC){
//            uname = " BTC";
//        }else if (anInt==Constant.ETH){
//            uname = " ETH";
//        }else if (anInt==Constant.USDT){
//            uname = " USDT";
//        }
//        String str = name + "\n" +uname;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }

    /**
     * 结尾 加单位 并换行
     */
//    public static SpannableStringBuilder formateStringEndWithUnitTabTool(String name){
//        String uname = "";
//        int anInt = SpUtil.getInt(Utils.getApp(), Constant.RATE_CHOICE, 0);
//        if (anInt==Constant.CNY){
//            uname = " CNY";
//        }else if (anInt==Constant.USD){
//            uname = " USDT";
//        }else if (anInt==Constant.BTC){
//            uname = " BTC";
//        }else if (anInt==Constant.ETH){
//            uname = " ETH";
//        }else if (anInt==Constant.USDT){
//            uname = " USDT";
//        }
//        String str = name + "\n" +uname;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }


//    /**
//     * 结尾 加自定义单位
//     *
//     */
//    public static SpannableStringBuilder formateStringEndWithCustomUnit(String name,String end ){
//        String str = name  + end;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }

    /**
     * 结尾 加自定义单位
     */
    public static SpannableStringBuilder formateStringEndWithCustomUnit(String name, String end, int color) {
        String str = name + end;
        final SpannableStringBuilder sp = new SpannableStringBuilder(str);
        sp.setSpan(new ForegroundColorSpan(color), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        return sp;
    }

    /**
     * 结尾 加自定义单位 并换行
     *
     */
//    public static SpannableStringBuilder formateStringEndWithUnitTab(String name,String end ){
//        String str = name + "\n" + end;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }

    /**
     * 结尾加usdt

     */
//    public static SpannableStringBuilder formateStringEndWithUsdt(String name){
//        String uname = "";
//        uname = "(USDT)";
//        String str = name + uname;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }

    /**
     * 结尾加btc

     */
//    public static SpannableStringBuilder formateStringEndWithBtc(String name){
//        String uname = "";
//        uname = "(BTC)";
//        String str = name + uname;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }

    /**
     * 结尾加eth
     */
//    public static SpannableStringBuilder formateStringEndWithEth(String name){
//        String uname = "";
//        uname = "(ETH)";
//        String str = name + uname;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }

    /**
     *  结尾加cny
     */
//    public static SpannableStringBuilder formateStringEndWithCny(String name){
//        String uname = "";
//        uname = "(CNY)";
//        String str = name + uname;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }
    /**
     * 结尾加usdt

     */
//    public static SpannableStringBuilder formateStringEndWithUsdt1(String name){
//        String uname = "";
//        uname = " USDT";
//        String str = name + uname;
//
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }

    /**
     * 结尾加btc

     */
//    public static SpannableStringBuilder formateStringEndWithBtc1(String name){
//        String uname = "";
//        uname = " BTC";
//        String str = name + uname;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }

    /**
     * 结尾加eth
     */
//    public static SpannableStringBuilder formateStringEndWithEth1(String name){
//        String uname = "";
//        uname = " ETH";
//        String str = name + uname;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }

    /**
     *  结尾加cny
     */
//    public static SpannableStringBuilder formateStringEndWithCny1(String name){
//        String uname = "";
//        uname = " CNY";
//        String str = name + uname;
//        final SpannableStringBuilder sp = new  SpannableStringBuilder(str);
//        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Utils.getApp()()(),R.color.coinname_2)), name.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
//        sp.setSpan(new AbsoluteSizeSpan(7, true), name.length(), str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        return sp;
//    }

    /**
     * 价格换成btc
     */
    public static String formatPriceWithBtc(double price) {

        if (price == d) {
            return "--";
        }

        String s = "";
        double rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTC, 1);
        price = price * rate;
        if (Math.abs(price) >= MILLION) {
            return s + keep2DecimalThousandthCharacter(price / MILLION) + MILLION_UNIT;
        } else if (Math.abs(price) >= THOUSAND) {
            return s + keep2DecimalThousandthCharacter(price);
        } else if (Math.abs(price) >= 1) {
            return s + keep4DecimalThousandthCharacter(price);
        } else {
            return s + keep8DecimalThousandthCharacter(price);
        }
    }

    /**
     * 价格换成cny
     */
    public static String formatPriceWithCny(double price) {

        if (price == d) {
            return "--";
        }

        String s = "";
        double rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_CNY, 1);
        price = price * rate;
        if (Math.abs(price) >= MILLION) {
            return s + keep2DecimalThousandthCharacter(price / MILLION) + MILLION_UNIT;
        } else if (Math.abs(price) >= THOUSAND) {
            return s + keep2DecimalThousandthCharacter(price);
        } else if (Math.abs(price) >= 1) {
            return s + keep4DecimalThousandthCharacter(price);
        } else {
            return s + keep8DecimalThousandthCharacter(price);
        }
    }

    /**
     * 价格换成eth
     */
    public static String formatPriceWithEth(double price) {

        if (price == d) {
            return "--";
        }

        String s = "";
        double rate = SpUtil.getDouble(Utils.getApp(), Constants.RATE_BTH, 1);
        price = price * rate;
        if (Math.abs(price) >= MILLION) {
            return s + keep2DecimalThousandthCharacter(price / MILLION) + MILLION_UNIT;
        } else if (Math.abs(price) >= THOUSAND) {
            return s + keep2DecimalThousandthCharacter(price);
        } else if (Math.abs(price) >= 1) {
            return s + keep4DecimalThousandthCharacter(price);
        } else {
            return s + keep8DecimalThousandthCharacter(price);
        }
    }

    /**
     * 价格换成usd
     */
    public static String formatPriceWithUSD(double price) {

        if (price == d) {
            return "--";
        }
        String s = "";
        double rate = 1;
        price = price * rate;
        if (Math.abs(price) >= MILLION) {
            return s + keep2DecimalThousandthCharacter(price / MILLION) + MILLION_UNIT;
        } else if (Math.abs(price) >= THOUSAND) {
            return s + keep2DecimalThousandthCharacter(price);
        } else if (Math.abs(price) >= 1) {
            return s + keep4DecimalThousandthCharacter(price);
        } else {
            return s + keep8DecimalThousandthCharacter(price);
        }
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static String getCurrnetUnit() {
        String uname = "";
        int anInt = SpUtil.getInt(Utils.getApp(), Constants.RATE_CHOICE, 0);
        if (anInt == Constants.CNY) {
            uname = "CNY";
        } else if (anInt == Constants.USD) {
            uname = "USD";
        } else if (anInt == Constants.BTC) {
            uname = "BTC";
        } else if (anInt == Constants.ETH) {
            uname = "ETH";
        } else if (anInt == Constants.USDT) {
            uname = "USDT";
        }
        return uname;
    }
}


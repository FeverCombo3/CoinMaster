package com.yjq.coinmaster.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.Utils;
import com.kline.library.indicator.Indicator;


public class SpUtil {
    private static SharedPreferences sp;
    public static SharedPreferences.Editor edit;

    /**
     * 保存boolean信息的操作
     */
    public static void saveBoolean(Context context, String key, boolean value){
        if (sp == null) {
            sp = context.getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        //保存数据
        edit = sp.edit();
        edit.putBoolean(key, value).apply();
    }

    /**
     * 获取boolean信息的操作
     */
    public static boolean getBoolean(Context context,String key,boolean defValue){
        if (sp == null) {
            sp = context.getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }



    /**
     * 保存String信息的操作
     */
    public static void saveString(Context context,String key,String value){
        if (sp == null) {
            sp = context.getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        //保存数据
        edit = sp.edit();
        edit.putString(key, value).apply();
    }

    /**
     * 获取String信息的操作
     */
    public static String getString(Context context,String key,String defValue){
        if (sp == null) {
            sp = context.getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    /**
     * 保存String信息的操作
     */
    public static void saveLong(Context context,String key,long value){
        if (sp == null) {
            sp = context.getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        //保存数据
        edit = sp.edit();
        edit.putLong(key, value).apply();
    }

    /**
     * 获取String信息的操作
     */
    public static Long getLong(Context context,String key,long defValue){
        if (sp == null) {
            sp = context.getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        return sp.getLong(key, defValue);
    }

    /**
     * 保存String信息的操作
     */
    public static void saveInt(Context context,String key,int value){
        if (sp == null) {
            sp = context.getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        //保存数据
        edit = sp.edit();
        edit.putInt(key, value).apply();
    }

    /**
     * 获取String信息的操作
     */
    public static int getInt(Context context,String key,int defValue){
        if (sp == null) {
            sp = context.getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    /**
     * 保存String信息的操作
     */
    public static void saveDouble(Context context,String key,double value){
        if (sp == null) {
            sp = context.getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        //保存数据
        edit = sp.edit();
        edit.putFloat(key, (float) value).apply();
    }

    /**
     * 获取String信息的操作
     */
    public static Double getDouble(Context context,String key,double defValue){
        if (sp == null) {
            sp = context.getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        return (double) sp.getFloat(key, (float) defValue);
    }

    //Kline的周期
    private static String KLINE_CYCLE = "kline_cycle";
    //Kline的主图指标
    private static String KLINE_MAIN_INDICATOR = "main_indicator";
    //Kline的副图指标
    private static String KLINE_SUB_INDICATOR = "sub_indicator";
    //KlineTYpe
    private static String KLINE_TYPE = "kline_type";


    public static void setKlineCycle(int cycle){
        if (sp == null) {
            sp = Utils.getApp().getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        edit = sp.edit();
        edit.putInt(KLINE_CYCLE,cycle).apply();
    }

    public static int getKlineCycle(){
        if (sp == null) {
            sp = Utils.getApp().getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        return sp.getInt(KLINE_CYCLE,4);
    }

    public static void setMainIndicator(int index){
        if (sp == null) {
            sp = Utils.getApp().getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        edit = sp.edit();
        edit.putInt(KLINE_MAIN_INDICATOR,index).apply();
    }

    public static int getMainIndicator(){
        if (sp == null) {
            sp = Utils.getApp().getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        return sp.getInt(KLINE_MAIN_INDICATOR,Indicator.INDICATOR_INDEX_MA);
    }

    public static void setSubIndicator(int index){
        if (sp == null) {
            sp = Utils.getApp().getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        edit = sp.edit();
        edit.putInt(KLINE_SUB_INDICATOR,index).apply();
    }

    public static int getSubIndicator(){
        if (sp == null) {
            sp = Utils.getApp().getSharedPreferences("sptable", Context.MODE_PRIVATE);
        }
        return sp.getInt(KLINE_SUB_INDICATOR,Indicator.INDICATOR_INDEX_MACD);
    }


}

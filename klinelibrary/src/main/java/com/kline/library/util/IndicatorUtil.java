package com.kline.library.util;

import android.content.Context;

import com.kline.library.indicator.BOLL;
import com.kline.library.indicator.Indicator;
import com.kline.library.indicator.KDJ;
import com.kline.library.indicator.Kline;
import com.kline.library.indicator.MA;
import com.kline.library.indicator.MACD;
import com.kline.library.indicator.RSI;
import com.kline.library.indicator.VOL;
import com.kline.library.indicator.ZLCP;

/**
 * Created by yjq
 * 2018/6/19.
 */

public class IndicatorUtil {

    public static Indicator newIndicatorInstance(int index, Context context){
        switch (index){
            case Indicator.INDICATOR_INDEX_K:
                return new Kline(context);
            case Indicator.INDICATOR_INDEX_VOL:
                return new VOL(context);
            case Indicator.INDICATOR_INDEX_KDJ:
                return new KDJ(context);
            case Indicator.INDICATOR_INDEX_MACD:
                return new MACD(context);
            case Indicator.INDICATOR_INDEX_RSI:
                return new RSI(context);
            case Indicator.INDICATOR_INDEX_MA:
                return new MA(context);
            case Indicator.INDICATOR_INDEX_BOLL:
                return new BOLL(context);
            case Indicator.INDICATOR_INDEX_ZLCP:
                return new ZLCP(context);
        }
        return new MACD(context);
    }
}

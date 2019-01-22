package com.kline.library.indicator;


import com.kline.library.bean.CandleData;

/**
 * K线参数
 * Created by 25341 on 2017/5/7.
 */

public class KLineParam {

    public int index;
    public float l;
    public float r;
    public float yx;
    public CandleData candelData;

    @Override
    public String toString() {
        return "KLineParam{" +
                "index=" + index +
                ", l=" + l +
                ", r=" + r +
                ", yx=" + yx +
                ", candelData=" + candelData +
                '}';
    }
}

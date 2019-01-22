package com.kline.library.type;

/**
 * Created by yjq
 * 2018/5/22.
 * 周期
 */

public enum Cycle {
    CYCLE_FENSHI("fenshi",0,"分时"),
    CYCLE_5MIN("5min",1,"5分钟"),
    CYCLE_15MIN("15min",2,"15分钟"),
    CYCLE_30MIN("30min",3,"30分钟"),
    CYCLE_1H("1h",4,"1小时"),
    CYCLE_4H("4h",5,"4小时"),//4小时的火币API没有，他自己的APP上却有，真的很神奇
    CYCLE_1DAY("1d",6,"1天"),
    CYCLE_1MON("1m",7,"1月"),
    CYCLE_1WEEK("1w",8,"1周"),
    CYCLE_1YEAR("1y",9,"1年");//1年的火币API有，他自己的APP上却没有，也很神奇

    private String value;
    private int index;
    private String name;

    private Cycle(String value, int index, String name){
        this.value = value;
        this.index = index;
        this.name = name;
    }

    public String getValue(){
        return value;
    }

    public int getIndex(){
        return index;
    }

    public String getName(){
        return name;
    }

    public static Cycle getCycleFromIndex(int index){
        switch (index){
            case 0:
                return CYCLE_FENSHI;
            case 1:
                return CYCLE_5MIN;
            case 2:
                return CYCLE_15MIN;
            case 3:
                return CYCLE_30MIN;
            case 4:
                return CYCLE_1H;
            case 5:
                return CYCLE_4H;
            case 6:
                return CYCLE_1DAY;
            case 7:
                return CYCLE_1MON;
            case 8:
                return CYCLE_1WEEK;
            case 9:
                return CYCLE_1YEAR;
        }

        return CYCLE_FENSHI;
    }
}

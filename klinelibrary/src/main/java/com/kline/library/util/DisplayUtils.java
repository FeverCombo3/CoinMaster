package com.kline.library.util;

import android.content.res.Resources;

/**
 * Created by yjq
 * 2017/5/12.
 */

public class DisplayUtils {

    public static final float DENSITY;

    static {
        DENSITY = Resources.getSystem().getDisplayMetrics().density;
    }

    public DisplayUtils() {
    }

    public static int pixelToDip(int var0) {
        return (int)(((float)var0 - 0.5F) / DENSITY);
    }

    public static int dipToPixel(int var0) {
        return (int)((float)var0 * DENSITY + 0.5F);
    }

    public static int sp2px(float spValue) {
        return (int) (spValue * DENSITY + 0.5f);
    }

}

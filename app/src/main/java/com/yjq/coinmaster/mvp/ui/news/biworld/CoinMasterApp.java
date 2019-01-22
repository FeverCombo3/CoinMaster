package com.yjq.coinmaster.mvp.ui.news.biworld;

import android.app.Application;
import android.content.Context;

public class CoinMasterApp extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

    }


    public static Context getContext(){
        return mContext;
    }
}

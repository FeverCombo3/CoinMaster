package com.yjq.coinmaster.mvp.contract;

import com.blockin.rvadapter.bean.RcvSectionWrapper;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorld;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorldData;
import com.yjq.coinmaster.mvp.ui.news.biworld.BiWorldSection;

import java.util.List;

import io.reactivex.Observable;

public interface FlashMewsContract {

    interface Model extends IModel{
        Observable<List<BiWorld>> getBiWorldList(String timeStamp);
    }

    interface View extends IView{


        void refreshData(List<RcvSectionWrapper<BiWorldSection, BiWorldData>> list);

        void setData(List<RcvSectionWrapper<BiWorldSection, BiWorldData>> list);
    }
}

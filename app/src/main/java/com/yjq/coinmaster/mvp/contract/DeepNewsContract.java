package com.yjq.coinmaster.mvp.contract;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.yjq.coinmaster.bean.DeepNewsData;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface DeepNewsContract {

    interface Model extends IModel{
        Observable<List<DeepNewsData>> getDeepNewsList(RequestBody requestBody);
    }

    interface View extends IView{

        void refreshData(List<DeepNewsData> data);

        void setData(List<DeepNewsData> data);
    }

}

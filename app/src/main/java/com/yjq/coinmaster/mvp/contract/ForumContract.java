package com.yjq.coinmaster.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yjq.coinmaster.bean.EightBitPost;
import com.yjq.coinmaster.bean.Post;
import com.yjq.common.view.BaseUiView;

import java.util.List;

import io.reactivex.Observable;

public interface ForumContract {

    interface View extends BaseUiView{

        void refreshData(List<Post> posts);

        void setData(List<Post> posts);
    }

    interface Model extends IModel{
        Observable<List<Post>> getPostData(int page);
    }
}

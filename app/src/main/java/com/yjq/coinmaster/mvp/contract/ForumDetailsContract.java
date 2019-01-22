package com.yjq.coinmaster.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yjq.coinmaster.bean.Comment;
import com.yjq.coinmaster.bean.ForumDetail;

import java.util.List;

import io.reactivex.Observable;

public interface ForumDetailsContract {

    interface View extends IView{

        void setForumTitle(String title);

        void setContent(String content);

        void setComment(List<Comment> comments);
    }

    interface Model extends IModel{

        Observable<ForumDetail> getDetails(String url);
    }

}




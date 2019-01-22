package com.yjq.coinmaster.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yjq.coinmaster.bean.Comment;
import com.yjq.coinmaster.bean.ForumDetail;
import com.yjq.coinmaster.bean.Post;
import com.yjq.coinmaster.constant.Constants;
import com.yjq.coinmaster.mvp.contract.ForumContract;
import com.yjq.coinmaster.mvp.contract.ForumDetailsContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@ActivityScope
public class ForumDetailsModel extends BaseModel implements ForumDetailsContract.Model{

    @Inject
    Application mApplication;

    @Inject
    public ForumDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<ForumDetail> getDetails(String url) {
        return Observable.create(new ObservableOnSubscribe<ForumDetail>() {
            @Override
            public void subscribe(ObservableEmitter<ForumDetail> emitter) throws Exception {
                ForumDetail forumDetail = new ForumDetail();
                List<Comment> comments = new ArrayList<>();
                Element element  = Jsoup.connect(url)
                        .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36")
                        .get()
                        .body();
                forumDetail.title = element.getElementById("thread_subject").text();
                Elements elements = element.select("div.archy_pvl_box > div[id~=^post_]");
                for (Element ele : elements){
                    Elements vavatar = ele.getElementsByClass("post_vavatar");
                    String avatar = "";
                    if(vavatar.size() == 0){
                        Elements t_fsz11111 = element.getElementsByClass("t_fsz11111");
                        t_fsz11111.select("div.a_pr").remove();
                        forumDetail.content = t_fsz11111.html();
                        forumDetail.content = getNewContent(forumDetail.content);
                        continue;
                    }else {
                        avatar = ele.getElementsByClass("post_vavatar").get(0).getElementsByTag("img").attr("src");
                    }
                    String comment = ele.getElementsByClass("t_fsz11111").text();
                    String author = ele.getElementsByClass("post_user").text();
                    Comment c = new Comment();
                    c.author = author;
                    c.avatar = avatar;
                    c.comment = comment;
                    comments.add(c);
                }

                forumDetail.comments = comments;
                emitter.onNext(forumDetail);
            }
        }).flatMap(new Function<ForumDetail, ObservableSource<ForumDetail>>() {
            @Override
            public ObservableSource<ForumDetail> apply(ForumDetail forumDetail) throws Exception {
                return Observable.just(forumDetail);
            }
        });
    }

    private String getNewContent(String bodyHTML){
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>*{margin:0;padding:0;}img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}

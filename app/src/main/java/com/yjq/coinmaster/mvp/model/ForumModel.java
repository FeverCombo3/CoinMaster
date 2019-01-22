package com.yjq.coinmaster.mvp.model;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yjq.coinmaster.bean.Post;
import com.yjq.coinmaster.constant.Constants;
import com.yjq.coinmaster.mvp.contract.ForumContract;

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
public class ForumModel extends BaseModel implements ForumContract.Model{

    @Inject
    Application mApplication;

    @Inject
    public ForumModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<Post>> getPostData(int page) {
        return Observable.create(new ObservableOnSubscribe<List<Post>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Post>> emitter) throws Exception {
                String url = "https://www.cwb.org.cn/forum.php?mod=forumdisplay&fid=36&page=" + page + "&t=3162055";
                Element element  = Jsoup.connect(url)
                        .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36")
                        .get()
                        .body();
                Elements discuz_list = element.select("tbody[id~=^normalthread]");
                List<Post> posts = new ArrayList<>();
                for (Element ele : discuz_list){
                    Post post = new Post();
                    String avatar = ele.getElementsByClass("zb7com_avatar").get(0).getElementsByTag("img").attr("src");
                    String title = ele.getElementsByClass("zb7com_ftitle").text();
                    String author = ele.getElementsByClass("zb7com_fauthor").text();
                    String directUrl = ele.getElementsByClass("zb7com_ftitle").attr("href");
                    String date = ele.getElementsByClass("zb7com_fother").text();
                    String comment = ele.getElementsByClass("zb7_freplies").text();
                    String watch = ele.getElementsByClass("zb7_fviews").text();
                    post.avatar = avatar;
                    post.title = title;
                    post.author = author;
                    post.date = date;
                    post.comments = comment;
                    post.watch = watch;
                    post.url = Constants.FORUM_DETAIL_HEADER + directUrl;

                    posts.add(post);
                }
                emitter.onNext(posts);
            }
        }).flatMap(new Function<List<Post>, ObservableSource<List<Post>>>() {
            @Override
            public ObservableSource<List<Post>> apply(List<Post> posts) throws Exception {
                return Observable.just(posts);
            }
        });
    }

}

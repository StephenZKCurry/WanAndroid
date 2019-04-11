package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zk.wanandroid.net.api.ApiService;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.net.rx.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * @description: 我的收藏Model
 * @author: zhukai
 * @date: 2018/3/14 9:38
 */
public class MyCollectionModel implements MyCollectionContract.IMyCollectionModel {

    @NonNull
    public static MyCollectionModel newInstance() {
        return new MyCollectionModel();
    }

    /**
     * 获取收藏文章列表
     *
     * @param page 页码，从0开始
     * @return
     */
    @Override
    public Observable<DataResponse<Article>> loadMyCollection(int page) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getMyCollection(page)
                .compose(RxSchedulers.<DataResponse<Article>>applySchedulers());
    }

    /**
     * 取消收藏文章
     *
     * @param article 取消收藏的文章
     * @return
     */
    @Override
    public Observable<DataResponse> cancelCollectArticle(Article.DatasBean article) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .removeMyCollectArticle(article.getId(), article.getOriginId())
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }

    /**
     * 收藏站外网站
     *
     * @param title  标题
     * @param author 作者
     * @param link   链接地址
     * @return
     */
    @Override
    public Observable<DataResponse> addOutsideCollectArticle(final String title, final String author, final String link) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .addOutsideCollectArticle(title, author, link)
                .filter(new Predicate<DataResponse>() {
                    @Override
                    public boolean test(DataResponse dataResponse) throws Exception {
                        return !TextUtils.isEmpty(title) && !TextUtils.isEmpty(author)
                                && !TextUtils.isEmpty(link);
                    }
                })
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }
}

package com.zk.wanandroid.ui.article;

import android.support.annotation.NonNull;

import com.zk.wanandroid.net.api.ApiService;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.net.rx.RxSchedulers;

import io.reactivex.Observable;

/**
 * @description: 知识体系详情Model
 * @author: zhukai
 * @date: 2018/3/11 11:53
 */
public class ArticleTypeModel implements ArticleTypeContract.IArticleTypeModel {

    @NonNull
    public static ArticleTypeModel newInstance() {
        return new ArticleTypeModel();
    }

    /**
     * 获取知识体系文章列表
     *
     * @param cid  分类的id，即知识体系二级目录的id
     * @param page 页码，从0开始
     * @return
     */
    @Override
    public Observable<DataResponse<Article>> loadKnowledgesystemArticles(int cid, int page) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getKnowledgeSystemsArticles(page, cid)
                .compose(RxSchedulers.<DataResponse<Article>>applySchedulers());
    }

    /**
     * 收藏文章
     *
     * @param id 文章id
     * @return
     */
    @Override
    public Observable<DataResponse> collectArticle(int id) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .addCollectArticle(id)
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }

    /**
     * 取消收藏文章
     *
     * @param id 文章id
     * @return
     */
    @Override
    public Observable<DataResponse> cancelCollectArticle(int id) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .removeCollectArticle(id)
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }
}

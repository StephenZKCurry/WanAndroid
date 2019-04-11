package com.zk.wanandroid.ui.home;

import android.support.annotation.NonNull;

import com.zk.wanandroid.net.api.ApiService;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.HomeBanner;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.net.rx.RxSchedulers;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 首页Model
 * @author: zhukai
 * @date: 2018/3/5 13:21
 */
public class HomeModel implements HomeContract.IHomeModel {

    @NonNull
    public static HomeModel newInstance() {
        return new HomeModel();
    }

    /**
     * 获取首页banner数据
     *
     * @return
     */
    @Override
    public Observable<DataResponse<List<HomeBanner>>> loadHomeBanner() {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getHomeBanners()
                .compose(RxSchedulers.<DataResponse<List<HomeBanner>>>applySchedulers());
    }

    /**
     * 获取首页文章列表
     *
     * @param page 页码，从0开始
     * @return
     */
    @Override
    public Observable<DataResponse<Article>> loadHomeArticles(int page) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getHomeArticles(page)
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

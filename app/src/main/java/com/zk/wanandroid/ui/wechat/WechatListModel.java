package com.zk.wanandroid.ui.wechat;

import android.support.annotation.NonNull;

import com.zk.wanandroid.net.api.ApiService;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.net.rx.RxSchedulers;

import io.reactivex.Observable;

/**
 * @description: 公众号文章列表Model
 * @author: zhukai
 * @date: 2019/4/10 15:17
 */
public class WechatListModel implements WechatListContract.IWechatListModel {

    @NonNull
    public static WechatListModel newInstance() {
        return new WechatListModel();
    }

    /**
     * 获取公众号文章列表
     *
     * @param id   公众号id
     * @param page 页码，从1开始
     * @return
     */
    @Override
    public Observable<DataResponse<Article>> loadWechatArticle(int id, int page) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getWechatArticle(id, page)
                .compose(RxSchedulers.<DataResponse<Article>>applySchedulers());
    }

    /**
     * 搜索公众号文章
     *
     * @param id   公众号id
     * @param page 页码，从1开始
     * @param key
     * @return 搜索条件
     */
    @Override
    public Observable<DataResponse<Article>> searchWechatArticle(int id, int page, String key) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .searchWechatArticle(id, page, key)
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

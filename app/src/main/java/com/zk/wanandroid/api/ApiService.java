package com.zk.wanandroid.api;

import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.HomeBanner;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @description: Retrofit请求接口
 * @author: zhukai
 * @date: 2018/3/5 13:34
 */
public interface ApiService {

    /**
     * 首页文章列表
     * http://www.wanandroid.com/article/list/0/json
     *
     * @param page 页码，从0开始
     * @return
     */
    @GET("/article/list/{page}/json")
    Observable<DataResponse<Article>> getHomeArticles(@Path("page") int page);

    /**
     * 首页banner
     * http://www.wanandroid.com/banner/json
     *
     * @return
     */
    @GET("/banner/json")
    Observable<DataResponse<List<HomeBanner>>> getHomeBanners();
}

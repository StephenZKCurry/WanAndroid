package com.zk.wanandroid.net.api;

import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.Bookmark;
import com.zk.wanandroid.bean.CommonWeb;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.HomeBanner;
import com.zk.wanandroid.bean.KnowledgeSystem;
import com.zk.wanandroid.bean.Navigation;
import com.zk.wanandroid.bean.Project;
import com.zk.wanandroid.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @description: Retrofit请求接口
 * @author: zhukai
 * @date: 2018/3/5 13:34
 */
public interface ApiService {

    /**
     * 首页文章列表
     * https://www.wanandroid.com/article/list/0/json
     *
     * @param page 页码，从0开始
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<DataResponse<Article>> getHomeArticles(@Path("page") int page);

    /**
     * 首页banner
     * https://www.wanandroid.com/banner/json
     *
     * @return
     */
    @GET("banner/json")
    Observable<DataResponse<List<HomeBanner>>> getHomeBanners();

    /**
     * 常用网站
     * https://www.wanandroid.com/friend/json
     *
     * @return
     */
    @GET("friend/json")
    Observable<DataResponse<List<CommonWeb>>> getCommonWeb();

    /**
     * 搜索热词
     * https://www.wanandroid.com/hotkey/json
     *
     * @return
     */
    @GET("hotkey/json")
    Observable<DataResponse<List<CommonWeb>>> getHotSearch();

    /**
     * 搜索文章
     * https://www.wanandroid.com/article/query/0/json
     *
     * @param page 页码，从0开始
     * @param key  搜索关键词
     * @return
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<DataResponse<Article>> getSearchArticles(@Path("page") int page, @Field("k") String key);

    /**
     * 知识体系
     * https://www.wanandroid.com/tree/json
     *
     * @return
     */
    @GET("tree/json")
    Observable<DataResponse<List<KnowledgeSystem>>> getKnowledgeSystems();

    /**
     * 知识体系下的文章
     * https://www.wanandroid.com/article/list/0/json?cid=60
     *
     * @param page 页码，从0开始
     * @param cid  分类的id，即知识体系二级目录的id
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<DataResponse<Article>> getKnowledgeSystemsArticles(@Path("page") int page, @Query("cid") int cid);

    /**
     * 导航数据
     * https://www.wanandroid.com/navi/json
     *
     * @return
     */
    @GET("navi/json")
    Observable<DataResponse<List<Navigation>>> getNavigation();

    /**
     * 项目分类
     * https://www.wanandroid.com/project/tree/json
     *
     * @return
     */
    @GET("project/tree/json")
    Observable<DataResponse<List<KnowledgeSystem>>> getProjectType();

    /**
     * 某一个分类下的项目列表
     * https://www.wanandroid.com/project/list/1/json?cid=294
     *
     * @param page 页码，从1开始
     * @param cid  项目分类的id
     * @return
     */
    @GET("project/list/{page}/json")
    Observable<DataResponse<Project>> getProjectList(@Path("page") int page, @Query("cid") int cid);

    /**
     * 登录
     * https://www.wanandroid.com/user/login
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<DataResponse<User>> doLogin(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     * https://www.wanandroid.com/user/register
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     * @return
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<DataResponse<User>> doRegister(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 收藏文章列表
     * https://www.wanandroid.com/lg/collect/list/0/json
     *
     * @param page 页码，从0开始
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    Observable<DataResponse<Article>> getMyCollection(@Path("page") int page);

    /**
     * 收藏站内文章
     * https://www.wanandroid.com/lg/collect/1165/json
     *
     * @param id 文章id
     * @return
     */
    @POST("lg/collect/{id}/json")
    Observable<DataResponse> addCollectArticle(@Path("id") int id);

    /**
     * 收藏站外文章
     * https://www.wanandroid.com/lg/collect/add/json
     *
     * @param title
     * @param author
     * @param link
     * @return
     */
    @POST("lg/collect/add/json")
    @FormUrlEncoded
    Observable<DataResponse> addOutsideCollectArticle(@Field("title") String title, @Field("author") String author, @Field("link") String link);

    /**
     * 文章列表取消收藏
     * https://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param id 文章id
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<DataResponse> removeCollectArticle(@Path("id") int id);

    /**
     * 我的收藏取消收藏
     * https://www.wanandroid.com/lg/uncollect/2805/json
     *
     * @param id
     * @return
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<DataResponse> removeMyCollectArticle(@Path("id") int id, @Field("originId") int originId);

    /**
     * 收藏网站列表
     * https://www.wanandroid.com/lg/collect/usertools/json
     *
     * @return
     */
    @GET("lg/collect/usertools/json")
    Observable<DataResponse<List<Bookmark>>> getMyBookmark();

    /**
     * 收藏网站
     * https://www.wanandroid.com/lg/collect/addtool/json
     *
     * @param name 网站名
     * @param link 链接地址
     * @return
     */
    @POST("lg/collect/addtool/json")
    @FormUrlEncoded
    Observable<DataResponse> addBookmark(@Field("name") String name, @Field("link") String link);

    /**
     * 编辑收藏网站
     * https://www.wanandroid.com/lg/collect/updatetool/json
     *
     * @param id   书签id
     * @param name 网站名
     * @param link 链接地址
     * @return
     */
    @POST("lg/collect/updatetool/json")
    @FormUrlEncoded
    Observable<DataResponse> editBookmark(@Field("id") int id, @Field("name") String name, @Field("link") String link);

    /**
     * 删除收藏网站
     * https://www.wanandroid.com/lg/collect/deletetool/json
     *
     * @param id 书签id
     * @return
     */
    @POST("lg/collect/deletetool/json")
    @FormUrlEncoded
    Observable<DataResponse> deleteBookmark(@Field("id") int id);

    /**
     * 获取公众号列表
     * https://wanandroid.com/wxarticle/chapters/json
     *
     * @return
     */
    @GET("/wxarticle/chapters/json")
    Observable<DataResponse<List<KnowledgeSystem>>> getWechat();

    /**
     * 查看某个公众号历史数据
     * https://wanandroid.com/wxarticle/list/408/1/json
     *
     * @param id   公众号id
     * @param page 公众号页码，从1开始
     * @return
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<DataResponse<Article>> getWechatArticle(@Path("id") int id, @Path("page") int page);

    /**
     * 在某个公众号中搜索历史文章
     * https://wanandroid.com/wxarticle/list/405/1/json?k=Java
     *
     * @param id   公众号id
     * @param page 公众号页码，从1开始
     * @param key  搜索条件
     * @return
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<DataResponse<Article>> searchWechatArticle(@Path("id") int id, @Path("page") int page, @Query("k") String key);

    /**
     * 获取个人积分
     * https://www.wanandroid.com/lg/coin/getcount/json
     *
     * @return
     */
    @GET("lg/coin/getcount/json")
    Observable<DataResponse> getMyPoint();
}

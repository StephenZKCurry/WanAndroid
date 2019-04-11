package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zk.wanandroid.net.api.ApiService;
import com.zk.wanandroid.bean.Bookmark;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.net.rx.RxSchedulers;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * @description: 我的书签Model
 * @author: zhukai
 * @date: 2018/3/14 17:33
 */
public class MyBookmarkModel implements MyBookmarkContract.IMyBookmarkModel {

    @NonNull
    public static MyBookmarkModel newInstance() {
        return new MyBookmarkModel();
    }

    /**
     * 获取收藏网站列表
     *
     * @return
     */
    @Override
    public Observable<DataResponse<List<Bookmark>>> loadMyBookmark() {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getMyBookmark()
                .compose(RxSchedulers.<DataResponse<List<Bookmark>>>applySchedulers());
    }

    /**
     * 收藏网站
     *
     * @param name 网站名
     * @param link 链接地址
     * @return
     */
    @Override
    public Observable<DataResponse> addBookmark(final String name, final String link) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .addBookmark(name, link)
                .filter(new Predicate<DataResponse>() {
                    @Override
                    public boolean test(DataResponse dataResponse) throws Exception {
                        return !TextUtils.isEmpty(name) && !TextUtils.isEmpty(link);
                    }
                })
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }

    /**
     * 编辑书签
     *
     * @param id   书签id
     * @param name 网站名
     * @param link 链接地址
     * @return
     */
    @Override
    public Observable<DataResponse> editBookmark(int id, String name, String link) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .editBookmark(id, name, link)
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }

    /**
     * 删除书签
     *
     * @param id 书签id
     * @return
     */
    @Override
    public Observable<DataResponse> deleteBookmark(int id) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .deleteBookmark(id)
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }
}

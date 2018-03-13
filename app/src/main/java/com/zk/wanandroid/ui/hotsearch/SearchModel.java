package com.zk.wanandroid.ui.hotsearch;

import android.support.annotation.NonNull;

import com.zk.wanandroid.api.ApiService;
import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.CommonWeb;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.db.SearchHistory;
import com.zk.wanandroid.db.SearchHistoryDao;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.RxSchedulers;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @description: 搜索文章Model
 * @author: zhukai
 * @date: 2018/3/7 19:08
 */
public class SearchModel implements SearchContract.ISearchModel {

    private SearchHistoryDao searchHistoryDao = App.getDaoSession().getSearchHistoryDao();

    @NonNull
    public static SearchModel newInstance() {
        return new SearchModel();
    }

    /**
     * 获取搜索热词
     *
     * @return
     */
    @Override
    public Observable<DataResponse<List<CommonWeb>>> loadHotSearch() {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getHotSearch()
                .compose(RxSchedulers.<DataResponse<List<CommonWeb>>>applySchedulers());
    }

    /**
     * 获取搜索文章列表
     *
     * @param key  搜索关键词
     * @param page 页码，从0开始
     */
    @Override
    public Observable<DataResponse<Article>> loadSearchArticles(String key, int page) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getSearchArticles(page, key)
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

    /**
     * 添加搜索记录到数据库
     *
     * @param name
     */
    @Override
    public Observable<SearchHistory> addHistory(final String name) {
        return Observable.create(new ObservableOnSubscribe<SearchHistory>() {
            @Override
            public void subscribe(ObservableEmitter<SearchHistory> e) throws Exception {
                SearchHistory searchHistory;
                // 查询是否存在该条搜索记录
                List<SearchHistory> searchHistories = searchHistoryDao.queryBuilder()
                        .where(SearchHistoryDao.Properties.Name.eq(name)).list();
                if (searchHistories.size() == 0) {
                    // 创建要添加的实体对象
                    searchHistory = new SearchHistory();
                    searchHistory.setName(name);
                    searchHistory.setDate(new Date());
                    // 添加到数据库
                    long id = searchHistoryDao.insert(searchHistory);
                    if (id > 0) {
                        e.onNext(searchHistory);
                    } else {
                        e.onError(new Throwable("添加搜索历史失败"));
                    }
                } else {
                    // 更新数据
                    searchHistory = searchHistories.get(0);
                    searchHistory.setDate(new Date());
                    searchHistoryDao.update(searchHistory);
                    e.onNext(searchHistory);
                }
            }
        }).compose(RxSchedulers.<SearchHistory>applySchedulers());
    }

    /**
     * 查询搜索历史
     *
     * @return
     */
    @Override
    public Observable<List<SearchHistory>> loadHistory() {
        return Observable.create(new ObservableOnSubscribe<List<SearchHistory>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SearchHistory>> e) throws Exception {
                List<SearchHistory> searchHistories =
                        searchHistoryDao.queryBuilder()
                                .orderDesc(SearchHistoryDao.Properties.Date).limit(10).list();
                e.onNext(searchHistories);
            }
        }).compose(RxSchedulers.<List<SearchHistory>>applySchedulers());
    }

    /**
     * 删除指定搜索历史
     *
     * @param key
     * @return
     */
    @Override
    public void deleteHistoryByKey(Long key) {
        searchHistoryDao.deleteByKey(key);
    }

    /**
     * 删除全部搜索历史
     */
    @Override
    public void deleteAllHistory() {
        searchHistoryDao.deleteAll();
    }
}

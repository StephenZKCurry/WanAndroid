package com.zk.wanandroid.ui.hotsearch;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.CommonWeb;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.db.SearchHistory;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 搜索文章Contract
 * @author: zhukai
 * @date: 2018/3/7 14:37
 */
public interface SearchContract {

    abstract class SearchPresenter extends BasePresenter<SearchContract.ISearchModel, SearchContract.ISearchView> {
        /**
         * 获取搜索热词
         */
        public abstract void loadHotSearch();

        /**
         * 获取搜索文章列表
         *
         * @param key  搜索关键词
         * @param page 页码，从0开始
         */
        public abstract void loadSearchArticles(String key, int page);

        /**
         * 下拉刷新数据
         */
        public abstract void refresh();

        /**
         * 上拉加载数据
         */
        public abstract void loadMore();

        /**
         * 添加搜索记录到数据库
         *
         * @param name
         */
        public abstract void addHistory(String name);

        /**
         * 查询搜索历史
         */
        public abstract void loadHistory();

        /**
         * 删除指定搜索历史
         *
         * @param key
         */
        public abstract void deleteHistoryByKey(Long key);

        /**
         * 删除全部搜索历史
         */
        public abstract void deleteAllHistory();
    }

    interface ISearchModel extends IBaseModel {
        /**
         * 获取搜索热词
         *
         * @return
         */
        Observable<DataResponse<List<CommonWeb>>> loadHotSearch();

        /**
         * 获取搜索文章列表
         *
         * @param key  搜索关键词
         * @param page 页码，从0开始
         */
        Observable<DataResponse<Article>> loadSearchArticles(String key, int page);

        /**
         * 添加搜索记录到数据库
         *
         * @param name
         */
        Observable<SearchHistory> addHistory(String name);

        /**
         * 查询搜索历史
         *
         * @return
         */
        Observable<List<SearchHistory>> loadHistory();

        /**
         * 删除指定搜索历史
         *
         * @param key
         * @return
         */
        void deleteHistoryByKey(Long key);

        /**
         * 删除全部搜索历史
         */
        void deleteAllHistory();
    }

    interface ISearchView extends IBaseView {
        /**
         * 显示搜索热词
         *
         * @param commonWebs
         */
        void setHotSearch(List<CommonWeb> commonWebs);

        /**
         * 显示搜索文章列表
         *
         * @param article
         * @param loadType 类型：刷新或加载更多
         */
        void showSearchArticle(Article article, int loadType);

        /**
         * 是否显示刷新view，这里使用SwipeRefreshLayout
         * 也可以使用IBaseView中的showProgressDialog()方法，以对话框形式刷新
         *
         * @param refresh
         */
        void showRefreshView(Boolean refresh);

        /**
         * 显示搜索历史
         *
         * @param searchHistories
         */
        void setHistory(List<SearchHistory> searchHistories);

        /**
         * 刷新搜索历史
         */
        void refreshHistory();
    }
}

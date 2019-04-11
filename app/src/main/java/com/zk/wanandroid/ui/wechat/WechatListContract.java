package com.zk.wanandroid.ui.wechat;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;

import io.reactivex.Observable;

/**
 * @description: 公众号文章列表Contract
 * @author: zhukai
 * @date: 2019/4/10 14:50
 */
public interface WechatListContract {

    abstract class WechatListPresenter extends BasePresenter<IWechatListModel, IWechatListView> {
        /**
         * 获取公众号文章列表
         *
         * @param id   公众号id
         * @param page 页码，从1开始
         */
        public abstract void loadWechatArticle(int id, int page);

        /**
         * 搜索公众号文章
         *
         * @param id   公众号id
         * @param page 页码，从1开始
         * @param key  搜索条件
         */
        public abstract void searchWechatArticle(int id, int page, String key);

        /**
         * 下拉刷新数据
         */
        public abstract void refresh();

        /**
         * 上拉加载数据
         */
        public abstract void loadMore();

        /**
         * 收藏文章
         *
         * @param position 文章在列表中的position，用于更新数据
         * @param article  收藏的文章
         */
        public abstract void collectArticle(int position, Article.DatasBean article);

        /**
         * 取消收藏文章
         *
         * @param position 文章在列表中的position，用于更新数据
         * @param article  取消收藏的文章
         */
        public abstract void cancelCollectArticle(int position, Article.DatasBean article);
    }

    interface IWechatListModel extends IBaseModel {
        /**
         * 获取公众号文章列表
         *
         * @param id   公众号id
         * @param page 页码，从1开始
         * @return
         */
        Observable<DataResponse<Article>> loadWechatArticle(int id, int page);

        /**
         * 搜索公众号文章
         *
         * @param id   公众号id
         * @param page 页码，从1开始
         * @param key
         * @return 搜索条件
         */
        Observable<DataResponse<Article>> searchWechatArticle(int id, int page, String key);

        /**
         * 收藏文章
         *
         * @param id 文章id
         * @return
         */
        Observable<DataResponse> collectArticle(int id);

        /**
         * 取消收藏文章
         *
         * @param id 文章id
         * @return
         */
        Observable<DataResponse> cancelCollectArticle(int id);
    }

    interface IWechatListView extends IBaseView {
        /**
         * 显示公众号文章列表
         *
         * @param article
         * @param loadType 类型：刷新或加载更多
         */
        void setArticleList(Article article, int loadType);

        /**
         * 是否显示刷新view，这里使用SwipeRefreshLayout
         * 也可以使用IBaseView中的showProgressDialog()方法，以对话框形式刷新
         *
         * @param refresh
         */
        void showRefreshView(Boolean refresh);

        /**
         * 收藏文章成功
         *
         * @param position 文章在列表中的position，用于更新数据
         * @param article  收藏的文章
         */
        void collectArticleSuccess(int position, Article.DatasBean article);

        /**
         * 取消收藏文章成功
         *
         * @param position 文章在列表中的position，用于更新数据
         * @param article  取消收藏的文章
         */
        void cancelCollectArticleSuccess(int position, Article.DatasBean article);
    }
}

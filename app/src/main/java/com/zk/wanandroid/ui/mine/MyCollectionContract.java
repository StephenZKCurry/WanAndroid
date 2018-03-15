package com.zk.wanandroid.ui.mine;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;

import io.reactivex.Observable;

/**
 * @description: 我的收藏Contract
 * @author: zhukai
 * @date: 2018/3/14 9:37
 */
public interface MyCollectionContract {

    abstract class MyCollectionPresenter extends BasePresenter<IMyCollectionModel, IMyCollectionView> {
        /**
         * 获取收藏文章列表
         *
         * @param page 页码，从0开始
         */
        public abstract void loadMyCollection(int page);

        /**
         * 下拉刷新数据
         */
        public abstract void refresh();

        /**
         * 上拉加载数据
         */
        public abstract void loadMore();

        /**
         * 取消收藏文章
         *
         * @param position 文章在列表中的position，用于更新数据
         * @param article  取消收藏的文章
         */
        public abstract void cancelCollectArticle(int position, Article.DatasBean article);

        /**
         * 收藏站外网站
         *
         * @param title  标题
         * @param author 作者
         * @param link   链接地址
         */
        public abstract void addOutsideCollectArticle(String title, String author, String link);
    }

    interface IMyCollectionModel extends IBaseModel {
        /**
         * 获取收藏文章列表
         *
         * @param page 页码，从0开始
         * @return
         */
        Observable<DataResponse<Article>> loadMyCollection(int page);

        /**
         * 取消收藏文章
         *
         * @param article 取消收藏的文章
         * @return
         */
        Observable<DataResponse> cancelCollectArticle(Article.DatasBean article);

        /**
         * 收藏站外网站
         *
         * @param title  标题
         * @param author 作者
         * @param link   链接地址
         * @return
         */
        Observable<DataResponse> addOutsideCollectArticle(String title, String author, String link);
    }

    interface IMyCollectionView extends IBaseView {
        /**
         * 显示我的收藏文章列表
         *
         * @param article
         * @param loadType 类型：刷新或加载更多
         */
        void setMyCollection(Article article, int loadType);

        /**
         * 是否显示刷新view，这里使用SwipeRefreshLayout
         * 也可以使用IBaseView中的showProgressDialog()方法，以对话框形式刷新
         *
         * @param refresh
         */
        void showRefreshView(Boolean refresh);

        /**
         * 取消收藏文章成功
         *
         * @param position 文章在列表中的position，用于更新数据
         */
        void cancelCollectArticleSuccess(int position);
    }
}

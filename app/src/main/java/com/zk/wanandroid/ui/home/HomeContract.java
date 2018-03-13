package com.zk.wanandroid.ui.home;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.HomeBanner;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 首页Contract
 * @author: zhukai
 * @date: 2018/3/5 13:15
 */
public interface HomeContract {

    abstract class HomePresenter extends BasePresenter<IHomeModel, IHomeView> {
        /**
         * 获取首页banner数据
         */
        public abstract void loadHomeBanner();

        /**
         * 获取首页文章列表
         *
         * @param page 页码，从0开始
         */
        public abstract void loadHomeArticles(int page);

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

    interface IHomeModel extends IBaseModel {
        /**
         * 获取首页banner数据
         *
         * @return
         */
        Observable<DataResponse<List<HomeBanner>>> loadHomeBanner();

        /**
         * 获取首页文章列表
         *
         * @param page 页码，从0开始
         * @return
         */
        Observable<DataResponse<Article>> loadHomeArticles(int page);

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

    interface IHomeView extends IBaseView {
        /**
         * 显示首页banner数据
         *
         * @param banners
         */
        void showHomeBanner(List<HomeBanner> banners);

        /**
         * 显示首页文章列表
         *
         * @param article
         * @param loadType 类型：刷新或加载更多
         */
        void showHomeArticle(Article article, int loadType);

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

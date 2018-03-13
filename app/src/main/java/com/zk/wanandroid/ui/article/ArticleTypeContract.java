package com.zk.wanandroid.ui.article;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 知识体系详情Contract
 * @author: zhukai
 * @date: 2018/3/11 10:45
 */
public interface ArticleTypeContract {

    abstract class ArticleTypePresenter extends BasePresenter<IArticleTypeModel, IArticleTypeView> {
        /**
         * 获取知识体系文章列表
         *
         * @param cid  分类的id，即知识体系二级目录的id
         * @param page 页码，从0开始
         */
        public abstract void loadKnowledgesystemArticles(int cid, int page);

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

    interface IArticleTypeModel extends IBaseModel {
        /**
         * 获取知识体系文章列表
         *
         * @param cid  分类的id，即知识体系二级目录的id
         * @param page 页码，从0开始
         * @return
         */
        Observable<DataResponse<Article>> loadKnowledgesystemArticles(int cid, int page);

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

    interface IArticleTypeView extends IBaseView {
        /**
         * 显示知识体系文章列表
         *
         * @param article
         * @param loadType 类型：刷新或加载更多
         */
        void setKnowledgesystemArticles(Article article,int loadType);

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

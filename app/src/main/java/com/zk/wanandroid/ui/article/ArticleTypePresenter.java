package com.zk.wanandroid.ui.article;

import android.support.annotation.NonNull;

import com.zk.wanandroid.R;
import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.net.rx.BaseObserver;
import com.zk.wanandroid.utils.Constant;

/**
 * @description: 知识体系详情Persenter
 * @author: zhukai
 * @date: 2018/3/11 11:52
 */
public class ArticleTypePresenter extends ArticleTypeContract.ArticleTypePresenter {

    private int mCid; // 分类的id
    private int mPage; // 当前页码

    @NonNull
    public static ArticleTypePresenter newInstance() {
        return new ArticleTypePresenter();
    }

    @Override
    public ArticleTypeContract.IArticleTypeModel getModel() {
        return ArticleTypeModel.newInstance();
    }

    /**
     * 下拉刷新数据
     */
    @Override
    public void refresh() {
        mPage = 0;
        loadKnowledgesystemArticles(mCid, mPage);
        mIView.showRefreshView(true);
    }

    /**
     * 上拉加载数据
     */
    @Override
    public void loadMore() {
        mPage += 1;
        loadKnowledgesystemArticles(mCid, mPage);
    }

    /**
     * 收藏文章
     *
     * @param position 文章在列表中的position，用于更新数据
     * @param article  收藏的文章
     */
    @Override
    public void collectArticle(final int position, final Article.DatasBean article) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.collectArticle(article.getId())
                .subscribeWith(new BaseObserver<DataResponse>(mIView) {
                    @Override
                    public void onSuccess(DataResponse dataResponse) {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 收藏成功
                            article.setCollect(true);
                            mIView.collectArticleSuccess(position, article);
                            mIView.showToast(App.getContext().getString(R.string.collection_success));
                        } else {
                            // 收藏失败
                            mIView.showToast(App.getContext().getString(R.string.collection_failed));
                        }
                    }
                }));
    }

    /**
     * 取消收藏文章
     *
     * @param position 文章在列表中的position，用于更新数据
     * @param article  取消收藏的文章
     */
    @Override
    public void cancelCollectArticle(final int position, final Article.DatasBean article) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.cancelCollectArticle(article.getId())
                .subscribeWith(new BaseObserver<DataResponse>(mIView) {
                    @Override
                    public void onSuccess(DataResponse dataResponse) {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 取消收藏成功
                            article.setCollect(false);
                            mIView.cancelCollectArticleSuccess(position, article);
                            mIView.showToast(App.getContext().getString(R.string.collection_cancel_success));
                        } else {
                            // 取消收藏失败
                            mIView.showToast(App.getContext().getString(R.string.collection_cancel_failed));
                        }
                    }
                }));
    }

    /**
     * 获取知识体系文章列表
     *
     * @param cid  分类的id，即知识体系二级目录的id
     * @param page 页码，从0开始
     */
    @Override
    public void loadKnowledgesystemArticles(int cid, final int page) {
        mCid = cid;
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.loadKnowledgesystemArticles(cid, page)
                .subscribeWith(new BaseObserver<DataResponse<Article>>(mIView) {
                    @Override
                    public void onSuccess(DataResponse<Article> dataResponse) {
                        if (page == 0) {
                            // 刷新
                            mIView.setKnowledgesystemArticles(dataResponse.getData(), Constant.TYPE_REFRESH_SUCCESS);
                        } else {
                            // 加载更多
                            mIView.setKnowledgesystemArticles(dataResponse.getData(), Constant.TYPE_LOAD_MORE_SUCCESS);
                        }
                        mIView.showRefreshView(false);
                    }

                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                        mIView.showRefreshView(false);
                    }
                }));
    }
}

package com.zk.wanandroid.ui.wechat;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zk.wanandroid.R;
import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.net.rx.BaseObserver;
import com.zk.wanandroid.utils.Constant;

/**
 * @description: 公众号文章列表Presenter
 * @author: zhukai
 * @date: 2019/4/10 15:21
 */
public class WechatListPresenter extends WechatListContract.WechatListPresenter {

    private int mId; // 公众号id
    private int mPage; // 当前页码
    private String mKey; // 搜索条件

    @NonNull
    public static WechatListPresenter newInstance() {
        return new WechatListPresenter();
    }

    @Override
    public WechatListContract.IWechatListModel getModel() {
        return WechatListModel.newInstance();
    }

    /**
     * 下拉刷新数据
     */
    @Override
    public void refresh() {
        mPage = 1;
        if (TextUtils.isEmpty(mKey)) {
            loadWechatArticle(mId, mPage);
        } else {
            searchWechatArticle(mId, mPage, mKey);
        }
        mIView.showRefreshView(true);
    }

    /**
     * 上拉加载数据
     */
    @Override
    public void loadMore() {
        mPage += 1;
        if (TextUtils.isEmpty(mKey)) {
            loadWechatArticle(mId, mPage);
        } else {
            searchWechatArticle(mId, mPage, mKey);
        }
    }

    /**
     * 获取公众号文章列表
     *
     * @param id   公众号id
     * @param page 页码，从1开始
     */
    @Override
    public void loadWechatArticle(int id, final int page) {
        mId = id;
        mKey = "";
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.loadWechatArticle(mId, mPage)
                .subscribeWith(new BaseObserver<DataResponse<Article>>(mIView) {
                    @Override
                    public void onSuccess(DataResponse<Article> dataResponse) {
                        if (page == 0) {
                            // 刷新
                            mIView.setArticleList(dataResponse.getData(), Constant.TYPE_REFRESH_SUCCESS);
                        } else {
                            // 加载更多
                            mIView.setArticleList(dataResponse.getData(), Constant.TYPE_LOAD_MORE_SUCCESS);
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

    /**
     * 搜索公众号文章
     *
     * @param id   公众号id
     * @param page 页码，从1开始
     * @param key  搜索条件
     */
    @Override
    public void searchWechatArticle(int id, final int page, String key) {
        mId = id;
        mKey = key;
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.searchWechatArticle(mId, mPage, mKey)
                .subscribeWith(new BaseObserver<DataResponse<Article>>(mIView) {
                    @Override
                    public void onSuccess(DataResponse<Article> dataResponse) {
                        if (page == 0) {
                            // 刷新
                            mIView.setArticleList(dataResponse.getData(), Constant.TYPE_REFRESH_SUCCESS);
                        } else {
                            // 加载更多
                            mIView.setArticleList(dataResponse.getData(), Constant.TYPE_LOAD_MORE_SUCCESS);
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
}

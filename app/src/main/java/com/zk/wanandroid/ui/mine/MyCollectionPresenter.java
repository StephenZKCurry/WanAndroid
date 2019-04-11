package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;

import com.zk.wanandroid.R;
import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.net.rx.BaseObserver;
import com.zk.wanandroid.utils.Constant;

/**
 * @description: 我的收藏Presenter
 * @author: zhukai
 * @date: 2018/3/14 9:38
 */
public class MyCollectionPresenter extends MyCollectionContract.MyCollectionPresenter {

    private int mPage; // 当前页码

    @NonNull
    public static MyCollectionPresenter newInstance() {
        return new MyCollectionPresenter();
    }

    @Override
    public MyCollectionContract.IMyCollectionModel getModel() {
        return MyCollectionModel.newInstance();
    }

    /**
     * 下拉刷新数据
     */
    @Override
    public void refresh() {
        mPage = 0;
        loadMyCollection(mPage);
        mIView.showRefreshView(true);
    }

    /**
     * 上拉加载数据
     */
    @Override
    public void loadMore() {
        mPage += 1;
        loadMyCollection(mPage);
    }

    /**
     * 获取收藏文章列表
     *
     * @param page 页码，从0开始
     */
    @Override
    public void loadMyCollection(final int page) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.loadMyCollection(page)
                .subscribeWith(new BaseObserver<DataResponse<Article>>(mIView) {
                    @Override
                    public void onSuccess(DataResponse<Article> dataResponse) {
                        if (page == 0) {
                            // 刷新
                            mIView.setMyCollection(dataResponse.getData(), Constant.TYPE_REFRESH_SUCCESS);
                        } else {
                            // 加载更多
                            mIView.setMyCollection(dataResponse.getData(), Constant.TYPE_LOAD_MORE_SUCCESS);
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
        mRxManager.register(mIModel.cancelCollectArticle(article)
                .subscribeWith(new BaseObserver<DataResponse>(mIView) {
                    @Override
                    public void onSuccess(DataResponse dataResponse) {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 取消收藏成功
                            mIView.cancelCollectArticleSuccess(position);
                            mIView.showToast(App.getContext().getString(R.string.collection_cancel_success));
                        } else {
                            // 取消收藏失败
                            mIView.showToast(App.getContext().getString(R.string.collection_cancel_failed));
                        }
                    }
                }));
    }

    /**
     * 收藏站外网站
     *
     * @param title  标题
     * @param author 作者
     * @param link   链接地址
     */
    @Override
    public void addOutsideCollectArticle(String title, String author, String link) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.addOutsideCollectArticle(title, author, link)
                .subscribeWith(new BaseObserver<DataResponse>(mIView) {
                    @Override
                    public void onSuccess(DataResponse dataResponse) {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 收藏成功
                            mIView.showToast(App.getContext().getString(R.string.collection_success));
                            refresh();
                        } else {
                            // 收藏失败
                            mIView.showToast(App.getContext().getString(R.string.collection_failed));
                        }
                    }
                }));
    }
}

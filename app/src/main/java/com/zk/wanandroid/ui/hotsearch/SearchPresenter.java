package com.zk.wanandroid.ui.hotsearch;

import android.support.annotation.NonNull;

import com.zk.wanandroid.R;
import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.CommonWeb;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.db.SearchHistory;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.NetworkUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @description: 搜索文章Presenter
 * @author: zhukai
 * @date: 2018/3/7 17:24
 */
public class SearchPresenter extends SearchContract.SearchPresenter {

    private String mKey; // 当前搜索关键词
    private int mPage; // 当前页码

    @NonNull
    public static SearchPresenter newInstance() {
        return new SearchPresenter();
    }

    @Override
    public SearchContract.ISearchModel getModel() {
        return SearchModel.newInstance();
    }

    @Override
    public void refresh() {
        mPage = 0;
        loadSearchArticles(mKey, mPage);
        mIView.showRefreshView(true);
    }

    @Override
    public void loadMore() {
        mPage += 1;
        loadSearchArticles(mKey, mPage);
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
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
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
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        boolean available = NetworkUtils.isAvailable(App.getContext());
                        if (!available) {
                            mIView.showNoNet();
                        } else {
                            mIView.showFaild(throwable.getMessage());
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
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
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
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        boolean available = NetworkUtils.isAvailable(App.getContext());
                        if (!available) {
                            mIView.showNoNet();
                        } else {
                            mIView.showFaild(throwable.getMessage());
                        }
                    }
                }));
    }

    /**
     * 添加搜索记录到数据库
     *
     * @param name
     */
    @Override
    public void addHistory(String name) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.addHistory(name)
                .subscribe(new Consumer<SearchHistory>() {
                    @Override
                    public void accept(SearchHistory searchHistory) throws Exception {
//                        mIView.addHistory(searchHistory);
                        mIView.refreshHistory();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // 添加搜索历史失败
//                        mIView.showToast(throwable.getMessage());
                    }
                }));
    }

    /**
     * 查询搜索历史
     */
    @Override
    public void loadHistory() {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.loadHistory()
                .subscribe(new Consumer<List<SearchHistory>>() {
                    @Override
                    public void accept(List<SearchHistory> searchHistories) throws Exception {
                        mIView.setHistory(searchHistories);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // 查询搜索历史失败
//                        mIView.showToast(throwable.getMessage());
                    }
                }));
    }

    /**
     * 获取搜索热词
     */
    @Override
    public void loadHotSearch() {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.loadHotSearch()
                .subscribe(new Consumer<DataResponse<List<CommonWeb>>>() {
                    @Override
                    public void accept(DataResponse<List<CommonWeb>> dataResponse) throws Exception {
                        mIView.setHotSearch(dataResponse.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        boolean available = NetworkUtils.isAvailable(App.getContext());
                        if (!available) {
                            mIView.showNoNet();
                        } else {
                            mIView.showFaild(throwable.getMessage());
                        }
                    }
                }));
    }

    /**
     * 获取搜索文章列表
     *
     * @param key  搜索关键词
     * @param page 页码，从0开始
     */
    @Override
    public void loadSearchArticles(String key, final int page) {
        if (mIView == null || mIModel == null) {
            return;
        }
        this.mKey = key;
        mRxManager.register(mIModel.loadSearchArticles(key, page)
                .subscribe(new Consumer<DataResponse<Article>>() {
                    @Override
                    public void accept(DataResponse<Article> dataResponse) throws Exception {
                        if (page == 0) {
                            // 刷新
                            mIView.showSearchArticle(dataResponse.getData(), Constant.TYPE_REFRESH_SUCCESS);
                        } else {
                            // 加载更多
                            mIView.showSearchArticle(dataResponse.getData(), Constant.TYPE_LOAD_MORE_SUCCESS);
                        }
                        mIView.showRefreshView(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        boolean available = NetworkUtils.isAvailable(App.getContext());
                        if (!available) {
                            mIView.showNoNet();
                        } else {
                            mIView.showFaild(throwable.getMessage());
                        }
                        mIView.showRefreshView(false);
                    }
                }));
    }

    /**
     * 删除指定搜索历史
     *
     * @param key
     */
    @Override
    public void deleteHistoryByKey(Long key) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mIModel.deleteHistoryByKey(key);
        mIView.refreshHistory();
    }

    /**
     * 删除全部搜索历史
     */
    @Override
    public void deleteAllHistory() {
        if (mIView == null || mIModel == null) {
            return;
        }
        mIModel.deleteAllHistory();
        mIView.refreshHistory();
    }
}

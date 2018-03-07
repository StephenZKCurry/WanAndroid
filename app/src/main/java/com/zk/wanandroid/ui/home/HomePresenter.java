package com.zk.wanandroid.ui.home;

import android.support.annotation.NonNull;

import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.HomeBanner;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.NetworkUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @description: 首页Presenter
 * @author: zhukai
 * @date: 2018/3/5 13:21
 */
public class HomePresenter extends HomeContract.HomePresenter {

    private int mPage; // 当前页码

    @NonNull
    public static HomePresenter newInstance() {
        return new HomePresenter();
    }

    @Override
    public HomeContract.IHomeModel getModel() {
        return HomeModel.newInstance();
    }

    @Override
    public void refresh() {
        mPage = 0;
        loadHomeArticles(mPage);
        mIView.showRefreshView(true);
    }

    @Override
    public void loadMore() {
        mPage += 1;
        loadHomeArticles(mPage);
    }

    /**
     * 获取首页banner数据
     */
    @Override
    public void loadHomeBanner() {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.loadHomeBanner()
                .subscribe(new Consumer<DataResponse<List<HomeBanner>>>() {
                    @Override
                    public void accept(DataResponse<List<HomeBanner>> dataResponse) throws Exception {
                        mIView.showHomeBanner(dataResponse.getData());
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
     * 获取首页文章列表
     *
     * @param page 页码，从0开始
     */
    @Override
    public void loadHomeArticles(final int page) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.loadHomeArticles(page)
                .subscribe(new Consumer<DataResponse<Article>>() {
                    @Override
                    public void accept(DataResponse<Article> dataResponse) throws Exception {
                        if (page == 0) {
                            // 刷新
                            mIView.showHomeArticle(dataResponse.getData(), Constant.TYPE_REFRESH_SUCCESS);
                        } else {
                            // 加载更多
                            mIView.showHomeArticle(dataResponse.getData(), Constant.TYPE_LOAD_MORE_SUCCESS);
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
}

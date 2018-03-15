package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;

import com.zk.wanandroid.R;
import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.Bookmark;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.NetworkUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @description: 我的书签Presenter
 * @author: zhukai
 * @date: 2018/3/14 17:34
 */
public class MyBookmarkPresenter extends MyBookmarkContract.MyBookmarkPresenter {

    @NonNull
    public static MyBookmarkPresenter newInstance() {
        return new MyBookmarkPresenter();
    }

    @Override
    public MyBookmarkContract.IMyBookmarkModel getModel() {
        return MyBookmarkModel.newInstance();
    }

    @Override
    public void refresh() {
        loadMyBookmark();
    }

    /**
     * 获取收藏网站列表
     */
    @Override
    public void loadMyBookmark() {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.loadMyBookmark()
                .subscribe(new Consumer<DataResponse<List<Bookmark>>>() {
                    @Override
                    public void accept(DataResponse<List<Bookmark>> dataResponse) throws Exception {
                        mIView.setMyBookmark(dataResponse.getData());
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
     * 收藏网站
     *
     * @param name 网站名
     * @param link 链接地址
     */
    @Override
    public void addBookmark(String name, String link) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.addBookmark(name, link)
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 添加书签成功
                            mIView.showToast(App.getContext().getString(R.string.add_bookmark_success));
                            refresh();
                        } else {
                            // 添加书签失败
                            mIView.showToast(App.getContext().getString(R.string.add_bookmark_failed));
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
     * 编辑书签
     *
     * @param position 书签在列表中的position，用于更新数据
     * @param bookmark 编辑的书签
     */
    @Override
    public void editBookmark(final int position, final Bookmark bookmark) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.editBookmark(bookmark.getId(), bookmark.getName(), bookmark.getLink())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 编辑书签成功
                            mIView.showToast(App.getContext().getString(R.string.edit_bookmark_success));
                            mIView.editBookmarkSuccess(position, bookmark);
                        } else {
                            // 编辑书签失败
                            mIView.showToast(App.getContext().getString(R.string.edit_bookmark_failed));
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
     * 删除书签
     *
     * @param position 书签在列表中的position，用于更新数据
     * @param id       书签id
     */
    @Override
    public void deleteBookmark(final int position, int id) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.deleteBookmark(id)
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 删除书签成功
                            mIView.showToast(App.getContext().getString(R.string.bookmark_delete_success));
                            mIView.deleteBookmarkSuccess(position);
                        } else {
                            // 删除书签失败
                            mIView.showToast(App.getContext().getString(R.string.bookmark_delete_failed));
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
}

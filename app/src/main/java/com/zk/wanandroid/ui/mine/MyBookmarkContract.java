package com.zk.wanandroid.ui.mine;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.Bookmark;
import com.zk.wanandroid.bean.DataResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 我的书签Contract
 * @author: zhukai
 * @date: 2018/3/14 17:30
 */
public interface MyBookmarkContract {

    abstract class MyBookmarkPresenter extends BasePresenter<IMyBookmarkModel, IMyBookmarkView> {
        /**
         * 获取收藏网站列表
         */
        public abstract void loadMyBookmark();

        /**
         * 刷新数据
         */
        public abstract void refresh();

        /**
         * 收藏网站
         *
         * @param name 网站名
         * @param link 链接地址
         */
        public abstract void addBookmark(String name, String link);

        /**
         * 编辑书签
         *
         * @param position 书签在列表中的position，用于更新数据
         * @param bookmark 编辑的书签
         */
        public abstract void editBookmark(int position, Bookmark bookmark);

        /**
         * 删除书签
         *
         * @param position 书签在列表中的position，用于更新数据
         * @param id       书签id
         */
        public abstract void deleteBookmark(int position, int id);
    }

    interface IMyBookmarkModel extends IBaseModel {
        /**
         * 获取收藏网站列表
         *
         * @return
         */
        Observable<DataResponse<List<Bookmark>>> loadMyBookmark();

        /**
         * 收藏网站
         *
         * @param name 网站名
         * @param link 链接地址
         * @return
         */
        Observable<DataResponse> addBookmark(String name, String link);

        /**
         * 编辑书签
         *
         * @param id   书签id
         * @param name 网站名
         * @param link 链接地址
         * @return
         */
        Observable<DataResponse> editBookmark(int id, String name, String link);

        /**
         * 删除书签
         *
         * @param id 书签id
         * @return
         */
        Observable<DataResponse> deleteBookmark(int id);
    }

    interface IMyBookmarkView extends IBaseView {
        /**
         * 显示我的书签列表
         *
         * @param bookmarks
         */
        void setMyBookmark(List<Bookmark> bookmarks);

        /**
         * 是否显示刷新view，这里使用SwipeRefreshLayout
         * 也可以使用IBaseView中的showProgressDialog()方法，以对话框形式刷新
         *
         * @param refresh
         */
        void showRefreshView(Boolean refresh);

        /**
         * 编辑书签成功
         *
         * @param position 书签在列表中的position，用于更新数据
         * @param bookmark 编辑后的书签
         */
        void editBookmarkSuccess(int position, Bookmark bookmark);

        /**
         * 删除书签成功
         *
         * @param position 书签在列表中的position，用于更新数据
         */
        void deleteBookmarkSuccess(int position);
    }
}

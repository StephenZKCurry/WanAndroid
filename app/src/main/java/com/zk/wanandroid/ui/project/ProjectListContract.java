package com.zk.wanandroid.ui.project;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.KnowledgeSystem;
import com.zk.wanandroid.bean.Project;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 项目列表Contract
 * @author: zhukai
 * @date: 2018/3/11 15:21
 */
public interface ProjectListContract {

    abstract class ProjectListPresenter extends BasePresenter<IProjectListModel, IProjectListView> {
        /**
         * 获取项目列表
         *
         * @param cid  项目分类id
         * @param page 页码，从1开始
         * @return
         */
        public abstract void loadProjectList(int cid, int page);

        /**
         * 下拉刷新数据
         */
        public abstract void refresh();

        /**
         * 上拉加载数据
         */
        public abstract void loadMore();
    }

    interface IProjectListModel extends IBaseModel {
        /**
         * 获取项目列表
         *
         * @param cid  项目分类id
         * @param page 页码，从1开始
         * @return
         */
        Observable<DataResponse<Project>> loadProjectList(int cid, int page);
    }

    interface IProjectListView extends IBaseView {
        /**
         * 显示项目列表
         *
         * @param project
         * @param loadType 类型：刷新或加载更多
         */
        void setProjectList(Project project, int loadType);

        /**
         * 是否显示刷新view，这里使用SwipeRefreshLayout
         * 也可以使用IBaseView中的showProgressDialog()方法，以对话框形式刷新
         *
         * @param refresh
         */
        void showRefreshView(Boolean refresh);
    }
}

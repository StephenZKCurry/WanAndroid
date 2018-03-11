package com.zk.wanandroid.ui.project;

import android.support.annotation.NonNull;

import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.Project;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.NetworkUtils;

import io.reactivex.functions.Consumer;

/**
 * @description: 项目列表Presenter
 * @author: zhukai
 * @date: 2018/3/11 15:25
 */
public class ProjectListPresenter extends ProjectListContract.ProjectListPresenter {

    private int mCid; // 分类的id
    private int mPage; // 当前页码

    @NonNull
    public static ProjectListPresenter newInstance() {
        return new ProjectListPresenter();
    }

    @Override
    public ProjectListContract.IProjectListModel getModel() {
        return ProjectListModel.newInstance();
    }

    /**
     * 下拉刷新数据
     */
    @Override
    public void refresh() {
        mPage = 1;
        loadProjectList(mCid, mPage);
        mIView.showRefreshView(true);
    }

    /**
     * 上拉加载数据
     */
    @Override
    public void loadMore() {
        mPage += 1;
        loadProjectList(mCid, mPage);
    }

    /**
     * 获取知识体系文章列表
     *
     * @param cid  分类的id，即知识体系二级目录的id
     * @param page 页码，从0开始
     */
    @Override
    public void loadProjectList(int cid, final int page) {
        mCid = cid;
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.loadProjectList(cid, page)
                .subscribe(new Consumer<DataResponse<Project>>() {
                    @Override
                    public void accept(DataResponse<Project> dataResponse) throws Exception {
                        if (page == 1) {
                            // 刷新
                            mIView.setProjectList(dataResponse.getData(), Constant.TYPE_REFRESH_SUCCESS);
                        } else {
                            // 加载更多
                            mIView.setProjectList(dataResponse.getData(), Constant.TYPE_LOAD_MORE_SUCCESS);
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

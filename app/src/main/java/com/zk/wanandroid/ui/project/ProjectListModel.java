package com.zk.wanandroid.ui.project;

import android.support.annotation.NonNull;

import com.zk.wanandroid.net.api.ApiService;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.Project;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.net.rx.RxSchedulers;

import io.reactivex.Observable;

/**
 * @description: 项目列表Model
 * @author: zhukai
 * @date: 2018/3/11 15:25
 */
public class ProjectListModel implements ProjectListContract.IProjectListModel {

    @NonNull
    public static ProjectListModel newInstance() {
        return new ProjectListModel();
    }

    /**
     * 获取项目列表
     *
     * @param cid  项目分类id
     * @param page 页码，从1开始
     * @return
     */
    @Override
    public Observable<DataResponse<Project>> loadProjectList(int cid, int page) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getProjectList(page, cid)
                .compose(RxSchedulers.<DataResponse<Project>>applySchedulers());
    }

    /**
     * 收藏项目
     *
     * @param id 项目id
     * @return
     */
    @Override
    public Observable<DataResponse> collectProject(int id) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .addCollectArticle(id)
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }

    /**
     * 取消收藏项目
     *
     * @param id 项目id
     * @return
     */
    @Override
    public Observable<DataResponse> cancelCollectProject(int id) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .removeCollectArticle(id)
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }
}

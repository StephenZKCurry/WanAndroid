package com.zk.wanandroid.ui.project;

import android.support.annotation.NonNull;

import com.zk.wanandroid.net.api.ApiService;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.KnowledgeSystem;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.net.rx.RxSchedulers;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 项目Model
 * @author: zhukai
 * @date: 2018/3/11 13:23
 */
public class ProjectModel implements ProjectContract.IProjectModel {

    @NonNull
    public static ProjectModel newInstance() {
        return new ProjectModel();
    }

    /**
     * 获取项目分类
     *
     * @return
     */
    @Override
    public Observable<DataResponse<List<KnowledgeSystem>>> getProjectType() {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getProjectType()
                .compose(RxSchedulers.<DataResponse<List<KnowledgeSystem>>>applySchedulers());
    }
}

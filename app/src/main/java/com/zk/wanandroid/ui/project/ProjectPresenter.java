package com.zk.wanandroid.ui.project;

import android.support.annotation.NonNull;

import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.KnowledgeSystem;
import com.zk.wanandroid.utils.NetworkUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @description: 项目Presenter
 * @author: zhukai
 * @date: 2018/3/11 13:23
 */
public class ProjectPresenter extends ProjectContract.ProjectPresenter {

    @NonNull
    public static ProjectPresenter newInstance() {
        return new ProjectPresenter();
    }

    @Override
    public ProjectContract.IProjectModel getModel() {
        return ProjectModel.newInstance();
    }

    /**
     * 获取项目分类
     */
    @Override
    public void getProjectType() {
        if (mIView == null || mIModel == null) {
            return;
        }
        mIView.showProgressDialog("请稍后");
        mRxManager.register(mIModel.getProjectType()
                .subscribe(new Consumer<DataResponse<List<KnowledgeSystem>>>() {
                    @Override
                    public void accept(DataResponse<List<KnowledgeSystem>> dataResponse) throws Exception {
                        mIView.setProjectType(dataResponse.getData());
                        mIView.hideProgressDialog();
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
                        mIView.hideProgressDialog();
                    }
                }));
    }
}

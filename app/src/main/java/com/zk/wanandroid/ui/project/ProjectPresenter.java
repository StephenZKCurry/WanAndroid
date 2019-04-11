package com.zk.wanandroid.ui.project;

import android.support.annotation.NonNull;

import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.KnowledgeSystem;
import com.zk.wanandroid.net.rx.BaseObserver;

import java.util.List;

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
                .subscribeWith(new BaseObserver<DataResponse<List<KnowledgeSystem>>>(mIView) {
                    @Override
                    public void onSuccess(DataResponse<List<KnowledgeSystem>> dataResponse) {
                        mIView.setProjectType(dataResponse.getData());
                        mIView.hideProgressDialog();
                    }

                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                        mIView.hideProgressDialog();
                    }
                }));
    }
}

package com.zk.wanandroid.ui.project;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.KnowledgeSystem;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 项目Contract
 * @author: zhukai
 * @date: 2018/3/11 13:02
 */
public interface ProjectContract {

    abstract class ProjectPresenter extends BasePresenter<IProjectModel, IProjectView> {
        /**
         * 获取项目分类
         */
        public abstract void getProjectType();
    }

    interface IProjectModel extends IBaseModel {
        /**
         * 获取项目分类
         *
         * @return
         */
        Observable<DataResponse<List<KnowledgeSystem>>> getProjectType();
    }

    interface IProjectView extends IBaseView {
        /**
         * 显示项目分类
         *
         * @param knowledgeSystems
         */
        void setProjectType(List<KnowledgeSystem> knowledgeSystems);
    }
}

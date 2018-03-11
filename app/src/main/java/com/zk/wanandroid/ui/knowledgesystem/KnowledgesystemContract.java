package com.zk.wanandroid.ui.knowledgesystem;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.KnowledgeSystem;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 知识体系Contract
 * @author: zhukai
 * @date: 2018/3/10 17:28
 */
public interface KnowledgesystemContract {

    abstract class KnowledgesystemPresenter extends BasePresenter<IKnowledgesystemModel, IKnowledgesystemView> {
        /**
         * 获取知识体系
         */
        public abstract void loadKnowledgesystem();

        /**
         * 刷新数据
         */
        public abstract void refresh();
    }

    interface IKnowledgesystemModel extends IBaseModel {
        /**
         * 获取知识体系
         *
         * @return
         */
        Observable<DataResponse<List<KnowledgeSystem>>> loadKnowledgesystem();
    }

    interface IKnowledgesystemView extends IBaseView {
        /**
         * 显示知识体系
         *
         * @param knowledgeSystems
         */
        void setKnowledgesystem(List<KnowledgeSystem> knowledgeSystems);

        /**
         * 是否显示刷新view，这里使用SwipeRefreshLayout
         * 也可以使用IBaseView中的showProgressDialog()方法，以对话框形式刷新
         *
         * @param refresh
         */
        void showRefreshView(Boolean refresh);
    }
}

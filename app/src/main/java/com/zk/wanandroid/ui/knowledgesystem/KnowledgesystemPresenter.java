package com.zk.wanandroid.ui.knowledgesystem;

import android.support.annotation.NonNull;

import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.KnowledgeSystem;
import com.zk.wanandroid.net.rx.BaseObserver;

import java.util.List;

/**
 * @description: 知识体系Presenter
 * @author: zhukai
 * @date: 2018/3/10 17:30
 */
public class KnowledgesystemPresenter extends KnowledgesystemContract.KnowledgesystemPresenter {

    @NonNull
    public static KnowledgesystemPresenter newInstance() {
        return new KnowledgesystemPresenter();
    }

    @Override
    public KnowledgesystemContract.IKnowledgesystemModel getModel() {
        return KnowledgesystemModel.newInstance();
    }

    /**
     * 获取知识体系
     */
    @Override
    public void loadKnowledgesystem() {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.loadKnowledgesystem()
                .subscribeWith(new BaseObserver<DataResponse<List<KnowledgeSystem>>>(mIView) {
                    @Override
                    public void onSuccess(DataResponse<List<KnowledgeSystem>> dataResponse) {
                        mIView.setKnowledgesystem(dataResponse.getData());
                        mIView.showRefreshView(false);
                    }

                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                        mIView.showRefreshView(false);
                    }
                }));
    }

    /**
     * 刷新数据
     */
    @Override
    public void refresh() {
        loadKnowledgesystem();
        mIView.showRefreshView(true);
    }
}

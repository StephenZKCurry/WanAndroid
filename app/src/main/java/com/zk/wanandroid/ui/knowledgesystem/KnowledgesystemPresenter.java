package com.zk.wanandroid.ui.knowledgesystem;

import android.support.annotation.NonNull;

import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.KnowledgeSystem;
import com.zk.wanandroid.utils.NetworkUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

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
                .subscribe(new Consumer<DataResponse<List<KnowledgeSystem>>>() {
                    @Override
                    public void accept(DataResponse<List<KnowledgeSystem>> dataResponse) throws Exception {
                        mIView.setKnowledgesystem(dataResponse.getData());
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

    /**
     * 刷新数据
     */
    @Override
    public void refresh() {
        loadKnowledgesystem();
        mIView.showRefreshView(true);
    }
}

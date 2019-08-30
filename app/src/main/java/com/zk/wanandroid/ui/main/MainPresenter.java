package com.zk.wanandroid.ui.main;

import android.support.annotation.NonNull;

import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.net.rx.BaseObserver;

/**
 * @description: 主页Presenter
 * @author: zhukai
 * @date: 2019/8/30 9:58
 */
public class MainPresenter extends MainContract.MainPresenter {

    @NonNull
    public static MainPresenter newInstance() {
        return new MainPresenter();
    }

    @Override
    public MainContract.IMainModel getModel() {
        return MainModel.newInstance();
    }

    /**
     * 获取我的积分
     */
    @Override
    public void getMyPoint() {
        if (mIView == null || mIModel == null) {
            return;
        }
        mIView.showLoading("请稍后");
        mRxManager.register(mIModel.getMyPoint()
                .subscribeWith(new BaseObserver<DataResponse>(mIView) {
                    @Override
                    public void onSuccess(DataResponse dataResponse) {
                        mIView.setMyPoint(String.valueOf(dataResponse.getData()));
                        mIView.hideLoading();
                    }

                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                        mIView.hideLoading();
                    }
                }));
    }
}

package com.zk.wanandroid.ui.navigation;

import android.support.annotation.NonNull;

import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.Navigation;
import com.zk.wanandroid.net.rx.BaseObserver;

import java.util.List;

/**
 * @description: 导航Presenter
 * @author: zhukai
 * @date: 2018/3/12 10:27
 */
public class NavigationPresenter extends NavigationContract.NavigationPresenter {

    @NonNull
    public static NavigationPresenter newInstance() {
        return new NavigationPresenter();
    }

    @Override
    public NavigationContract.INavigationModel getModel() {
        return NavigationModel.newInstance();
    }

    /**
     * 获取导航数据
     */
    @Override
    public void getNavigation() {
        if (mIView == null || mIModel == null) {
            return;
        }
        mIView.showProgressDialog("请稍后");
        mRxManager.register(mIModel.getNavigation()
                .subscribeWith(new BaseObserver<DataResponse<List<Navigation>>>(mIView) {
                    @Override
                    public void onSuccess(DataResponse<List<Navigation>> dataResponse) {
                        mIView.setNavigation(dataResponse.getData());
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

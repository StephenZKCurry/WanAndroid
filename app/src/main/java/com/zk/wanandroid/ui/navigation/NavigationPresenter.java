package com.zk.wanandroid.ui.navigation;

import android.support.annotation.NonNull;

import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.Navigation;
import com.zk.wanandroid.utils.NetworkUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

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
        mIView.showProgressDialog("请稍后...");
        mRxManager.register(mIModel.getNavigation()
                .subscribe(new Consumer<DataResponse<List<Navigation>>>() {
                    @Override
                    public void accept(DataResponse<List<Navigation>> dataResponse) throws Exception {
                        mIView.setNavigation(dataResponse.getData());
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

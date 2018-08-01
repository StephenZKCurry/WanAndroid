package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;

import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.User;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.NetworkUtils;

import io.reactivex.functions.Consumer;

/**
 * @description: 登录Presenter
 * @author: zhukai
 * @date: 2018/3/8 16:56
 */
public class LoginPresenter extends LoginContract.LoginPresenter {

    @NonNull
    public static LoginPresenter newInstance() {
        return new LoginPresenter();
    }

    @Override
    public LoginContract.ILoginModel getModel() {
        return LoginModel.newInstance();
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public void doLogin(String username, String password) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mIView.showProgressDialog("请稍后...");
        mRxManager.register(mIModel.doLogin(username, password)
                .subscribe(new Consumer<DataResponse<User>>() {
                    @Override
                    public void accept(DataResponse<User> dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 登录成功
                            mIView.showLoginSuccess(dataResponse.getData());
                        } else {
                            // 登录失败
                            mIView.showLoginFaild(dataResponse.getErrorMsg());
                        }
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

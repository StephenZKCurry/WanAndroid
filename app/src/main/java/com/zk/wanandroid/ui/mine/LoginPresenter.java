package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;

import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.User;
import com.zk.wanandroid.net.rx.BaseObserver;
import com.zk.wanandroid.utils.Constant;

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
        mIView.showLoading("请稍后");
        mRxManager.register(mIModel.doLogin(username, password)
                .subscribeWith(new BaseObserver<DataResponse<User>>(mIView) {
                    @Override
                    public void onSuccess(DataResponse<User> dataResponse) {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 登录成功
                            mIView.showLoginSuccess(dataResponse.getData());
                        } else {
                            // 登录失败
                            mIView.showLoginFaild(dataResponse.getErrorMsg());
                        }
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

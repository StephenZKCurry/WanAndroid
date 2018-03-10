package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;

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
}

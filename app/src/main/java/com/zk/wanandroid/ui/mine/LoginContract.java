package com.zk.wanandroid.ui.mine;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.User;

import io.reactivex.Observable;

/**
 * @description: 登录Contract
 * @author: zhukai
 * @date: 2018/3/8 16:54
 */
public interface LoginContract {

    abstract class LoginPresenter extends BasePresenter<ILoginModel, ILoginView> {
        /**
         * 登录
         *
         * @param username 用户名
         * @param password 密码
         */
        public abstract void doLogin(String username, String password);
    }

    interface ILoginModel extends IBaseModel {
        /**
         * 登录
         *
         * @param username 用户名
         * @param password 密码
         * @return
         */
        Observable<DataResponse<User>> doLogin(String username, String password);
    }

    interface ILoginView extends IBaseView {
        /**
         * 登录成功
         *
         * @param user 用户信息
         */
        void showLoginSuccess(User user);

        /**
         * 登录失败
         *
         * @param message 失败信息
         */
        void showLoginFaild(String message);
    }
}

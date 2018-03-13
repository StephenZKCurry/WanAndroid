package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;

import com.zk.wanandroid.api.ApiService;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.User;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.RxSchedulers;

import io.reactivex.Observable;

/**
 * @description: 登录Model
 * @author: zhukai
 * @date: 2018/3/8 16:57
 */
public class LoginModel implements LoginContract.ILoginModel {

    @NonNull
    public static LoginModel newInstance() {
        return new LoginModel();
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public Observable<DataResponse<User>> doLogin(String username, String password) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .doLogin(username, password)
                .compose(RxSchedulers.<DataResponse<User>>applySchedulers());
    }
}

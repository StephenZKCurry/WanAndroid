package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;

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
}

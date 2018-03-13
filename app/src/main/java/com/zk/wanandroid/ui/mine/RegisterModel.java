package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zk.wanandroid.api.ApiService;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.User;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * @description: 注册Model
 * @author: zhukai
 * @date: 2018/3/12 16:36
 */
public class RegisterModel implements RegisterContract.IRegisterModel {

    @NonNull
    public static RegisterModel newInstance() {
        return new RegisterModel();
    }

    /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     * @return
     */
    @Override
    public Observable<DataResponse<User>> doRegister(final String username, final String password, final String repassword) {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .doRegister(username, password, repassword)
                .filter(new Predicate<DataResponse<User>>() {
                    @Override
                    public boolean test(DataResponse<User> dataResponse) throws Exception {
                        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)
                                && !TextUtils.isEmpty(repassword);
                    }
                })
                .compose(RxSchedulers.<DataResponse<User>>applySchedulers());
    }
}

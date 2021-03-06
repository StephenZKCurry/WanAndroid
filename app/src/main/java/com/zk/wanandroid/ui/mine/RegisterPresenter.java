package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;

import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.User;
import com.zk.wanandroid.net.rx.BaseObserver;
import com.zk.wanandroid.utils.Constant;

/**
 * @description: 注册Presenter
 * @author: zhukai
 * @date: 2018/3/12 16:35
 */
public class RegisterPresenter extends RegisterContract.RegisterPresenter {

    @NonNull
    public static RegisterPresenter newInstance() {
        return new RegisterPresenter();
    }

    @Override
    public RegisterContract.IRegisterModel getModel() {
        return RegisterModel.newInstance();
    }

    /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     */
    @Override
    public void doRegister(String username, String password, String repassword) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mIView.showLoading("请稍后");
        mRxManager.register(mIModel.doRegister(username, password, repassword)
                .subscribeWith(new BaseObserver<DataResponse<User>>(mIView) {
                    @Override
                    public void onSuccess(DataResponse<User> dataResponse) {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 注册成功
                            mIView.showRegisterSuccess(dataResponse.getData());
                        } else {
                            // 注册失败
                            mIView.showRegisterFaild(dataResponse.getErrorMsg());
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

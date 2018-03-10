package com.zk.wanandroid.ui.mine;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;

/**
 * @description: 登录Contract
 * @author: zhukai
 * @date: 2018/3/8 16:54
 */
public interface LoginContract {

    abstract class LoginPresenter extends BasePresenter<ILoginModel, ILoginView> {

    }

    interface ILoginModel extends IBaseModel {

    }

    interface ILoginView extends IBaseView {

    }
}

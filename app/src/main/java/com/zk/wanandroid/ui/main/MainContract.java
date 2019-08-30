package com.zk.wanandroid.ui.main;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.DataResponse;

import io.reactivex.Observable;

/**
 * @description: 主页Contract
 * @author: zhukai
 * @date: 2019/8/30 9:52
 */
public interface MainContract {

    abstract class MainPresenter extends BasePresenter<IMainModel, IMainView> {

        /**
         * 获取我的积分
         */
        public abstract void getMyPoint();
    }

    interface IMainModel extends IBaseModel {
        /**
         * 获取我的积分
         *
         * @return
         */
        Observable<DataResponse> getMyPoint();
    }

    interface IMainView extends IBaseView {
        /**
         * 显示我的积分
         *
         * @param point
         */
        void setMyPoint(String point);
    }
}

package com.zk.wanandroid.ui.navigation;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.Navigation;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 导航Contract
 * @author: zhukai
 * @date: 2018/3/12 10:26
 */
public interface NavigationContract {

    abstract class NavigationPresenter extends BasePresenter<INavigationModel, INavigationView> {
        /**
         * 获取导航数据
         */
        public abstract void getNavigation();
    }

    interface INavigationModel extends IBaseModel {
        /**
         * 获取导航数据
         *
         * @return
         */
        Observable<DataResponse<List<Navigation>>> getNavigation();
    }

    interface INavigationView extends IBaseView {
        /**
         * 显示导航数据
         *
         * @param navigations
         */
        void setNavigation(List<Navigation> navigations);
    }
}

package com.zk.wanandroid.ui.navigation;

import android.support.annotation.NonNull;

import com.zk.wanandroid.api.ApiService;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.Navigation;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.RxSchedulers;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 导航Model
 * @author: zhukai
 * @date: 2018/3/12 10:27
 */
public class NavigationModel implements NavigationContract.INavigationModel {

    @NonNull
    public static NavigationModel newInstance() {
        return new NavigationModel();
    }

    /**
     * 获取导航数据
     *
     * @return
     */
    @Override
    public Observable<DataResponse<List<Navigation>>> getNavigation() {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getNavigation()
                .compose(RxSchedulers.<DataResponse<List<Navigation>>>applySchedulers());
    }
}

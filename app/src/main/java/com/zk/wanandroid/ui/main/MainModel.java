package com.zk.wanandroid.ui.main;

import android.support.annotation.NonNull;

import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.net.api.ApiService;
import com.zk.wanandroid.net.rx.RxSchedulers;
import com.zk.wanandroid.utils.Constant;

import io.reactivex.Observable;

/**
 * @description: 主页Model
 * @author: zhukai
 * @date: 2019/8/30 9:56
 */
public class MainModel implements MainContract.IMainModel {

    @NonNull
    public static MainModel newInstance() {
        return new MainModel();
    }

    /**
     * 获取我的积分
     *
     * @return
     */
    @Override
    public Observable<DataResponse> getMyPoint() {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getMyPoint()
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }
}

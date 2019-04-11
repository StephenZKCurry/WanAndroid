package com.zk.wanandroid.ui.hotsearch;

import android.support.annotation.NonNull;

import com.zk.wanandroid.net.api.ApiService;
import com.zk.wanandroid.bean.CommonWeb;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.net.rx.RxSchedulers;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 常用网站Model
 * @author: zhukai
 * @date: 2018/3/7 14:40
 */
public class CommonWebModel implements CommonWebContract.ICommonWebModel {

    @NonNull
    public static CommonWebModel newInstance() {
        return new CommonWebModel();
    }

    /**
     * 查询常用网站
     *
     * @return
     */
    @Override
    public Observable<DataResponse<List<CommonWeb>>> loadCommonWeb() {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getCommonWeb()
                .compose(RxSchedulers.<DataResponse<List<CommonWeb>>>applySchedulers());
    }
}

package com.zk.wanandroid.ui.wechat;

import android.support.annotation.NonNull;

import com.zk.wanandroid.api.ApiService;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.KnowledgeSystem;
import com.zk.wanandroid.manager.RetrofitManager;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.RxSchedulers;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 公众号Model
 * @author: zhukai
 * @date: 2019/4/9 17:29
 */
public class WechatModel implements WechatContract.IWechatModel {

    @NonNull
    public static WechatModel newInstance() {
        return new WechatModel();
    }

    /**
     * 获取公众号列表
     *
     * @return
     */
    @Override
    public Observable<DataResponse<List<KnowledgeSystem>>> getWechat() {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getWechat()
                .compose(RxSchedulers.<DataResponse<List<KnowledgeSystem>>>applySchedulers());
    }
}

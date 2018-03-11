package com.zk.wanandroid.ui.knowledgesystem;

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
 * @description: 知识体系Model
 * @author: zhukai
 * @date: 2018/3/10 17:31
 */
public class KnowledgesystemModel implements KnowledgesystemContract.IKnowledgesystemModel {

    @NonNull
    public static KnowledgesystemModel newInstance() {
        return new KnowledgesystemModel();
    }

    /**
     * 获取知识体系
     *
     * @return
     */
    @Override
    public Observable<DataResponse<List<KnowledgeSystem>>> loadKnowledgesystem() {
        return RetrofitManager.createApi(ApiService.class, Constant.BASE_URL)
                .getKnowledgeSystems()
                .compose(RxSchedulers.<DataResponse<List<KnowledgeSystem>>>applySchedulers());
    }
}

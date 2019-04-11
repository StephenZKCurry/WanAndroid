package com.zk.wanandroid.ui.wechat;

import android.support.annotation.NonNull;

import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.KnowledgeSystem;
import com.zk.wanandroid.utils.NetworkUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @description: 公众号Presenter
 * @author: zhukai
 * @date: 2019/4/9 17:27
 */
public class WechatPresenter extends WechatContract.WechatPresenter {

    @NonNull
    public static WechatPresenter newInstance() {
        return new WechatPresenter();
    }

    @Override
    public WechatContract.IWechatModel getModel() {
        return WechatModel.newInstance();
    }

    /**
     * 获取公众号列表
     */
    @Override
    public void getWechat() {
        if (mIView == null || mIModel == null) {
            return;
        }
        mIView.showProgressDialog("请稍后");
        mRxManager.register(mIModel.getWechat()
                .subscribe(new Consumer<DataResponse<List<KnowledgeSystem>>>() {
                    @Override
                    public void accept(DataResponse<List<KnowledgeSystem>> dataResponse) throws Exception {
                        mIView.setWechatTab(dataResponse.getData());
                        mIView.hideProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        boolean available = NetworkUtils.isAvailable(App.getContext());
                        if (!available) {
                            mIView.showNoNet();
                        } else {
                            mIView.showFaild(throwable.getMessage());
                        }
                        mIView.hideProgressDialog();
                    }
                }));
    }
}

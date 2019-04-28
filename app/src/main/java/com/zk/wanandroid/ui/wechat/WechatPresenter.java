package com.zk.wanandroid.ui.wechat;

import android.support.annotation.NonNull;

import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.KnowledgeSystem;
import com.zk.wanandroid.net.rx.BaseObserver;

import java.util.List;

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
        mIView.showLoading("请稍后");
        mRxManager.register(mIModel.getWechat()
                .subscribeWith(new BaseObserver<DataResponse<List<KnowledgeSystem>>>(mIView) {
                    @Override
                    public void onSuccess(DataResponse<List<KnowledgeSystem>> dataResponse) {
                        mIView.setWechatTab(dataResponse.getData());
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

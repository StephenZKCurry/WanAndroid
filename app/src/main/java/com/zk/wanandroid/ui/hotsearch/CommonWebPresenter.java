package com.zk.wanandroid.ui.hotsearch;

import android.support.annotation.NonNull;

import com.zk.wanandroid.bean.CommonWeb;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.net.rx.BaseObserver;

import java.util.List;

/**
 * @description: 常用网站Presenter
 * @author: zhukai
 * @date: 2018/3/7 14:40
 */
public class CommonWebPresenter extends CommonWebContract.CommonWebPresenter {

    @NonNull
    public static CommonWebPresenter newInstance() {
        return new CommonWebPresenter();
    }

    @Override
    public CommonWebContract.ICommonWebModel getModel() {
        return CommonWebModel.newInstance();
    }

    /**
     * 查询常用网站
     */
    @Override
    public void loadCommonWeb() {
        if (mIView == null || mIModel == null) {
            return;
        }
        mIView.showLoading("请稍后");
        mRxManager.register(mIModel.loadCommonWeb()
                .subscribeWith(new BaseObserver<DataResponse<List<CommonWeb>>>(mIView) {
                    @Override
                    public void onSuccess(DataResponse<List<CommonWeb>> dataResponse) {
                        mIView.setCommonWeb(dataResponse.getData());
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

package com.zk.wanandroid.ui.hotsearch;

import android.support.annotation.NonNull;

import com.zk.wanandroid.base.App;
import com.zk.wanandroid.bean.CommonWeb;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.utils.NetworkUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

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
        mIView.showProgressDialog("请稍后");
        mRxManager.register(mIModel.loadCommonWeb()
                .subscribe(new Consumer<DataResponse<List<CommonWeb>>>() {
                    @Override
                    public void accept(DataResponse<List<CommonWeb>> dataResponse) throws Exception {
                        mIView.setCommonWeb(dataResponse.getData());
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

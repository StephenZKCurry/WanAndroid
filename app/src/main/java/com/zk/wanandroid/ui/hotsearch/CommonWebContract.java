package com.zk.wanandroid.ui.hotsearch;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.CommonWeb;
import com.zk.wanandroid.bean.DataResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 常用网站Contract
 * @author: zhukai
 * @date: 2018/3/7 14:37
 */
public interface CommonWebContract {

    abstract class CommonWebPresenter extends BasePresenter<CommonWebContract.ICommonWebModel, CommonWebContract.ICommonWebView> {
        /**
         * 查询常用网站
         */
        public abstract void loadCommonWeb();
    }

    interface ICommonWebModel extends IBaseModel {
        /**
         * 查询常用网站
         *
         * @return
         */
        Observable<DataResponse<List<CommonWeb>>> loadCommonWeb();
    }

    interface ICommonWebView extends IBaseView {
        /**
         * 显示常用网站
         *
         * @param commonWebs
         */
        void setCommonWeb(List<CommonWeb> commonWebs);
    }
}

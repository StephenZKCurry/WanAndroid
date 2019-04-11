package com.zk.wanandroid.ui.wechat;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;
import com.zk.wanandroid.bean.DataResponse;
import com.zk.wanandroid.bean.KnowledgeSystem;

import java.util.List;

import io.reactivex.Observable;

/**
 * @description: 公众号Contract
 * @author: zhukai
 * @date: 2019/4/9 17:20
 */
public interface WechatContract {

    abstract class WechatPresenter extends BasePresenter<IWechatModel, IWechatView> {
        /**
         * 获取公众号列表
         */
        public abstract void getWechat();
    }

    interface IWechatModel extends IBaseModel {
        /**
         * 获取公众号列表
         *
         * @return
         */
        Observable<DataResponse<List<KnowledgeSystem>>> getWechat();
    }

    interface IWechatView extends IBaseView {
        /**
         * 显示公众号列表
         *
         * @param wechats
         */
        void setWechatTab(List<KnowledgeSystem> wechats);
    }
}

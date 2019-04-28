package com.zk.wanandroid.base;

import android.support.annotation.NonNull;

/**
 * @description: View基类接口
 * @author: zhukai
 * @date: 2018/3/2 12:00
 */
public interface IBaseView {

    /**
     * 初始化presenter
     * <p>
     * 此方法返回的presenter对象不可为空
     */
    @NonNull
    BasePresenter initPresenter();

    /**
     * 显示toast消息
     *
     * @param msg 要显示的toast消息字符串
     */
    void showToast(String msg);

    /**
     * 显示加载dialog
     *
     * @param waitMsg 加载提示字符串
     */
    void showLoading(String waitMsg);

    /**
     * 隐藏加载dialog
     */
    void hideLoading();

    /**
     * 请求成功
     *
     * @param message 成功信息
     */
    void showSuccess(String message);

    /**
     * 请求失败
     *
     * @param message 失败信息
     */
    void showFaild(String message);

    /**
     * 显示当前网络不可用
     */
    void showNoNet();
}

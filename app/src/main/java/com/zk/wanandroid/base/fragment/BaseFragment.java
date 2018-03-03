package com.zk.wanandroid.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.wanandroid.utils.ToastUtils;
import com.zk.wanandroid.widgets.WaitPorgressDialog;

import butterknife.ButterKnife;

/**
 * @description: 普通Fragment基类
 * @author: zhukai
 * @date: 2018/3/2 14:01
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected View mRootView;
    protected WaitPorgressDialog mWaitPorgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getContentViewId(), null);
            initView(mRootView);
            initData();
            initEvent();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 设置根布局资源id
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 初始化页面
     */
    protected void initView(View view) {
        ButterKnife.bind(this, view);
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        mWaitPorgressDialog = new WaitPorgressDialog(mContext);
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {

    }

    /**
     * 弹出Toast
     *
     * @param msg 要显示的toast消息字符串
     */
    protected void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    /**
     * 显示提示框
     *
     * @param msg 等待消息字符串
     */
    protected void showProgressDialog(String msg) {
        if (mWaitPorgressDialog.isShowing()) {
            mWaitPorgressDialog.dismiss();
        }
        mWaitPorgressDialog.setMessage(msg);
        mWaitPorgressDialog.show();
    }

    /**
     * 隐藏提示框
     */
    protected void hideProgressDialog() {
        if (mWaitPorgressDialog != null) {
            mWaitPorgressDialog.dismiss();
        }
    }
}
package com.zk.wanandroid.ui.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.wanandroid.R;
import com.zk.wanandroid.base.fragment.BaseFragment;

/**
 * @description: 导航网站Fragment
 * @author: zhukai
 * @date: 2018/3/5 10:51
 */
public class NavigationFragment extends BaseFragment {

    public static NavigationFragment newInstance() {
        Bundle args = new Bundle();
        NavigationFragment fragment = new NavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }
}

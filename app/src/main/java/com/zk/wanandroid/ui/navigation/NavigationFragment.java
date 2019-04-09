package com.zk.wanandroid.ui.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.fragment.BaseMVPFragment;
import com.zk.wanandroid.bean.Navigation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.youngkaaa.yviewpager.YViewPager;
import q.rorbin.verticaltablayout.VerticalTabLayout;

/**
 * @description: 导航网站Fragment
 * @author: zhukai
 * @date: 2018/3/5 10:51
 */
public class NavigationFragment extends BaseMVPFragment<NavigationContract.NavigationPresenter, NavigationContract.INavigationModel>
        implements NavigationContract.INavigationView {

    @BindView(R.id.tl_tabs)
    VerticalTabLayout mTabLayout;
    @BindView(R.id.vp_tabs)
    YViewPager mViewPager;

    private List<Fragment> mFragments;
    private NavigationFragmentAdapter mAdapter;

    public static NavigationFragment newInstance() {
        Bundle args = new Bundle();
        NavigationFragment fragment = new NavigationFragment();
        fragment.setArguments(args);
        return fragment;
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
        mFragments = new ArrayList<>();
        mPresenter.getNavigation();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return NavigationPresenter.newInstance();
    }

    /**
     * 显示导航数据
     *
     * @param navigations
     */
    @Override
    public void setNavigation(final List<Navigation> navigations) {
        for (int i = 0; i < navigations.size(); i++) {
            NavigationListFragment navigationListFragment = NavigationListFragment.newInstance((Serializable) navigations.get(i));
            mFragments.add(navigationListFragment);
        }
        mAdapter = new NavigationFragmentAdapter(getChildFragmentManager(), mFragments, navigations);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(navigations.size());
        mTabLayout.setBackgroundColor(getResources().getColor(R.color.color_divider));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}

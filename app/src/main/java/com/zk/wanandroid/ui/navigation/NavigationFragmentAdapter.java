package com.zk.wanandroid.ui.navigation;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zk.wanandroid.bean.Navigation;

import java.util.List;

import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;

/**
 * @description: 导航ViewPager adapter
 * @author: zhukai
 * @date: 2018/3/12 13:03
 */
public class NavigationFragmentAdapter extends FragmentStatePagerAdapter implements TabAdapter {

    private List<Fragment> mFragments;
    private List<Navigation> mData;

    public NavigationFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<Navigation> data) {
        super(fm);
        this.mFragments = fragments;
        this.mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public ITabView.TabBadge getBadge(int position) {
        return null;
    }

    @Override
    public ITabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public ITabView.TabTitle getTitle(int position) {
        return new ITabView.TabTitle.Builder()
                .setContent(mData.get(position).getName())
                .setTextColor(Color.BLACK, Color.GRAY)
                .setTextSize(14)
                .build();
    }

    @Override
    public int getBackground(int position) {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getName();
    }
}

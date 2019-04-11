package com.zk.wanandroid.ui.wechat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.fragment.BaseMVPFragment;
import com.zk.wanandroid.bean.KnowledgeSystem;
import com.zk.wanandroid.ui.article.ArticleTypeFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @description: 公众号Fragment
 * @author: zhukai
 * @date: 2019/4/9 17:13
 */
public class WechatFragment extends BaseMVPFragment<WechatContract.WechatPresenter, WechatContract.IWechatModel> implements
        WechatContract.IWechatView {

    @BindView(R.id.tl_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.vp_tabs)
    ViewPager mViewPager;

    private List<Fragment> mFragments;

    public static WechatFragment newInstance() {
        Bundle args = new Bundle();
        WechatFragment fragment = new WechatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_wechat;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void initData() {
        super.initData();
        mFragments = new ArrayList<>();
        mPresenter.getWechat();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return WechatPresenter.newInstance();
    }

    /**
     * 显示公众号列表
     *
     * @param wechats
     */
    @Override
    public void setWechatTab(List<KnowledgeSystem> wechats) {
        for (int i = 0; i < wechats.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(wechats.get(i).getName()));
            WechatListFragment wechatListFragment = WechatListFragment.newInstance(wechats.get(i).getId());
            mFragments.add(wechatListFragment);
        }
        mViewPager.setAdapter(new ArticleTypeFragmentAdapter(getChildFragmentManager(), mFragments));
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
        // mTabLayout.setupWithViewPager方法内部会remove所有的tabs，这里重新设置一遍tabs的text，否则tabs的text不显示
        for (int i = 0; i < wechats.size(); i++) {
            mTabLayout.getTabAt(i).setText(wechats.get(i).getName());
        }
    }
}

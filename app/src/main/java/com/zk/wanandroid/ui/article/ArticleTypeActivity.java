package com.zk.wanandroid.ui.article;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.zk.wanandroid.R;
import com.zk.wanandroid.base.activity.BaseActivity;
import com.zk.wanandroid.bean.KnowledgeSystem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @description: 知识体系详情页面
 * @author: zhukai
 * @date: 2018/3/11 10:32
 */
public class ArticleTypeActivity extends BaseActivity {

    @BindView(R.id.tl_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.vp_tabs)
    ViewPager mViewPager;
    private List<KnowledgeSystem.ChildrenBean> mTabs;
    private List<Fragment> mFragments;

    public static final String ARTICLE_TYPE_FIRST = "article_type_first"; // 一级目录
    public static final String ARTICLE_TYPE_SECOND = "article_type_second"; // 二级目录

    @Override
    protected int getContentViewId() {
        return R.layout.activity_article_type;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置一级目录名为标题
        getSupportActionBar().setTitle(getIntent().getStringExtra(ARTICLE_TYPE_FIRST));
    }

    @Override
    protected void initData() {
        super.initData();
        mFragments = new ArrayList<>();
        mTabs = (List<KnowledgeSystem.ChildrenBean>) getIntent().getSerializableExtra(ARTICLE_TYPE_SECOND);
        for (int i = 0; i < mTabs.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTabs.get(i).getName()));
            ArticleTypeFragment articleTypeFragment = ArticleTypeFragment.newInstance(mTabs.get(i).getId());
            mFragments.add(articleTypeFragment);
        }
        mViewPager.setAdapter(new ArticleTypeFragmentAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
        // mTabLayout.setupWithViewPager方法内部会remove所有的tabs，这里重新设置一遍tabs的text，否则tabs的text不显示
        for (int i = 0; i < mTabs.size(); i++) {
            mTabLayout.getTabAt(i).setText(mTabs.get(i).getName());
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }
}

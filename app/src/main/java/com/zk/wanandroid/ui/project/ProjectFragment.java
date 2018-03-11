package com.zk.wanandroid.ui.project;

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
 * @description: 项目Fragment
 * @author: zhukai
 * @date: 2018/3/5 10:51
 */
public class ProjectFragment extends BaseMVPFragment<ProjectContract.ProjectPresenter, ProjectContract.IProjectModel>
        implements ProjectContract.IProjectView {

    @BindView(R.id.tl_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.vp_tabs)
    ViewPager mViewPager;

    private List<Fragment> mFragments;

    public static ProjectFragment newInstance() {
        Bundle args = new Bundle();
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void initData() {
        super.initData();
        mFragments = new ArrayList<>();
        mPresenter.getProjectType();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return ProjectPresenter.newInstance();
    }

    /**
     * 显示项目分类
     *
     * @param knowledgeSystems
     */
    @Override
    public void setProjectType(List<KnowledgeSystem> knowledgeSystems) {
        for (int i = 0; i < knowledgeSystems.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(knowledgeSystems.get(i).getName()));
            ProjectListFragment projectListFragment = ProjectListFragment.newInstance();
            projectListFragment.setSubId(knowledgeSystems.get(i).getId() + "");
            mFragments.add(projectListFragment);
        }
        mViewPager.setAdapter(new ArticleTypeFragmentAdapter(getChildFragmentManager(), mFragments));
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
        // mTabLayout.setupWithViewPager方法内部会remove所有的tabs，这里重新设置一遍tabs的text，否则tabs的text不显示
        for (int i = 0; i < knowledgeSystems.size(); i++) {
            mTabLayout.getTabAt(i).setText(knowledgeSystems.get(i).getName());
        }
    }
}

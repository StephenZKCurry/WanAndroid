package com.zk.wanandroid.ui.navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.fragment.BaseFragment;
import com.zk.wanandroid.bean.Navigation;
import com.zk.wanandroid.ui.article.ArticleContentActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * @description: 导航网站列表Fragment
 * @author: zhukai
 * @date: 2018/3/12 11:45
 */
public class NavigationListFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tfl_navigation)
    TagFlowLayout mTflNavigation;

    private List<Navigation.ArticlesBean> articles;
    private NavigationAdapter mAdapter;

    public static final String NAVIGATION_ARTICLE_TITLE = "navigation_article_title";

    public static NavigationListFragment newInstance(Serializable param) {
        Bundle args = new Bundle();
        NavigationListFragment fragment = new NavigationListFragment();
        args.putSerializable(NAVIGATION_ARTICLE_TITLE, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_navigation_list;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void initData() {
        super.initData();
        Navigation data = ((Navigation) getArguments().getSerializable(NAVIGATION_ARTICLE_TITLE));
        if (data == null || data.getArticles() == null) {
            return;
        }
        tvTitle.setText(data.getName()); // 导航网站标题
        articles = data.getArticles();
        mAdapter = new NavigationAdapter(mContext, articles);
        mTflNavigation.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTflNavigation.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                // 跳转网站详情页
                ArticleContentActivity.runActivity(mContext, mAdapter.getItem(position).getLink(),
                        mAdapter.getItem(position).getTitle());
                return false;
            }
        });
    }
}

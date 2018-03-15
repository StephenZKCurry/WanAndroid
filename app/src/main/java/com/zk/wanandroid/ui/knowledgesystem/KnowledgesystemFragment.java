package com.zk.wanandroid.ui.knowledgesystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.fragment.BaseMVPFragment;
import com.zk.wanandroid.bean.KnowledgeSystem;
import com.zk.wanandroid.ui.article.ArticleTypeActivity;
import com.zk.wanandroid.utils.ActivityUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * @description: 知识体系Fragment
 * @author: zhukai
 * @date: 2018/3/5 10:51
 */
public class KnowledgesystemFragment extends BaseMVPFragment<KnowledgesystemContract.KnowledgesystemPresenter, KnowledgesystemContract.IKnowledgesystemModel>
        implements KnowledgesystemContract.IKnowledgesystemView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_knowledgesystem)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_knowledgesystem)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private KnowledgesystemAdapter mAdapter;

    public static KnowledgesystemFragment newInstance() {
        Bundle args = new Bundle();
        KnowledgesystemFragment fragment = new KnowledgesystemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_knowledgesystem;
    }

    /**
     * 初始化页面
     *
     * @param view
     */
    @Override
    protected void initView(View view) {
        super.initView(view);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_main));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new KnowledgesystemAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    /**
     * 初始化事件
     */
    @Override
    protected void initEvent() {
        super.initEvent();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return KnowledgesystemPresenter.newInstance();
    }

    /**
     * 显示知识体系
     *
     * @param knowledgeSystems
     */
    @Override
    public void setKnowledgesystem(List<KnowledgeSystem> knowledgeSystems) {
        mAdapter.setNewData(knowledgeSystems);
    }

    /**
     * 是否显示刷新view
     *
     * @param refresh
     */
    @Override
    public void showRefreshView(final Boolean refresh) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(refresh);
            }
        });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 跳转知识体系详情页面
        Intent intent = new Intent(mContext, ArticleTypeActivity.class);
        intent.putExtra(ArticleTypeActivity.ARTICLE_TYPE_FIRST, mAdapter.getItem(position).getName());
        intent.putExtra(ArticleTypeActivity.ARTICLE_TYPE_SECOND, (Serializable) mAdapter.getItem(position).getChildren());
        ActivityUtils.startActivity(mContext, intent);
    }
}

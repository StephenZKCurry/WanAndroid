package com.zk.wanandroid.ui.project;

import android.content.Intent;
import android.net.Uri;
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
import com.zk.wanandroid.bean.Project;
import com.zk.wanandroid.ui.article.ArticleContentActivity;
import com.zk.wanandroid.utils.Constant;

import butterknife.BindView;

/**
 * @description: 项目列表Fragment
 * @author: zhukai
 * @date: 2018/3/11 15:12
 */
public class ProjectListFragment extends BaseMVPFragment<ProjectListContract.ProjectListPresenter, ProjectListContract.IProjectListModel>
        implements ProjectListContract.IProjectListView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_project)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_project)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ProjectListAdapter mAdapter;

    public static ProjectListFragment newInstance() {
        Bundle args = new Bundle();
        ProjectListFragment fragment = new ProjectListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_main));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ProjectListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        showRefreshView(true);
        mPresenter.loadProjectList(Integer.parseInt(typeId), 1);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return ProjectListPresenter.newInstance();
    }

    @Override
    public void setProjectList(Project project, int loadType) {
        switch (loadType) {
            case Constant.TYPE_REFRESH_SUCCESS:
                // 刷新
                mAdapter.setNewData(project.getDatas());
                break;
            case Constant.TYPE_LOAD_MORE_SUCCESS:
                // 加载更多
                mAdapter.addData(project.getDatas());
                break;
            default:
                break;
        }
        if (project.getDatas() == null || project.getDatas().isEmpty() ||
                project.getDatas().size() < Constant.PAGE_SIZE_PROJECT) {
            mAdapter.loadMoreEnd();
        } else {
            mAdapter.loadMoreComplete();
        }
    }

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

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    /**
     * RecyclerView点击
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 跳转项目详情页面
        ArticleContentActivity.runActivity(mContext, mAdapter.getItem(position).getLink(), mAdapter.getItem(position).getTitle());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_download:
                // 跳转手机浏览器下载apk
                Uri uri = Uri.parse(mAdapter.getItem(position).getApkLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

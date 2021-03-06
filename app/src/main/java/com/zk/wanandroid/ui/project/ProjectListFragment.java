package com.zk.wanandroid.ui.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.fragment.BaseMVPFragment;
import com.zk.wanandroid.bean.Project;
import com.zk.wanandroid.rxbus.Subscribe;
import com.zk.wanandroid.rxbus.ThreadMode;
import com.zk.wanandroid.ui.article.ArticleContentActivity;
import com.zk.wanandroid.ui.mine.LoginActivity;
import com.zk.wanandroid.utils.ActivityUtils;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.SpUtils;

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

    private int projectTypeId; // 项目类型id
    private static final String PROJECT_TYPE_ID = "project_type_id";

    public static ProjectListFragment newInstance(int projectTypeId) {
        Bundle args = new Bundle();
        args.putInt(PROJECT_TYPE_ID, projectTypeId);
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
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.color_main));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ProjectListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        projectTypeId = getArguments().getInt(PROJECT_TYPE_ID);
        showRefreshView(true);
        mPresenter.loadProjectList(projectTypeId, 1);
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
     * 收藏成功
     *
     * @param position 项目在列表中的position，用于更新数据
     * @param project  收藏的项目
     */
    @Override
    public void collectProjectSuccess(int position, Project.DatasBean project) {
        // 更新数据
        mAdapter.setData(position, project);
    }

    /**
     * 取消收藏成功
     *
     * @param position 项目在列表中的position，用于更新数据
     * @param project  取消收藏的项目
     */
    @Override
    public void cancelCollectProjectSuccess(int position, Project.DatasBean project) {
        // 更新数据
        mAdapter.setData(position, project);
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
            case R.id.iv_collect:
                String username = SpUtils.getString(mContext, Constant.USER_NAME, "");
                if (TextUtils.isEmpty(username)) {
                    // 未登录，跳转登录页面
                    showToast(getString(R.string.collection_no_login));
                    ActivityUtils.startActivity(mContext, new Intent(mContext, LoginActivity.class));
                } else {
                    // 收藏（取消收藏）项目
                    Project.DatasBean project = mAdapter.getItem(position);
                    if (!project.isCollect()) {
                        // 添加收藏
                        mPresenter.collectProject(position, project);
                    } else {
                        // 取消收藏
                        mPresenter.cancelCollectProject(position, project);
                    }
                }
                break;
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

    /**
     * 登录或退出登录刷新
     */
    @Subscribe(code = Constant.RX_BUS_CODE_LOGIN, threadMode = ThreadMode.MAIN)
    public void refreshProject() {
        mPresenter.refresh();
    }
}

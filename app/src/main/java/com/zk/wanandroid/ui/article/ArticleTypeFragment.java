package com.zk.wanandroid.ui.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.fragment.BaseMVPFragment;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.rxbus.Subscribe;
import com.zk.wanandroid.rxbus.ThreadMode;
import com.zk.wanandroid.ui.mine.LoginActivity;
import com.zk.wanandroid.utils.ActivityUtils;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.SpUtils;

import butterknife.BindView;

/**
 * @description: 知识体系详情Fragment
 * @author: zhukai
 * @date: 2018/3/11 11:39
 */
public class ArticleTypeFragment extends BaseMVPFragment<ArticleTypeContract.ArticleTypePresenter, ArticleTypeContract.IArticleTypeModel>
        implements ArticleTypeContract.IArticleTypeView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_article)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_article)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArticleAdapter mAdapter; // 文章列表adapter

    public static ArticleTypeFragment newInstance() {
        Bundle args = new Bundle();
        ArticleTypeFragment fragment = new ArticleTypeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_article_type;
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
        mAdapter = new ArticleAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        super.initData();
        showRefreshView(true);
        mPresenter.loadKnowledgesystemArticles(Integer.parseInt(typeId), 0);
    }

    /**
     * 初始化事件
     */
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
        return ArticleTypePresenter.newInstance();
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
     * 收藏成功
     *
     * @param position 文章在列表中的position，用于更新数据
     * @param article  收藏的文章
     */
    @Override
    public void collectArticleSuccess(int position, Article.DatasBean article) {
        // 更新数据
        mAdapter.setData(position, article);
    }

    /**
     * 取消收藏成功
     *
     * @param position 文章在列表中的position，用于更新数据
     * @param article  取消收藏的文章
     */
    @Override
    public void cancelCollectArticleSuccess(int position, Article.DatasBean article) {
        // 更新数据
        mAdapter.setData(position, article);
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 跳转文章详情页面
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
                }else {
                    // 收藏（取消收藏）文章
                    Article.DatasBean article = mAdapter.getItem(position);
                    if (!article.isCollect()) {
                        // 添加收藏
                        mPresenter.collectArticle(position, article);
                    } else {
                        // 取消收藏
                        mPresenter.cancelCollectArticle(position, article);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setKnowledgesystemArticles(Article article, int loadType) {
        switch (loadType) {
            case Constant.TYPE_REFRESH_SUCCESS:
                // 刷新
                mAdapter.setNewData(article.getDatas());
                break;
            case Constant.TYPE_LOAD_MORE_SUCCESS:
                // 加载更多
                mAdapter.addData(article.getDatas());
                break;
            default:
                break;
        }
        if (article.getDatas() == null || article.getDatas().isEmpty() ||
                article.getDatas().size() < Constant.PAGE_SIZE) {
            mAdapter.loadMoreEnd();
        } else {
            mAdapter.loadMoreComplete();
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

package com.zk.wanandroid.ui.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.fragment.BaseMVPFragment;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.rxbus.Subscribe;
import com.zk.wanandroid.rxbus.ThreadMode;
import com.zk.wanandroid.ui.article.ArticleAdapter;
import com.zk.wanandroid.ui.article.ArticleContentActivity;
import com.zk.wanandroid.ui.mine.LoginActivity;
import com.zk.wanandroid.utils.ActivityUtils;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description: 公众号文章列表Fragment
 * @author: zhukai
 * @date: 2019/4/10 11:31
 */
public class WechatListFragment extends BaseMVPFragment<WechatListContract.WechatListPresenter, WechatListContract.IWechatListModel> implements
        WechatListContract.IWechatListView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_wechat)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_wechat)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.et_search)
    EditText etSearch;

    private ArticleAdapter mAdapter; // 文章列表adapter
    private String searchKey; // 搜索条件

    private int wechatId; // 公众号id
    private static final String WECHAT_ID = "wechat_id";

    public static WechatListFragment newInstance(int wechatId) {
        Bundle args = new Bundle();
        args.putInt(WECHAT_ID, wechatId);
        WechatListFragment fragment = new WechatListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_wechat_list;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.color_main));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ArticleAdapter(null);
        // 设置空数据显示
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, null, false);
        mAdapter.setEmptyView(emptyView);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        wechatId = getArguments().getInt(WECHAT_ID);
        showRefreshView(true);
        if (TextUtils.isEmpty(searchKey)) {
            mPresenter.loadWechatArticle(wechatId, 1);
        } else {
            mPresenter.searchWechatArticle(wechatId, 1, searchKey);
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 隐藏软键盘
                    hiddenKeyboard();
                    // 搜索文章
                    searchKey = etSearch.getText().toString().trim();
                    showRefreshView(true);
                    mPresenter.searchWechatArticle(wechatId, 1, searchKey);
                    return true;
                }
                return false;
            }
        });
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return WechatListPresenter.newInstance();
    }

    @OnClick({R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                // 搜索文章
                searchKey = etSearch.getText().toString().trim();
                showRefreshView(true);
                mPresenter.searchWechatArticle(wechatId, 1, searchKey);
                break;
            default:
                break;
        }
    }

    /**
     * 显示公众号文章列表
     *
     * @param article
     * @param loadType 类型：刷新或加载更多
     */
    @Override
    public void setArticleList(Article article, int loadType) {
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
     * 收藏文章成功
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
     * 取消收藏文章成功
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
                } else {
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

    /**
     * 登录或退出登录刷新
     */
    @Subscribe(code = Constant.RX_BUS_CODE_LOGIN, threadMode = ThreadMode.MAIN)
    public void refreshProject() {
        mPresenter.refresh();
    }
}

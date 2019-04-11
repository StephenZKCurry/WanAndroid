package com.zk.wanandroid.ui.hotsearch;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.activity.BaseMVPActivity;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.bean.CommonWeb;
import com.zk.wanandroid.db.SearchHistory;
import com.zk.wanandroid.rxbus.Subscribe;
import com.zk.wanandroid.rxbus.ThreadMode;
import com.zk.wanandroid.ui.article.ArticleAdapter;
import com.zk.wanandroid.ui.article.ArticleContentActivity;
import com.zk.wanandroid.ui.mine.LoginActivity;
import com.zk.wanandroid.utils.ActivityUtils;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description: 搜索文章页面
 * @author: zhukai
 * @date: 2018/3/7 16:24
 */
public class SearchActivity extends BaseMVPActivity<SearchContract.SearchPresenter, SearchContract.ISearchModel>
        implements SearchContract.ISearchView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.ll_hot)
    LinearLayout llHot; // 搜索热词和搜索历史布局
    @BindView(R.id.tfl_hot_search)
    TagFlowLayout mTflHotSearch; // 搜索热词
    @BindView(R.id.tv_clear)
    TextView tvClear; // 清空搜索历史
    @BindView(R.id.rv_history)
    RecyclerView rvHistory; // 搜索历史
    @BindView(R.id.ll_article)
    LinearLayout llArticle; // 搜索结果布局
    @BindView(R.id.refresh_article)
    SwipeRefreshLayout mSwipeRefreshLayout; // 下拉刷新
    @BindView(R.id.rv_article)
    RecyclerView rvArticle; // 搜索结果

    private SearchView mSearchView;
    private CommonWebAdapter hotAdapter; // 搜索热词adapter
    private SearchHistroyAdapter histroyAdapter; // 搜索历史adapter
    private ArticleAdapter articleAdapter; // 文章列表adapter

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    /**
     * 初始化页面
     */
    @Override
    protected void initView() {
        super.initView();
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.color_main));
        rvHistory.setLayoutManager(new LinearLayoutManager(mContext));
        rvArticle.setLayoutManager(new LinearLayoutManager(mContext));
        articleAdapter = new ArticleAdapter(null);
        // 设置空数据显示
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, null, false);
        articleAdapter.setEmptyView(emptyView);
        rvArticle.setAdapter(articleAdapter);
        histroyAdapter = new SearchHistroyAdapter(null);
        rvHistory.setAdapter(histroyAdapter);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        super.initData();
        // 获取搜索热词
        mPresenter.loadHotSearch();
        // 查询搜索历史
        mPresenter.loadHistory();
    }

    /**
     * 初始化事件
     */
    @Override
    protected void initEvent() {
        super.initEvent();
        mTflHotSearch.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String keyWord = hotAdapter.getItem(position).getName();
                mSearchView.clearFocus();
                // 将文字填入搜索框
                mSearchView.setQuery(keyWord, true);
                return false;
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(this);
        articleAdapter.setOnLoadMoreListener(this);
        articleAdapter.setOnItemClickListener(this);
        articleAdapter.setOnItemChildClickListener(this);
        histroyAdapter.setOnItemClickListener(this);
        histroyAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        // 通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        // 关联检索配置与 SearchActivity
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(
                new ComponentName(mContext, SearchActivity.class));
        mSearchView.setSearchableInfo(searchableInfo);
        mSearchView.onActionViewExpanded();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 提交按钮的点击事件
            @Override
            public boolean onQueryTextSubmit(String query) {
                showRefreshView(true);
                mPresenter.loadSearchArticles(query, 0);
                mPresenter.addHistory(query);
                llHot.setVisibility(View.GONE);
                llArticle.setVisibility(View.VISIBLE);
                return true;
            }

            // 当输入框内容改变的时候回调
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return SearchPresenter.newInstance();
    }

    /**
     * 显示搜索热词
     *
     * @param commonWebs
     */
    @Override
    public void setHotSearch(List<CommonWeb> commonWebs) {
        hotAdapter = new CommonWebAdapter(this, commonWebs);
        mTflHotSearch.setAdapter(hotAdapter);
    }

    /**
     * 显示搜索文章列表
     *
     * @param article
     * @param loadType
     */
    @Override
    public void showSearchArticle(Article article, int loadType) {
        switch (loadType) {
            case Constant.TYPE_REFRESH_SUCCESS:
                // 刷新
                articleAdapter.setNewData(article.getDatas());
                break;
            case Constant.TYPE_LOAD_MORE_SUCCESS:
                // 加载更多
                articleAdapter.addData(article.getDatas());
                break;
            default:
                break;
        }
        if (article.getDatas() == null || article.getDatas().isEmpty() ||
                article.getDatas().size() < Constant.PAGE_SIZE) {
            articleAdapter.loadMoreEnd();
        } else {
            articleAdapter.loadMoreComplete();
        }
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
     * 显示搜索历史
     *
     * @param searchHistories
     */
    @Override
    public void setHistory(List<SearchHistory> searchHistories) {
        histroyAdapter.setNewData(searchHistories);
    }

    /**
     * 刷新搜索历史
     */
    @Override
    public void refreshHistory() {
        mPresenter.loadHistory();
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
        articleAdapter.setData(position, article);
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
        articleAdapter.setData(position, article);
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
        if (adapter instanceof SearchHistroyAdapter) {
            String keyWord = histroyAdapter.getItem(position).getName();
            mSearchView.clearFocus();
            // 将文字填入搜索框
            mSearchView.setQuery(keyWord, true);
        } else if (adapter instanceof ArticleAdapter) {
            // 跳转文章详情页面
            ArticleContentActivity.runActivity(mContext, articleAdapter.getItem(position).getLink(),
                    articleAdapter.getItem(position).getTitle());
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_close:
                // 删除单条搜索记录
                mPresenter.deleteHistoryByKey(histroyAdapter.getItem(position).getId());
                break;
            case R.id.iv_collect:
                String username = SpUtils.getString(mContext, Constant.USER_NAME, "");
                if (TextUtils.isEmpty(username)) {
                    // 未登录，跳转登录页面
                    showToast(getString(R.string.collection_no_login));
                    ActivityUtils.startActivity(mContext, new Intent(mContext, LoginActivity.class));
                } else {
                    // 收藏（取消收藏）文章
                    Article.DatasBean article = articleAdapter.getItem(position);
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
     * 清空历史记录
     */
    @OnClick(R.id.tv_clear)
    public void clear() {
        new MaterialDialog.Builder(mContext)
                .content(R.string.delete_all_search_history)
                .positiveText(R.string.dialog_confirm)
//                .positiveColor(ContextCompat.getColor(mContext, R.color.color_main))
                .negativeText(R.string.dialog_cancel)
//                .negativeColor(ContextCompat.getColor(mContext, R.color.font_default))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        // 清空搜索历史
                        mPresenter.deleteAllHistory();
                    }
                }).show();
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isFocused()) {
            mSearchView.clearFocus();
        } else {
            if (llArticle.getVisibility() != View.GONE) {
                // 关闭搜索结果
                mSearchView.setQuery("", false);
                mSearchView.clearFocus();
                llArticle.setVisibility(View.GONE);
                llHot.setVisibility(View.VISIBLE);
                // 清空文章列表数据，这里暂时调用该方法
                articleAdapter.setNewData(null);
            } else {
                finish();
            }
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


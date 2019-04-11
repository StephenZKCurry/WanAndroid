package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.activity.BaseMVPActivity;
import com.zk.wanandroid.bean.Article;
import com.zk.wanandroid.rxbus.Subscribe;
import com.zk.wanandroid.rxbus.ThreadMode;
import com.zk.wanandroid.ui.article.ArticleAdapter;
import com.zk.wanandroid.ui.article.ArticleContentActivity;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.widgets.AddCollectionDialog;

import butterknife.BindView;

/**
 * @description: 我的收藏页面
 * @author: zhukai
 * @date: 2018/3/13 17:01
 */
public class MyCollectionActivity extends BaseMVPActivity<MyCollectionContract.MyCollectionPresenter, MyCollectionContract.IMyCollectionModel>
        implements MyCollectionContract.IMyCollectionView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_mycollection)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_mycollection)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArticleAdapter mAdapter;
    private AddCollectionDialog mDialog; // 添加收藏弹窗

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mycollection;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_collection, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            // 添加站外网站收藏
            mDialog = new AddCollectionDialog(mContext, Constant.DIALOG_ADD_COLLECTION);
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        super.initView();
        getSupportActionBar().setTitle(getString(R.string.item_collection));
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
        onRefresh();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    /**
     * 显示我的收藏文章列表
     *
     * @param article
     * @param loadType 类型：刷新或加载更多
     */
    @Override
    public void setMyCollection(Article article, int loadType) {
        // 我的收藏列表返回json中没有collect字段，需要设置为true
        for (Article.DatasBean datasBean : article.getDatas()) {
            datasBean.setCollect(true);
        }
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
     * 取消收藏文章成功
     *
     * @param position 文章在列表中的position，用于更新数据
     */
    @Override
    public void cancelCollectArticleSuccess(int position) {
        // 更新数据
        mAdapter.remove(position);
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return MyCollectionPresenter.newInstance();
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

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

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
                // 取消收藏
                Article.DatasBean article = mAdapter.getItem(position);
                mPresenter.cancelCollectArticle(position, article);
                break;
            default:
                break;
        }
    }

    /**
     * 确定添加收藏
     */
    @Subscribe(code = Constant.RX_BUS_CODE_ADD_COLLECTION, threadMode = ThreadMode.MAIN)
    public void addCollection() {
        String title = mDialog.etTitle.getText().toString().trim();
        String author = mDialog.etAuthor.getText().toString().trim();
        String link = mDialog.etLink.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast(getString(R.string.hint_title));
        } else if (TextUtils.isEmpty(author)) {
            showToast(getString(R.string.hint_author));
        } else if (TextUtils.isEmpty(link)) {
            showToast(getString(R.string.hint_link));
        } else {
            mDialog.dismiss();
            mPresenter.addOutsideCollectArticle(title, author, link);
        }
    }
}

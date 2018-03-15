package com.zk.wanandroid.ui.mine;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.activity.BaseMVPActivity;
import com.zk.wanandroid.bean.Bookmark;
import com.zk.wanandroid.rxbus.Subscribe;
import com.zk.wanandroid.rxbus.ThreadMode;
import com.zk.wanandroid.ui.article.ArticleAdapter;
import com.zk.wanandroid.ui.article.ArticleContentActivity;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.widgets.AddCollectionDialog;

import java.util.List;

import butterknife.BindView;

/**
 * @description: 我的书签页面
 * @author: zhukai
 * @date: 2018/3/14 17:26
 */
public class MyBookmarkActivity extends BaseMVPActivity<MyBookmarkContract.MyBookmarkPresenter, MyBookmarkContract.IMyBookmarkModel>
        implements MyBookmarkContract.IMyBookmarkView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_mybookmark)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_mybookmark)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Menu mMenu; // 功能菜单
    private MyBookmarkAdapter mAdapter;
    private AddCollectionDialog mDialog; // 添加书签弹窗
    private int editPosition; // 修改书签在列表中的position

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mybookmark;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_collection, menu);
        menu.getItem(1).setVisible(true); // 显示编辑
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                // 添加网站收藏
                mDialog = new AddCollectionDialog(mContext, Constant.DIALOG_ADD_BOOKMARK);
                if (!mDialog.isShowing()) {
                    mDialog.show();
                }
                break;
            case R.id.menu_edit:
                if (mAdapter.isEditable()) {
                    mAdapter.setEditable(false);
                    item.setIcon(R.drawable.ic_vector_edit);
                    mMenu.getItem(0).setVisible(true); // 显示添加书签
                } else {
                    mAdapter.setEditable(true);
                    item.setIcon(R.drawable.ic_vector_check);
                    mMenu.getItem(0).setVisible(false); // 隐藏添加书签
                }
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        super.initView();
        getSupportActionBar().setTitle(getString(R.string.item_bookmark));
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_main));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MyBookmarkAdapter(null);
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
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return MyBookmarkPresenter.newInstance();
    }

    /**
     * 显示我的书签列表
     *
     * @param bookmarks
     */
    @Override
    public void setMyBookmark(List<Bookmark> bookmarks) {
        mAdapter.setNewData(bookmarks);
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
     * 编辑书签成功
     *
     * @param position 书签在列表中的position，用于更新数据
     * @param bookmark 编辑后的书签
     */
    @Override
    public void editBookmarkSuccess(int position, Bookmark bookmark) {
        // 更新数据
        mAdapter.setData(position, bookmark);
    }

    /**
     * 删除书签成功
     *
     * @param position 书签在列表中的position，用于更新数据
     */
    @Override
    public void deleteBookmarkSuccess(int position) {
        // 更新数据
        mAdapter.remove(position);
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        mPresenter.loadMyBookmark();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 跳转网站页面
        ArticleContentActivity.runActivity(mContext, mAdapter.getItem(position).getLink(), mAdapter.getItem(position).getName());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
        switch (view.getId()) {
            case R.id.iv_edit:
                // 编辑书签
                editPosition = position;
                mDialog = new AddCollectionDialog(mContext, Constant.DIALOG_EDIT_BOOKMARK);
                mDialog.setBookmark(mAdapter.getItem(position));
                if (!mDialog.isShowing()) {
                    mDialog.show();
                }
                break;
            case R.id.iv_delete:
                new MaterialDialog.Builder(mContext)
                        .content(R.string.confirm_bookmark_delete)
                        .positiveText(R.string.dialog_confirm)
                        .positiveColor(mContext.getResources().getColor(R.color.color_main))
                        .negativeText(R.string.dialog_cancel)
                        .negativeColor(mContext.getResources().getColor(R.color.font_default))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                // 删除书签
                                mPresenter.deleteBookmark(position, mAdapter.getItem(position).getId());
                            }
                        }).show();
                break;
            default:
                break;
        }
    }

    /**
     * 确定添加书签
     */
    @Subscribe(code = Constant.RX_BUS_CODE_ADD_BOOKMARK, threadMode = ThreadMode.MAIN)
    public void addBookmark() {
        String name = mDialog.etName.getText().toString().trim();
        String link = mDialog.etLink.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast(getString(R.string.hint_name));
        } else if (TextUtils.isEmpty(link)) {
            showToast(getString(R.string.hint_link));
        } else {
            mDialog.dismiss();
            mPresenter.addBookmark(name, link);
        }
    }

    /**
     * 编辑书签
     */
    @Subscribe(code = Constant.RX_BUS_CODE_EDIT_BOOKMARK, threadMode = ThreadMode.MAIN)
    public void editBookmark() {
        Bookmark bookmark = mDialog.getBookmark();
        if (TextUtils.isEmpty(bookmark.getName())) {
            showToast(getString(R.string.hint_name));
        } else if (TextUtils.isEmpty(bookmark.getLink())) {
            showToast(getString(R.string.hint_link));
        } else {
            mDialog.dismiss();
            mPresenter.editBookmark(editPosition, bookmark);
        }
    }
}

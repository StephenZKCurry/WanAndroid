package com.zk.wanandroid.ui.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.fragment.BaseMVPFragment;
import com.zk.wanandroid.bean.Navigation;

import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * @description: 导航网站Fragment
 * @author: zhukai
 * @date: 2018/3/5 10:51
 */
public class NavigationFragment extends BaseMVPFragment<NavigationContract.NavigationPresenter, NavigationContract.INavigationModel>
        implements NavigationContract.INavigationView {

    @BindView(R.id.tl_tabs)
    VerticalTabLayout mTabLayout;
    @BindView(R.id.rv_tabs)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private NavigationTabAdapter mAdapter;

    private int lastScrollState; // RecyclerView上一个滑动状态

    public static NavigationFragment newInstance() {
        Bundle args = new Bundle();
        NavigationFragment fragment = new NavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getNavigation();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                // RecyclerView滑动到对应position
                mRecyclerView.scrollToPosition(position);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastScrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                    // 由于设置了SnapHelper，RecyclerView在滑动距离不足时会有一个回弹效果
                    // 此时滑动状态的变化情况为SCROLL_STATE_DRAGGING→SCROLL_STATE_IDLE→SCROLL_STATE_SETTLING→SCROLL_STATE_IDLE
                    // 滑动方向改变，因此滑动状态会有两次变为SCROLL_STATE_IDLE，但这里实际需要的是最后RecyclerView完全停止滑动的状态
                    // 因此添加了判断lastScrollState == RecyclerView.SCROLL_STATE_SETTLING
                    // 如果滑动距离够大的话不会产生回弹，滑动状态的变化情况为SCROLL_STATE_DRAGGING→SCROLL_STATE_SETTLING→SCROLL_STATE_IDLE，同样满足条件
                    mTabLayout.setTabSelected(mLayoutManager.findFirstVisibleItemPosition());
                }
                lastScrollState = newState;
            }
        });
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return NavigationPresenter.newInstance();
    }

    /**
     * 显示导航数据
     *
     * @param navigations
     */
    @Override
    public void setNavigation(final List<Navigation> navigations) {
        mTabLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_tab_backgroung));
        for (Navigation navigation : navigations) {
            // 添加Tab
            QTabView tabView = new QTabView(mContext);
            ITabView.TabTitle tabTitle = new ITabView.TabTitle.Builder()
                    .setContent(navigation.getName())
                    .setTextColor(ContextCompat.getColor(mContext, R.color.color_tab_active),
                            ContextCompat.getColor(mContext, R.color.color_tab_unactive))
                    .build();
            tabView.setTitle(tabTitle);
            mTabLayout.addTab(tabView);
        }
        mAdapter = new NavigationTabAdapter(mContext, navigations);
        mRecyclerView.setAdapter(mAdapter);
    }
}

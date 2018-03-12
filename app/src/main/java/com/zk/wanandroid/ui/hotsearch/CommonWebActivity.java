package com.zk.wanandroid.ui.hotsearch;

import android.support.annotation.NonNull;
import android.view.View;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.activity.BaseMVPActivity;
import com.zk.wanandroid.bean.CommonWeb;
import com.zk.wanandroid.ui.article.ArticleContentActivity;

import java.util.List;

import butterknife.BindView;

/**
 * @description: 常用网站页面
 * @author: zhukai
 * @date: 2018/3/7 14:33
 */
public class CommonWebActivity extends BaseMVPActivity<CommonWebContract.CommonWebPresenter, CommonWebContract.ICommonWebModel>
        implements CommonWebContract.ICommonWebView {

    @BindView(R.id.tfl_common_web)
    TagFlowLayout mTflCommonWeb;

    private CommonWebAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_common_web;
    }

    /**
     * 初始化页面
     */
    @Override
    protected void initView() {
        super.initView();
        getSupportActionBar().setTitle(getString(R.string.common_website));
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        super.initData();
        // 查询常用网站
        mPresenter.loadCommonWeb();
    }

    /**
     * 初始化事件
     */
    @Override
    protected void initEvent() {
        super.initEvent();
        mTflCommonWeb.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                // 跳转网站详情页
                ArticleContentActivity.runActivity(mContext, mAdapter.getItem(position).getLink(),
                        mAdapter.getItem(position).getName());
                return false;
            }
        });
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return CommonWebPresenter.newInstance();
    }

    /**
     * 显示常用网站
     *
     * @param commonWebs
     */
    @Override
    public void setCommonWeb(List<CommonWeb> commonWebs) {
        mAdapter = new CommonWebAdapter(mContext, commonWebs);
        mTflCommonWeb.setAdapter(mAdapter);
    }
}

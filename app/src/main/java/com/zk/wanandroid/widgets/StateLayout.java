package com.zk.wanandroid.widgets;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zk.wanandroid.R;

/**
 * @description: 统一状态管理布局
 * @author: zhukai
 * @date: 2018/12/3 13:49
 */
public class StateLayout extends FrameLayout {

    private View mContentView;
    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mNoNetworkView;

    public final static int TYPE_CONTENT = 0x00; // 内容
    public final static int TYPE_EMPTY = 0x02; // 空数据
    public final static int TYPE_LOADING = 0x01; // 加载中
    public final static int TYPE_ERROR = 0x03; // 发生错误
    public final static int TYPE_NO_NETWORK = 0x04; // 无网络

    public StateLayout(@NonNull Context context) {
        super(context);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 显示内容视图
     */
    public void showContent() {
        showStateView(TYPE_CONTENT);
    }

    /**
     * 显示加载中视图
     */
    public void showLoading() {
        showStateView(TYPE_LOADING);
    }

    /**
     * 显示空数据视图
     */
    public void showEmpty() {
        showStateView(TYPE_EMPTY);
    }

    /**
     * 显示错误视图
     */
    public void showError() {
        showStateView(TYPE_ERROR);
    }

    /**
     * 显示无网络视图
     */
    public void showNoNetwork() {
        showStateView(TYPE_NO_NETWORK);
    }

    /**
     * 显示状态视图
     *
     * @param state 当前状态
     */
    private void showStateView(int state) {
        hideAllView();
        switch (state) {
            case TYPE_CONTENT:
                mContentView.setVisibility(VISIBLE);
                break;
            case TYPE_LOADING:
                mLoadingView.setVisibility(VISIBLE);
                break;
            case TYPE_EMPTY:
                mEmptyView.setVisibility(VISIBLE);
                break;
            case TYPE_ERROR:
                mErrorView.setVisibility(VISIBLE);
                break;
            case TYPE_NO_NETWORK:
                mNoNetworkView.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * 隐藏所有视图
     */
    private void hideAllView() {
        if (mContentView != null) {
            mContentView.setVisibility(GONE);
        }
        if (mLoadingView != null) {
            mLoadingView.setVisibility(GONE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(GONE);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(GONE);
        }
        if (mNoNetworkView != null) {
            mNoNetworkView.setVisibility(GONE);
        }
    }

    /**
     * 隐藏状态视图，显示内容
     */
    public void hide() {
        showContent();
    }

    /**
     * 建造者模式创建StateLayout
     */
    public static class Builder {

        private Context mContext;
        private StateLayout mStateLayout;
        private LayoutInflater mInflater;
        private OnRetryClickListener mListener;

        public Builder(Context context) {
            mContext = context;
            mStateLayout = new StateLayout(mContext);
            mInflater = LayoutInflater.from(mContext);
        }

        /**
         * 设置状态视图的根布局
         *
         * @param view
         * @return
         */
        public Builder into(View view) {
            if (view == null) {
                throw new IllegalArgumentException("view can not be null");
            }
            mStateLayout.mContentView = view;
            mStateLayout.removeAllViews();
            ViewGroup parent = (ViewGroup) view.getParent();
            int index = parent.indexOfChild(view) < 0 ? 0 : parent.indexOfChild(view);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            parent.removeView(view);
            parent.addView(mStateLayout, index, layoutParams);
            mStateLayout.addView(view);
            return this;
        }

        /**
         * 设置状态视图的根布局
         *
         * @param activity
         * @return
         */
        public Builder into(Activity activity) {
            // 获取contentView
            ViewGroup contentView = activity.findViewById(android.R.id.content);
            into(contentView.getChildAt(0));
            return this;
        }

        /**
         * 设置状态视图的根布局
         *
         * @param fragment
         * @return
         */
        public Builder into(Fragment fragment) {
            into(fragment.getView());
            return this;
        }

        /**
         * 设置状态视图的根布局
         *
         * @param viewResId
         * @return
         */
        public Builder into(int viewResId) {
            View view = mInflater.inflate(viewResId, null);
            into(view);
            return this;
        }

        /**
         * 设置加载中视图
         *
         * @param loadingResId
         * @return
         */
        public Builder loadingView(int loadingResId) {
            View loadingView = mInflater.inflate(loadingResId, mStateLayout, false);
            mStateLayout.mLoadingView = loadingView;
            mStateLayout.addView(loadingView);
            return this;
        }

        /**
         * 设置加载中视图
         *
         * @param loadingView
         * @return
         */
        public Builder loadingView(View loadingView) {
            mStateLayout.mLoadingView = loadingView;
            mStateLayout.addView(loadingView);
            return this;
        }

        /**
         * 设置空数据视图
         *
         * @param emptyResId
         * @return
         */
        public Builder emptyView(int emptyResId) {
            View emptyView = mInflater.inflate(emptyResId, mStateLayout, false);
            mStateLayout.mEmptyView = emptyView;
            mStateLayout.addView(emptyView);
            return this;
        }

        /**
         * 设置空数据视图
         *
         * @param emptyView
         * @return
         */
        public Builder emptyView(View emptyView) {
            mStateLayout.mEmptyView = emptyView;
            mStateLayout.addView(emptyView);
            return this;
        }

        /**
         * 设置错误视图
         *
         * @param errorResId
         * @return
         */
        public Builder errorView(int errorResId) {
            View errorView = mInflater.inflate(errorResId, mStateLayout, false);
            mStateLayout.mErrorView = errorView;
            mStateLayout.addView(errorView);
            return this;
        }

        /**
         * 设置错误视图
         *
         * @param errorView
         * @return
         */
        public Builder errorView(View errorView) {
            mStateLayout.mErrorView = errorView;
            mStateLayout.addView(errorView);
            return this;
        }

        /**
         * 设置无网络视图
         *
         * @param noNetworkResId
         * @return
         */
        public Builder noNetworkView(int noNetworkResId) {
            View noNetworkView = mInflater.inflate(noNetworkResId, mStateLayout, false);
            mStateLayout.mNoNetworkView = noNetworkView;
            mStateLayout.addView(noNetworkView);
            return this;
        }

        /**
         * 设置无网络视图
         *
         * @param noNetworkView
         * @return
         */
        public Builder noNetworkView(View noNetworkView) {
            mStateLayout.mNoNetworkView = noNetworkView;
            mStateLayout.addView(noNetworkView);
            return this;
        }

        public Builder onRetryListener(OnRetryClickListener listener) {
            this.mListener = listener;
            return this;
        }

        /**
         * 创建状态布局
         *
         * @return
         */
        public StateLayout create() {
            // 设置默认状态视图
            if (mStateLayout.mLoadingView == null) {
                View loadingView = mInflater.inflate(R.layout.layout_loading_view, mStateLayout, false);
                mStateLayout.mLoadingView = loadingView;
                mStateLayout.addView(loadingView);
            }
            if (mStateLayout.mEmptyView == null) {
                View emptyView = mInflater.inflate(R.layout.layout_empty_view, mStateLayout, false);
                mStateLayout.mEmptyView = emptyView;
                mStateLayout.addView(emptyView);
            }
            if (mStateLayout.mErrorView == null) {
                View errorView = mInflater.inflate(R.layout.layout_error_view, mStateLayout, false);
                mStateLayout.mErrorView = errorView;
                mStateLayout.addView(errorView);
            }
            if (mStateLayout.mNoNetworkView == null) {
                View noNetworkView = mInflater.inflate(R.layout.layout_no_network_view, mStateLayout, false);
                mStateLayout.mNoNetworkView = noNetworkView;
                mStateLayout.addView(noNetworkView);
            }
            mStateLayout.hide();
            return mStateLayout;
        }
    }

    /**
     * 点击重试监听
     */
    public interface OnRetryClickListener {
        void onRetry();
    }
}

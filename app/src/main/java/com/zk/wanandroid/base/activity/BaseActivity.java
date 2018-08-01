package com.zk.wanandroid.base.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.zk.wanandroid.R;
import com.zk.wanandroid.manager.AppManager;
import com.zk.wanandroid.rxbus.RxBus;
import com.zk.wanandroid.utils.ActivityUtils;
import com.zk.wanandroid.utils.ToastUtils;
import com.zk.wanandroid.widgets.WaitPorgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @description: 普通Activity基类
 * @author: zhukai
 * @date: 2018/3/2 11:46
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected WaitPorgressDialog mWaitPorgressDialog;
    protected Toolbar mToolbar;
    protected String TAG;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
        mContext = this;
        TAG = getClass().getSimpleName();
        Log.d(TAG, "onCreate()");
        setContentView(getContentViewId());
        RxBus.get().register(this); // 注册RxBus
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        AppManager.getAppManager().finishActivity(this);
        RxBus.get().unRegister(mContext); // 取消注册RxBus
        unbinder.unbind();
        Log.d(TAG, "onDestroy()");
    }

    /**
     * 设置布局资源id
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 初始化页面
     */
    protected void initView() {
        unbinder = ButterKnife.bind(this);
        initToolBar();
        mWaitPorgressDialog = new WaitPorgressDialog(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new NullPointerException("toolbar can not be null");
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp());
        // toolbar除掉阴影
        getSupportActionBar().setElevation(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setElevation(0);
        }
    }

    /**
     * toolbar是否显示返回键
     *
     * @return
     */
    protected boolean showHomeAsUp() {
        return false;
    }

    /**
     * 点击toolbar返回
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityUtils.finishActivity(mContext);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    finishAfterTransition();
//                } else {
//                    finish();
//                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 弹出Toast
     *
     * @param msg 要显示的toast消息字符串
     */
    protected void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    /**
     * 显示提示框
     *
     * @param msg 等待消息字符串
     */
    protected void showProgressDialog(String msg) {
        if (mWaitPorgressDialog.isShowing()) {
            mWaitPorgressDialog.dismiss();
        }
        mWaitPorgressDialog.setMessage(msg);
        mWaitPorgressDialog.show();
    }

    /**
     * 隐藏提示框
     */
    protected void hideProgressDialog() {
        if (mWaitPorgressDialog != null) {
            mWaitPorgressDialog.dismiss();
        }
    }

    /**
     * 隐藏软键盘
     *
     * @return true:隐藏成功 false:隐藏失败
     */
    protected boolean hiddenKeyboard() {
        // 点击空白位置 隐藏软键盘
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService
                (INPUT_METHOD_SERVICE);
        return mInputMethodManager.hideSoftInputFromWindow(this
                .getCurrentFocus().getWindowToken(), 0);
    }
}

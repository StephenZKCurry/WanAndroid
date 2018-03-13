package com.zk.wanandroid.ui.mine;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.activity.BaseMVPActivity;
import com.zk.wanandroid.bean.User;
import com.zk.wanandroid.rxbus.RxBus;
import com.zk.wanandroid.utils.ActivityUtils;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.SpUtils;
import com.zk.wanandroid.widgets.ClearEditText;

import butterknife.BindView;

/**
 * @description: 登录页面
 * @author: zhukai
 * @date: 2018/3/8 16:29
 */
public class LoginActivity extends BaseMVPActivity<LoginContract.LoginPresenter, LoginContract.ILoginModel>
        implements LoginContract.ILoginView, View.OnClickListener {

    @BindView(R.id.et_login_username)
    ClearEditText etLoginUsername;
    @BindView(R.id.et_login_pwd)
    ClearEditText etLoginPwd;
    @BindView(R.id.tv_login)
    RoundTextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return LoginPresenter.newInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                String username = etLoginUsername.getText().toString().trim();
                String password = etLoginPwd.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    showToast(getString(R.string.register_name));
                } else if (TextUtils.isEmpty(password)) {
                    showToast(getString(R.string.register_password));
                } else {
                    mPresenter.doLogin(username, password);
                }
                break;
            case R.id.tv_register:
                // 跳转注册页面
                ActivityUtils.startActivity(mContext, new Intent(mContext, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 登录成功
     *
     * @param user 用户信息
     */
    @Override
    public void showLoginSuccess(User user) {
        // 保存用户信息
        SpUtils.setString(mContext, Constant.USER_ID, user.getId() + "");
        SpUtils.setString(mContext, Constant.USER_NAME, user.getUsername());
//        SpUtils.setString(mContext, Constant.USER_PASSWORD, user.getPassword());
        // 登录成功通知其他页面刷新
        RxBus.get().send(Constant.RX_BUS_CODE_LOGIN);
        ActivityUtils.finishActivity(mContext);
    }

    /**
     * 登录失败
     *
     * @param message 失败信息
     */
    @Override
    public void showLoginFaild(String message) {
        showToast(message);
    }
}

package com.zk.wanandroid.ui.mine;

import android.os.Build;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.activity.BaseMVPActivity;
import com.zk.wanandroid.widgets.ClearEditText;

import butterknife.BindView;

/**
 * @description: 登录页面
 * @author: zhukai
 * @date: 2018/3/8 16:29
 */
public class LoginActivity extends BaseMVPActivity<LoginContract.LoginPresenter, LoginContract.ILoginModel>
        implements LoginContract.ILoginView {

    @BindView(R.id.et_login_phone)
    ClearEditText etLoginPhone;
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
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return LoginPresenter.newInstance();
    }
}

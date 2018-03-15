package com.zk.wanandroid.ui.mine;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.activity.BaseMVPActivity;
import com.zk.wanandroid.bean.User;
import com.zk.wanandroid.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description: 注册页面
 * @author: zhukai
 * @date: 2018/3/12 15:50
 */
public class RegisterActivity extends BaseMVPActivity<RegisterContract.RegisterPresenter, RegisterContract.IRegisterModel>
        implements RegisterContract.IRegisterView {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repassword)
    EditText etRepassword;
    @BindView(R.id.tv_register)
    RoundTextView tvRegister;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        super.initView();
        getSupportActionBar().setTitle(getString(R.string.login_register));
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return RegisterPresenter.newInstance();
    }

    /**
     * 注册
     */
    @OnClick(R.id.tv_register)
    public void doRegister() {
        String username = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String repassword = etRepassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            showToast(getString(R.string.register_name));
        } else if (TextUtils.isEmpty(password)) {
            showToast(getString(R.string.register_password));
        } else if (TextUtils.isEmpty(repassword)) {
            showToast(getString(R.string.register_repassword));
        } else if (!password.equals(repassword)) {
            showToast(getString(R.string.password_different));
        } else {
            // 注册
            mPresenter.doRegister(username, password, repassword);
        }
    }

    /**
     * 注册成功
     *
     * @param user 用户信息
     */
    @Override
    public void showRegisterSuccess(User user) {
        View dialog = getLayoutInflater().inflate(R.layout.dialog_register_success, (ViewGroup) findViewById(R.id.dialog));
        // 注册成功信息
        TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_message);
        tvMessage.setText(getString(R.string.dialog_register_success) + "\n用户名：" + user.getUsername()
                + "\n密码：" + user.getPassword());
        // 确定
        TextView tvSubmit = (TextView) dialog.findViewById(R.id.tv_submit);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.finishActivity(mContext);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialog);
        builder.show();
    }

    /**
     * 注册失败
     *
     * @param message 失败信息
     */
    @Override
    public void showRegisterFaild(String message) {
        showToast(message);
    }
}

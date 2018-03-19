package com.zk.wanandroid.ui;

import com.zk.wanandroid.R;
import com.zk.wanandroid.base.activity.BaseActivity;

/**
 * @description: 关于项目页面
 * @author: zhukai
 * @date: 2018/3/15 14:09
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }
}

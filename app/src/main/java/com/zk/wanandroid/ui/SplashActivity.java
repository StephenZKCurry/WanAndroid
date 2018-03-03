package com.zk.wanandroid.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zk.wanandroid.utils.ActivityUtils;
import com.zk.wanandroid.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @description: app启动闪屏页
 * @author: zhukai
 * @date: 2018/3/2 11:15
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 申请运行时权限
        requestPermissions();
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(SplashActivity.this);
        // 请求权限全部结果
        rxPermission.request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (!granted) {
                            ToastUtils.showToast("App未能获取全部需要的相关权限，部分功能可能不能正常使用");
                        }
                        // 不管是否获取全部权限，进入主页面
                        Observable.timer(2, TimeUnit.SECONDS)
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        jumpNextPage();
                                    }
                                });
                    }
                });
    }

    /**
     * 跳转下一个页面
     */
    private void jumpNextPage() {
        ActivityUtils.startActivity(this, new Intent(this, MainActivity.class));
        finish();
    }

}


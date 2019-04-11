package com.zk.wanandroid.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zk.wanandroid.utils.ActivityUtils;
import com.zk.wanandroid.utils.NotchUtils;
import com.zk.wanandroid.utils.RomUtils;
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
        // 适配刘海屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Android P利用官方提供的API适配
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            // 始终允许窗口延伸到屏幕短边上的缺口区域
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        } else {
            // Android P以下根据手机厂商的适配方案进行适配
            if (RomUtils.isHuawei() && NotchUtils.hasNotchAtHuawei(this)) {
                NotchUtils.setFullScreenAtHuawei(getWindow());
            } else if (RomUtils.isXiaomi() && NotchUtils.hasNotchAtXiaomi(this)) {
                NotchUtils.setFullScreenAtXiaomi(getWindow());
            }
        }
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


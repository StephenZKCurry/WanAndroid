package com.zk.wanandroid.base;

import android.app.Application;
import android.content.Context;

/**
 * @description: app初始化
 * @author: zhukai
 * @date: 2018/2/27 13:04
 */
public class App extends Application {

    private static Context mContext;
    private static App application;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        application = this;
    }

    public static Context getContext() {
        return mContext;
    }

    public static App getApplication() {
        return application;
    }

}

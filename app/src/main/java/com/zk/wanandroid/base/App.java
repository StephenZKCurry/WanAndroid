package com.zk.wanandroid.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zk.wanandroid.db.DaoMaster;
import com.zk.wanandroid.db.DaoSession;

/**
 * @description: app初始化
 * @author: zhukai
 * @date: 2018/2/27 13:04
 */
public class App extends Application {

    private static Context mContext;
    private static App application;
    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        application = this;
        setDatabase();
    }

    public static Context getContext() {
        return mContext;
    }

    public static App getApplication() {
        return application;
    }

    /**
     * 初始化数据库
     */
    private void setDatabase() {
        // 创建数据库
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "searchhistory.db", null);
        // 获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        // 获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        // 获取Dao对象管理者
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

}

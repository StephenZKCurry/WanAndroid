package com.zk.wanandroid.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.zk.wanandroid.db.DaoMaster;
import com.zk.wanandroid.db.DaoSession;
import com.zk.wanandroid.utils.ApkUtils;
import com.zk.wanandroid.utils.Constant;

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
        initBugly();
        setDatabase();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        // 安装tinker
        Beta.installTinker();
    }

    public static Context getContext() {
        return mContext;
    }

    public static App getApplication() {
        return application;
    }

    /**
     * 初始化Bugly
     */
    private void initBugly() {
        // 获取当前包名
        String packageName = getApplicationContext().getPackageName();
        // 获取当前进程名
        String processName = ApkUtils.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), Constant.BUGLY_ID, false, strategy);
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

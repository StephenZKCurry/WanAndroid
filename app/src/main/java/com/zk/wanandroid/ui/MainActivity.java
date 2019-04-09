package com.zk.wanandroid.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zk.wanandroid.R;
import com.zk.wanandroid.base.activity.BaseActivity;
import com.zk.wanandroid.rxbus.RxBus;
import com.zk.wanandroid.rxbus.Subscribe;
import com.zk.wanandroid.rxbus.ThreadMode;
import com.zk.wanandroid.ui.article.ArticleContentActivity;
import com.zk.wanandroid.ui.home.HomeFragment;
import com.zk.wanandroid.ui.hotsearch.CommonWebActivity;
import com.zk.wanandroid.ui.hotsearch.SearchActivity;
import com.zk.wanandroid.ui.knowledgesystem.KnowledgesystemFragment;
import com.zk.wanandroid.ui.mine.LoginActivity;
import com.zk.wanandroid.ui.mine.MyBookmarkActivity;
import com.zk.wanandroid.ui.mine.MyCollectionActivity;
import com.zk.wanandroid.ui.navigation.NavigationFragment;
import com.zk.wanandroid.ui.project.ProjectFragment;
import com.zk.wanandroid.utils.ActivityUtils;
import com.zk.wanandroid.utils.Constant;
import com.zk.wanandroid.utils.NavigationUtils;
import com.zk.wanandroid.utils.SpUtils;
import com.zk.wanandroid.utils.SystemStatusManager;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @description: 主页面
 * @author: zhukai
 * @date: 2018/3/2
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.dl_root)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nv_menu)
    NavigationView mNavigationView;
    @BindView(R.id.bnv_bar)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBar;
    @BindView(R.id.fab_hot)
    FloatingActionButton mFloatingActionButton;

    private CircleImageView civ_head;
    private TextView tv_name;

    private HomeFragment homefragment;
    private KnowledgesystemFragment knowledgesystemFragment;
    private NavigationFragment navigationFragment;
    private ProjectFragment projectFragment;
    private int position; // 当前选中位置
    private static final String POSITION = "position";
    private static final String SELECT_ITEM = "bottomNavigationSelectItem";
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_KNOWLEDGESYSTEM = 1;
    private static final int FRAGMENT_NAVIGATION = 2;
    private static final int FRAGMENT_PROJECT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            homefragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            knowledgesystemFragment = (KnowledgesystemFragment) getSupportFragmentManager().findFragmentByTag(KnowledgesystemFragment.class.getName());
            navigationFragment = (NavigationFragment) getSupportFragmentManager().findFragmentByTag(NavigationFragment.class.getName());
            projectFragment = (ProjectFragment) getSupportFragmentManager().findFragmentByTag(ProjectFragment.class.getName());
            // 恢复recreate前的位置
            showFragment(savedInstanceState.getInt(POSITION));
            mBottomNavigationView.setSelectedItemId(savedInstanceState.getInt(SELECT_ITEM));
        } else {
            // 默认显示首页
            showFragment(FRAGMENT_HOME);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // recreate时记录当前位置(在Manifest已禁止Activity旋转,所以旋转屏幕并不会执行以下代码)
        outState.putInt(POSITION, position);
        outState.putInt(SELECT_ITEM, mBottomNavigationView.getSelectedItemId());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置透明状态栏
        new SystemStatusManager(this).setTranslucentStatus(R.color.transparent);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开侧滑菜单
                if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        // 去除NavigationView中menu的滚动条
        NavigationUtils.disableNavigationViewScrollbars(mNavigationView);
        tv_name = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.tv_name);
        civ_head = (CircleImageView) mNavigationView.getHeaderView(0).findViewById(R.id.civ_head);
    }

    @Override
    protected void initData() {
        super.initData();
        // 初始化用户信息
        setUserInfo();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_home:
                        mToolbar.setTitle(getString(R.string.app_name));
                        showFragment(FRAGMENT_HOME);
                        break;
                    case R.id.menu_item_knowledgesystem:
                        mToolbar.setTitle(getString(R.string.title_knowledgesystem));
                        showFragment(FRAGMENT_KNOWLEDGESYSTEM);
                        break;
                    case R.id.menu_item_navigation:
                        mToolbar.setTitle(getString(R.string.title_navigation));
                        showFragment(FRAGMENT_NAVIGATION);
                        break;
                    case R.id.menu_item_project:
                        mToolbar.setTitle(getString(R.string.title_project));
                        showFragment(FRAGMENT_PROJECT);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.group_item_home:
                        // 显示主页
                        mBottomNavigationView.setSelectedItemId(R.id.menu_item_home);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
//                    case R.id.group_item_history:
//
//                        break;
                    case R.id.group_item_collection:
                        // 跳转我的收藏页面
                        ActivityUtils.startActivity(mContext, new Intent(mContext, MyCollectionActivity.class));
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.group_item_bookmark:
                        // 跳转我的书签页面
                        ActivityUtils.startActivity(mContext, new Intent(mContext, MyBookmarkActivity.class));
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.group_item_github:
                        // 跳转项目主页
                        ArticleContentActivity.runActivity(mContext, "https://github.com/StephenZKCurry/WanAndroid", "WanAndroid");
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.group_item_share_project:
                        // 分享项目
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_article_url, getString(R.string.app_name), "https://github.com/StephenZKCurry/WanAndroid", "这个App贼好用，快下载体验吧~"));
                        intent.setType("text/plain");
                        startActivity(intent);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.group_item_setting:
                        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                            @Override
                            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                            }

                            @Override
                            public void onDrawerOpened(@NonNull View drawerView) {

                            }

                            @Override
                            public void onDrawerClosed(@NonNull View drawerView) {
                                // 切换夜间主题
                                switchNight(SpUtils.getBoolean(mContext, Constant.NIGHT_MODEL, false));
                            }

                            @Override
                            public void onDrawerStateChanged(int newState) {

                            }
                        });
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.group_item_about:
                        ActivityUtils.startActivity(mContext, new Intent(mContext, AboutActivity.class));
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.group_item_logout:
                        new MaterialDialog.Builder(mContext)
                                .content(R.string.confirm_logout)
                                .positiveText(R.string.dialog_confirm)
                                .positiveColor(mContext.getResources().getColor(R.color.color_main))
                                .negativeText(R.string.dialog_cancel)
                                .negativeColor(mContext.getResources().getColor(R.color.font_default))
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(MaterialDialog dialog, DialogAction which) {
                                        // 退出登录
//                                        SpUtils.clearSp(mContext);
                                        SpUtils.setString(mContext, Constant.USER_ID, "");
                                        SpUtils.setString(mContext, Constant.USER_NAME, "");
                                        // 清除cookies,不清除的话会保持登录状态，请求时还会携带cookie
                                        new SharedPrefsCookiePersistor(mContext).clear();
                                        // 退出登录通知其他页面刷新
                                        RxBus.get().send(Constant.RX_BUS_CODE_LOGIN);
//                                        mDrawerLayout.closeDrawer(GravityCompat.START);
                                    }
                                }).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        // 动态显示和隐藏悬浮按钮
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (position == FRAGMENT_HOME) {
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                    if (verticalOffset == 0) {
                        mFloatingActionButton.show();
                    } else {
                        mFloatingActionButton.hide();
                    }
                } else {
                    mFloatingActionButton.setVisibility(View.GONE);
                }
            }
        });

        civ_head.setOnClickListener(this);
        mFloatingActionButton.setOnClickListener(this);
    }

    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        position = index;
        switch (index) {
            case FRAGMENT_HOME:
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (homefragment == null) {
                    homefragment = HomeFragment.newInstance();
                    ft.add(R.id.fl_container, homefragment, HomeFragment.class.getName());
                    // 这里先hide再show是为了触发onHiddenChanged(),实现懒加载
                    ft.hide(homefragment);
                    ft.show(homefragment);
                } else {
                    ft.show(homefragment);
                }
                break;
            case FRAGMENT_KNOWLEDGESYSTEM:
                if (knowledgesystemFragment == null) {
                    knowledgesystemFragment = KnowledgesystemFragment.newInstance();
                    ft.add(R.id.fl_container, knowledgesystemFragment, KnowledgesystemFragment.class.getName());
                    ft.hide(knowledgesystemFragment);
                    ft.show(knowledgesystemFragment);
                } else {
                    ft.show(knowledgesystemFragment);
                }
                break;
            case FRAGMENT_NAVIGATION:
                if (navigationFragment == null) {
                    navigationFragment = NavigationFragment.newInstance();
                    ft.add(R.id.fl_container, navigationFragment, NavigationFragment.class.getName());
                    ft.hide(navigationFragment);
                    ft.show(navigationFragment);
                } else {
                    ft.show(navigationFragment);
                }
                break;
            case FRAGMENT_PROJECT:
                if (projectFragment == null) {
                    projectFragment = ProjectFragment.newInstance();
                    ft.add(R.id.fl_container, projectFragment, ProjectFragment.class.getName());
                    ft.hide(projectFragment);
                    ft.show(projectFragment);
                } else {
                    ft.show(projectFragment);
                }
                break;
            default:
                break;
        }
        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (homefragment != null) {
            ft.hide(homefragment);
        }
        if (knowledgesystemFragment != null) {
            ft.hide(knowledgesystemFragment);
        }
        if (navigationFragment != null) {
            ft.hide(navigationFragment);
        }
        if (projectFragment != null) {
            ft.hide(projectFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            // 跳转搜索页面
            ActivityUtils.startActivity(mContext, new Intent(mContext, SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    // 按两次返回键退出
    private long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.ACTION_DOWN == event.getAction() && keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                if ((System.currentTimeMillis() - time) > 2000) {
                    Toast.makeText(getApplicationContext(), getString(R.string.main_alert_back), Toast.LENGTH_SHORT).show();
                    time = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_head:
                // 跳转登录页面
                ActivityUtils.startActivity(mContext, new Intent(mContext, LoginActivity.class));
                break;
            case R.id.fab_hot:
                // 跳转常用网站页面
                ActivityUtils.startActivity(mContext, new Intent(mContext, CommonWebActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 设置用户信息
     */
    @Subscribe(code = Constant.RX_BUS_CODE_LOGIN, threadMode = ThreadMode.MAIN)
    public void setUserInfo() {
        String username = SpUtils.getString(mContext, Constant.USER_NAME, "");
        if (TextUtils.isEmpty(username)) {
            // 未登录
            tv_name.setText(getString(R.string.click_tologin));
            civ_head.setImageResource(R.mipmap.icon_default_header);
            civ_head.setEnabled(true);
            mNavigationView.getMenu().getItem(1).setVisible(false); // 隐藏我的收藏
            mNavigationView.getMenu().getItem(2).setVisible(false); // 隐藏我的收藏
            mNavigationView.getMenu().getItem(3).setVisible(false); // 隐藏退出登录
        } else {
            // 已经登录
            tv_name.setText(username);
            civ_head.setImageResource(R.mipmap.icon_header);
            civ_head.setEnabled(false);
            mNavigationView.getMenu().getItem(1).setVisible(true);
            mNavigationView.getMenu().getItem(2).setVisible(true);
            mNavigationView.getMenu().getItem(3).setVisible(true);
        }
    }

    /**
     * 切换主题
     *
     * @param isNight
     */
    private void switchNight(final boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            SpUtils.setBoolean(mContext, Constant.NIGHT_MODEL, false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            SpUtils.setBoolean(mContext, Constant.NIGHT_MODEL, true);
        }
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
    }
}

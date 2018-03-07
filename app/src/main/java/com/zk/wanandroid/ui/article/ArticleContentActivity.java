package com.zk.wanandroid.ui.article;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.zk.wanandroid.R;
import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.activity.BaseMVPActivity;
import com.zk.wanandroid.widgets.WebViewFragment;

import butterknife.BindView;

/**
 * @description: 文章详情页面
 * @author: zhukai
 * @date: 2018/3/6 11:50
 */
public class ArticleContentActivity extends BaseMVPActivity<ArticleContentContract.ArticleContentPresenter, ArticleContentContract.IArticleContentModel>
        implements ArticleContentContract.IArticleContentView {

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private WebViewFragment webViewFragment;
    private WebView mWebView;
    private String mUrl; // 文章url
    private String mTitle; // 文章标题
    public static final String WEB_URL = "web_url";
    public static final String WEB_TITLE = "web_title";

    public static void runActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, ArticleContentActivity.class);
        intent.putExtra(WEB_URL, url);
        intent.putExtra(WEB_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_article_content;
    }

    @Override
    protected void initView() {
        super.initView();
        webViewFragment = new WebViewFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, webViewFragment, WebViewFragment.class.getName()).commit();
    }

    @Override
    protected void initData() {
        super.initData();
        mUrl = getIntent().getStringExtra(WEB_URL);
        mTitle = getIntent().getStringExtra(WEB_TITLE);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            // 调用系统分享
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_article_url, getString(R.string.app_name), mTitle, mUrl));
            intent.setType("text/plain");
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_collect) {
            // 收藏

        } else if (item.getItemId() == R.id.menu_copy) {
            // 复制到剪贴板
            ClipboardManager cmd = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cmd.setPrimaryClip(ClipData.newPlainText(getString(R.string.title_copy), mUrl));
            Snackbar.make(mWebView, R.string.copy_link_success, Snackbar.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.menu_browser) {
            // 调用系统浏览器
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(mUrl));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return ArticleContentPresenter.newInstance();
    }

    @Override
    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    @Override
    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 由于Fragment的onCreateView()在Activity的onCreat()之后调用，所以不能在init()方法中获取到WebView
        mWebView = webViewFragment.getWebView();
        mPresenter.setWebView(mWebView, mUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack(); // 返回上一个网页
                return true;
            } else {
                finish(); // 退出程序
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

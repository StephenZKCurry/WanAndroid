package com.zk.wanandroid.ui.article;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * @description: 文章详情Presenter
 * @author: zhukai
 * @date: 2018/3/6 21:41
 */
public class ArticleContentPresenter extends ArticleContentContract.ArticleContentPresenter {

    @NonNull
    public static ArticleContentPresenter newInstance() {
        return new ArticleContentPresenter();
    }

    @Override
    public ArticleContentContract.IArticleContentModel getModel() {
        return ArticleContentModel.newInstance();
    }

    /**
     * 初始化WebView
     *
     * @param webView
     * @param url
     */
    @Override
    public void setWebView(WebView webView, String url) {
        if (mIView == null || mIModel == null) {
            return;
        }
        final ProgressBar progressBar = mIView.getProgressBar();
        // 设置WebView参数
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); // 支持js
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 显示放大缩小按钮
        settings.setDisplayZoomControls(false); // 隐藏原生的缩放控件
        // 设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLoadsImagesAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("网页开始加载");
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("网页加载结束");
                progressBar.setVisibility(View.GONE);
            }

            /**
             * 所有跳转的链接都在此方法中回调
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                mIView.setTitle(title);
                super.onReceivedTitle(view, title);
            }
        });
        webView.loadUrl(url);
    }
}

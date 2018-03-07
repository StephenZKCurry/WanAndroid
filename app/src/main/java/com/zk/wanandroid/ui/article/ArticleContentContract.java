package com.zk.wanandroid.ui.article;

import android.webkit.WebView;
import android.widget.ProgressBar;

import com.zk.wanandroid.base.BasePresenter;
import com.zk.wanandroid.base.IBaseModel;
import com.zk.wanandroid.base.IBaseView;

/**
 * @description: 文章详情Contract
 * @author: zhukai
 * @date: 2018/3/6 14:02
 */
public interface ArticleContentContract {

    abstract class ArticleContentPresenter extends BasePresenter<IArticleContentModel, IArticleContentView> {
        /**
         * 初始化WebView
         *
         * @param webView
         * @param url
         */
        public abstract void setWebView(WebView webView, String url);
    }

    interface IArticleContentModel extends IBaseModel {

    }

    interface IArticleContentView extends IBaseView {
        /**
         * 获得WebView加载进度条
         *
         * @return
         */
        ProgressBar getProgressBar();

        /**
         * 设置toolbar标题
         *
         * @param title
         */
        void setTitle(String title);
    }

}

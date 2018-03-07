package com.zk.wanandroid.ui.article;

import android.support.annotation.NonNull;

/**
 * @description: 文章详情Model
 * @author: zhukai
 * @date: 2018/3/7 9:07
 */
public class ArticleContentModel implements ArticleContentContract.IArticleContentModel {

    @NonNull
    public static ArticleContentModel newInstance() {
        return new ArticleContentModel();
    }

}

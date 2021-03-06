package com.zk.wanandroid.ui.article;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zk.wanandroid.R;
import com.zk.wanandroid.bean.Article;

import java.util.List;

/**
 * @description: 文章列表adapter
 * @author: zhukai
 * @date: 2018/3/5 16:38
 */
public class ArticleAdapter extends BaseQuickAdapter<Article.DatasBean, BaseViewHolder> {

    public ArticleAdapter(List<Article.DatasBean> data) {
        super(R.layout.item_article, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article.DatasBean item) {
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_date, item.getNiceDate());
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tv_chapter, item.getChapterName());

        if (item.isCollect()) {
            helper.setImageResource(R.id.iv_collect, R.drawable.ic_vector_like);
        } else {
            helper.setImageResource(R.id.iv_collect, R.drawable.ic_vector_notlike);
        }
        // 收藏添加点击事件
        helper.addOnClickListener(R.id.iv_collect);
    }
}

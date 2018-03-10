package com.zk.wanandroid.ui.hotsearch;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zk.wanandroid.R;
import com.zk.wanandroid.db.SearchHistory;

import java.util.List;

/**
 * @description: 搜索历史adapter
 * @author: zhukai
 * @date: 2018/3/9 15:11
 */
public class SearchHistroyAdapter extends BaseQuickAdapter<SearchHistory, BaseViewHolder> {

    public SearchHistroyAdapter(List<SearchHistory> data) {
        super(R.layout.item_search_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistory item) {
        helper.setText(R.id.tv_history, item.getName());
        helper.addOnClickListener(R.id.iv_close);
    }
}

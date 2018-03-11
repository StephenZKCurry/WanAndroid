package com.zk.wanandroid.ui.knowledgesystem;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zk.wanandroid.R;
import com.zk.wanandroid.bean.KnowledgeSystem;

import java.util.List;

/**
 * @description: 知识体系adapter
 * @author: zhukai
 * @date: 2018/3/10 23:10
 */
public class KnowledgesystemAdapter extends BaseQuickAdapter<KnowledgeSystem, BaseViewHolder> {

    public KnowledgesystemAdapter(List<KnowledgeSystem> data) {
        super(R.layout.item_knowledgesystem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeSystem item) {
        helper.setText(R.id.tv_first, item.getName());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < item.getChildren().size(); i++) {
            sb.append(item.getChildren().get(i).getName() + "     ");
        }
        helper.setText(R.id.tv_second, sb.toString());
    }
}

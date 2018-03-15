package com.zk.wanandroid.ui.mine;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zk.wanandroid.R;
import com.zk.wanandroid.bean.Bookmark;

import java.util.List;

/**
 * @description: 我的书签列表adapter
 * @author: zhukai
 * @date: 2018/3/15 10:22
 */
public class MyBookmarkAdapter extends BaseQuickAdapter<Bookmark, BaseViewHolder> {

    private boolean isEditable; // 是否可编辑

    public MyBookmarkAdapter(List<Bookmark> data) {
        super(R.layout.item_bookmark, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bookmark item) {
        helper.setText(R.id.tv_name, item.getName());
        ImageView ivEdit = helper.getView(R.id.iv_edit);
        ImageView ivDelete = helper.getView(R.id.iv_delete);
        if (isEditable) {
            ivEdit.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.VISIBLE);
        } else {
            ivEdit.setVisibility(View.INVISIBLE);
            ivDelete.setVisibility(View.INVISIBLE);
        }
        helper.addOnClickListener(R.id.iv_edit);
        helper.addOnClickListener(R.id.iv_delete);
    }

    public boolean isEditable() {
        return isEditable;
    }

    /**
     * 设置是否可编辑
     *
     * @param editable
     */
    public void setEditable(boolean editable) {
        this.isEditable = editable;
    }
}

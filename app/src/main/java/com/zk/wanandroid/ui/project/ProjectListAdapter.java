package com.zk.wanandroid.ui.project;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zk.wanandroid.R;
import com.zk.wanandroid.bean.Project;
import com.zk.wanandroid.utils.ImageLoader;

import java.util.List;

/**
 * @description: 项目列表adapter
 * @author: zhukai
 * @date: 2018/3/11 15:40
 */
public class ProjectListAdapter extends BaseQuickAdapter<Project.DatasBean, BaseViewHolder> {

    public ProjectListAdapter(List<Project.DatasBean> data) {
        super(R.layout.item_project, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Project.DatasBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_description, item.getDesc())
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_date, item.getNiceDate());

        ImageView ivProject = helper.getView(R.id.iv_project);
        ImageLoader.getInstance().displayImage(mContext, item.getEnvelopePic(), ivProject);

        TextView tvDownload = helper.getView(R.id.tv_download);
        if (TextUtils.isEmpty(item.getApkLink())) {
            tvDownload.setVisibility(View.GONE);
        } else {
            tvDownload.setVisibility(View.VISIBLE);
        }
        // 安装下载添加点击事件
        helper.addOnClickListener(R.id.tv_download);

        if (item.isCollect()) {
            helper.setImageResource(R.id.iv_collect, R.drawable.ic_vector_like);
        } else {
            helper.setImageResource(R.id.iv_collect, R.drawable.ic_vector_notlike);
        }
        // 收藏添加点击事件
        helper.addOnClickListener(R.id.iv_collect);
    }
}

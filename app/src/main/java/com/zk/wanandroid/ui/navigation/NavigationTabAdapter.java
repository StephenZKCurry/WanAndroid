package com.zk.wanandroid.ui.navigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zk.wanandroid.R;
import com.zk.wanandroid.bean.Navigation;
import com.zk.wanandroid.ui.article.ArticleContentActivity;

import java.util.List;

/**
 * @description: 导航标签页adapter
 * @author: zhukai
 * @date: 2019/4/9 14:25
 */
public class NavigationTabAdapter extends RecyclerView.Adapter<NavigationTabAdapter.TabViewHolder> {

    private Context mContext;
    private List<Navigation> mData;

    public NavigationTabAdapter(Context context, List<Navigation> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_navigation, parent, false);
        TabViewHolder holder = new TabViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TabViewHolder holder, int position) {
        holder.tvTitle.setText(mData.get(position).getName());
        final NavigationAdapter navigationAdapter = new NavigationAdapter(mContext, mData.get(position).getArticles());
        holder.tflNavigation.setAdapter(navigationAdapter);
        holder.tflNavigation.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                // 跳转网站详情页
                ArticleContentActivity.runActivity(mContext, navigationAdapter.getItem(position).getLink(),
                        navigationAdapter.getItem(position).getTitle());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TabViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TagFlowLayout tflNavigation;

        public TabViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tflNavigation = itemView.findViewById(R.id.tfl_navigation);
        }
    }
}

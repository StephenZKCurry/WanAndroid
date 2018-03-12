package com.zk.wanandroid.ui.navigation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zk.wanandroid.R;
import com.zk.wanandroid.bean.Navigation;
import com.zk.wanandroid.utils.DensityUtils;

import java.util.List;

/**
 * @description: 导航网站adapter
 * @author: zhukai
 * @date: 2018/3/12 14:10
 */
public class NavigationAdapter extends TagAdapter<Navigation.ArticlesBean> {

    private Context mContext;
    private LayoutInflater mInflater;

    public NavigationAdapter(Context context, List<Navigation.ArticlesBean> datas) {
        super(datas);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(FlowLayout parent, int position, Navigation.ArticlesBean articlesBean) {
        View view = mInflater.inflate(R.layout.item_common, parent, false);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        int parseColor = 0;
        try {
            tv_title.setText(articlesBean.getTitle());
            String str = Integer.toHexString((int) (Math.random() * 16777215));
            parseColor = Color.parseColor("#".concat(str));
            tv_title.setTextColor(parseColor);
        } catch (Exception e) {
            e.printStackTrace();
            parseColor = mContext.getResources().getColor(R.color.colorAccent);
        }
        tv_title.setTextColor(parseColor);
        return view;
    }
}

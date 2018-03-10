package com.zk.wanandroid.ui.hotsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zk.wanandroid.R;
import com.zk.wanandroid.bean.CommonWeb;

import java.util.List;

/**
 * @description: 常用网站adapter
 * @author: zhukai
 * @date: 2018/3/7 15:22
 */
public class CommonWebAdapter extends TagAdapter<CommonWeb> {

    private Context mContext;
    private LayoutInflater mInflater;

    public CommonWebAdapter(Context context, List<CommonWeb> datas) {
        super(datas);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(FlowLayout parent, int position, CommonWeb commonWeb) {
        View view = mInflater.inflate(R.layout.item_common, parent, false);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(commonWeb.getName());
        return view;
    }
}

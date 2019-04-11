package com.zk.wanandroid.ui.navigation;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zk.wanandroid.R;
import com.zk.wanandroid.bean.Navigation;
import com.zk.wanandroid.utils.DensityUtils;

import java.util.List;
import java.util.Random;

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
//        int parseColor = 0;
//        try {
//            tv_title.setText(articlesBean.getTitle());
//            String str = Integer.toHexString((int) (Math.random() * 16777215));
//            parseColor = Color.parseColor("#".concat(str));
//            tv_title.setTextColor(parseColor);
//        } catch (Exception e) {
//            e.printStackTrace();
//            parseColor = ContextCompat.getColor(mContext, R.color.colorAccent);
//        }
        tv_title.setText(articlesBean.getTitle());
        tv_title.setTextColor(getRandomColor());
        return view;
    }

    /**
     * 生成随机颜色
     *
     * @return
     */
    private int getRandomColor() {
        Random random = new Random();
        int red;
        int green;
        int blue;
        // 0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        red = random.nextInt(190);
        green = random.nextInt(190);
        blue = random.nextInt(190);
        // 使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue);
    }
}

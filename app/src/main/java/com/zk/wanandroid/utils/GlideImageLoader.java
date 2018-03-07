package com.zk.wanandroid.utils;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * @description: 图片加载器
 * @author: zhukai
 * @date: 2018/3/5 14:25
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        com.zk.wanandroid.utils.ImageLoader.getInstance().
                displayImage(context.getApplicationContext(), (String) path, imageView);
    }
}

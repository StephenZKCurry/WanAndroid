package com.zk.wanandroid.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zk.wanandroid.R;

import java.io.File;

/**
 * @description: 图像加载工具类
 * @author: zhukai
 * @date: 2018/2/27 13:31
 */
public class ImageLoader {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private static class ImageLoaderHolder {
        private static final ImageLoader INSTANCE = new ImageLoader();
    }

    private ImageLoader() {
    }

    public static final ImageLoader getInstance() {
        return ImageLoaderHolder.INSTANCE;
    }

    /**
     * 直接加载网络图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void displayImage(Context context, String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.icon_default)
                .into(imageView);
    }

    /**
     * 加载SD卡图片
     *
     * @param context
     * @param file
     * @param imageView
     */
    public void displayImage(Context context, File file, ImageView imageView) {
        Glide.with(context).load(file).centerCrop().into(imageView);

    }

    /**
     * 加载网络图片并设置大小
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public void displayImage(Context context, String url, ImageView imageView, int width, int height) {
        Glide.with(context).load(url).centerCrop().override(width, height).crossFade().into(imageView);
    }

    /**
     * 加载SD卡图片并设置大小
     *
     * @param context
     * @param file
     * @param imageView
     * @param width
     * @param height
     */
    public void displayImage(Context context, File file, ImageView imageView, int width, int height) {
        Glide.with(context).load(file).override(width, height).centerCrop().into(imageView);

    }

    /**
     * 加载drawable图片
     *
     * @param context
     * @param resId
     * @param imageView
     */
    public void displayImage(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resourceIdToUri(context, resId)).crossFade().into(imageView);
    }

    /**
     * 加载网络图片显示为圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void displayCricleImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                // .centerCrop()//网友反馈，设置此属性可能不起作用,在有些设备上可能会不能显示为圆形。
                .transform(new GlideCircleTransform(context)).crossFade().into(imageView);
    }

    /**
     * 加载SD卡图片显示为圆形图片
     *
     * @param context
     * @param file
     * @param imageView
     */
    public void displayCricleImage(Context context, File file, ImageView imageView) {
        Glide.with(context).load(file)
                // .centerCrop()
                .transform(new GlideCircleTransform(context)).into(imageView);

    }

    /**
     * 加载drawable图片显示为圆形图片
     *
     * @param context
     * @param resId
     * @param imageView
     */
    public void displayCricleImage(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resourceIdToUri(context, resId)).crossFade()
                .transform(new GlideCircleTransform(context)).into(imageView);
    }

    /**
     * 将资源ID转为Uri
     *
     * @param context
     * @param resourceId
     * @return
     */
    public Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

}

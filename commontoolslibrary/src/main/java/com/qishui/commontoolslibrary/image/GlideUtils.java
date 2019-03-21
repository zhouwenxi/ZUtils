package com.qishui.commontoolslibrary.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.qishui.commontoolslibrary.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

/**
 * 作者: create by qishui
 * 日期：2019/3/20  10:55
 * 邮箱: qishuichixi@163.com
 * 描述：glide 加载图片框架  设置image宽高
 */

public class GlideUtils {

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/20  11:17
     * 添加注释: 默认图片配置规则
     */
    public static RequestOptions getDefaultRequestOptions() {
        RequestOptions options = new RequestOptions()
                //.override(200, 100) //设置大小
                //.override(Target.SIZE_ORIGINAL) //加载原图
                .placeholder(R.drawable.logo)    //加载中图片
                .error(R.drawable.logo)           //加载出错
                .centerCrop()                     //设置图片变换  圆形
                .circleCrop()                     //圆角
                .transform(new BlurTransformation(), new GrayscaleTransformation()) //对图片进行模糊化和黑白化处理
                .skipMemoryCache(true)           //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE);//不是使用磁盘缓存
        return options;
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/20  11:02
     * 添加注释: 加载一张图片 可以加载gif文件
     */
    public static void display(Context context, Object url, ImageView iv) {
        Glide.with(context).load(url).into(iv);
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/20  11:14
     * 添加注释:
     */
    public static void display(Context context, Object url, ImageView iv, RequestOptions options) {
        Glide.with(context)
                .asBitmap()     //设置加载为一张图片,gif显示第一张
                .load(url)
                .apply(options)
                .into(iv);
    }


}

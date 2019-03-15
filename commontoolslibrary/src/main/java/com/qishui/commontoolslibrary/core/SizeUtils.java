package com.qishui.commontoolslibrary.core;


import android.content.Context;
import android.util.DisplayMetrics;

import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;

/**
 * 添加人: add by qishui
 * 添加时间: 2019/3/15  17:18
 * 添加注释:
 */
public class SizeUtils {


    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/15  17:20
     * 添加注释: 获取宽度
     */
    public static int getWith() {
        DisplayMetrics metrics = BaseQiShuiApplication.getContext().getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/15  17:21
     * 添加注释: 获取高度
     */
    public static int getHeight() {
        DisplayMetrics metrics = BaseQiShuiApplication.getContext().getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * px转dp
     *
     * @param px
     * @return
     */
    public static int px2dp(int px) {
        DisplayMetrics metrics = BaseQiShuiApplication.getContext().getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (px / density + 0.5f);
    }

    /**
     * dp转成px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        DisplayMetrics metrics = BaseQiShuiApplication.getContext().getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * Value of pt to value of px.
     *
     * @param ptValue The value of pt.
     * @return value of px
     */
    public static int pt2Px(float ptValue) {
        DisplayMetrics metrics = BaseQiShuiApplication.getContext().getResources().getDisplayMetrics();
        return (int) (ptValue * metrics.xdpi / 72f + 0.5);
    }

    /**
     * Value of px to value of pt.
     *
     * @param pxValue The value of px.
     * @return value of pt
     */
    public static int px2Pt(float pxValue) {
        DisplayMetrics metrics = BaseQiShuiApplication.getContext().getResources().getDisplayMetrics();
        return (int) (pxValue * 72 / metrics.xdpi + 0.5);
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


}

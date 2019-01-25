package com.qishui.commontoolslibrary.core;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;

public class UiUtils {

    private static Context context = BaseQiShuiApplication.getContext();

    /**
     * 加载布局
     *
     * @param resLayoutId
     * @return
     */
    public static View inflate(int resLayoutId) {
        return LayoutInflater.from(context).inflate(resLayoutId, null);
    }

    public static View inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.from(context).inflate(resource, root, attachToRoot);
    }

    /**
     * 查找id
     *
     * @param view
     * @param id
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewById(View view, int id) {
        return view.findViewById(id);
    }

    public static <T extends View> T findViewById(Activity activity, int id) {
        return activity.findViewById(id);
    }


    /**
     * 获取颜色
     *
     * @param id
     * @return
     */
    public static int getColor(int id) {
        int color = context.getResources().getColor(id);
        return color;
    }

    /**
     * 延迟发生消息
     *
     * @param runnable
     * @param delayMillis
     */
    public static void post(Runnable runnable, long delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(runnable, delayMillis);
    }


    /**
     * px转dp
     *
     * @param px
     * @return
     */
    public static int px2dp(int px) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
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
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (dp * density+0.5f);
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

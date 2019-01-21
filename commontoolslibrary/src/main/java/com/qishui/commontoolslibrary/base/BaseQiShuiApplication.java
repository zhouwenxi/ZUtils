package com.qishui.commontoolslibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.qishui.commontoolslibrary.cache.CacheManager;
import com.qishui.commontoolslibrary.core.AdapterScreenUtils;
import com.qishui.commontoolslibrary.core.CrashUtils;
import com.qishui.commontoolslibrary.http.HttpManager;
import com.qishui.commontoolslibrary.http.easyhttp.HttpThreadPoolManager;
import com.qishui.commontoolslibrary.http.proxy.EasyHttpProxy;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhou on 2018/12/23.
 */

public class BaseQiShuiApplication extends Application {

    //全局上下文
    private static Context context;

    private static LinkedList<Activity> activityLinkedList = new LinkedList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        CrashUtils.with(this);
        //适配注册
        AdapterScreenUtils.setup(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activityLinkedList.add(activity);
                if (activity != null) {
                    AdapterScreenUtils.match(activity, 360, AdapterScreenUtils.MATCH_BASE_WIDTH, AdapterScreenUtils.MATCH_UNIT_DP);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (activity != null) {
                    AdapterScreenUtils.cancelMatch(activity,  AdapterScreenUtils.MATCH_UNIT_DP);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityLinkedList.remove(activity);
            }
        });

        HttpManager.with().setHttp(new EasyHttpProxy());

        //检查最大容量 使用子线程，提高启动速度
        HttpThreadPoolManager.with().excute(new Runnable() {
            @Override
            public void run() {
                CacheManager.with().clean();
            }
        });


    }

    public static Context getContext() {
        return context;
    }

    public static void exitApp() {

        // 复制了一份mActivities 集合
        List<Activity> copy;
        synchronized (BaseQiShuiApplication.class) {
            copy = new LinkedList<>(activityLinkedList);
        }
        for (Activity activity : copy) {
            activity.finish();
        }
        //关闭已奔溃的app进程
        System.exit(1);
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

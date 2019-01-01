package com.qishui.commontoolslibrary.base;

import android.app.Application;
import android.content.Context;

import com.qishui.commontoolslibrary.cache.CacheManager;
import com.qishui.commontoolslibrary.http.HttpManager;
import com.qishui.commontoolslibrary.http.easyhttp.HttpThreadPoolManager;
import com.qishui.commontoolslibrary.http.proxy.EasyHttpProxy;

/**
 * Created by zhou on 2018/12/23.
 */

public class BaseQiShuiApplication extends Application {

    //全局上下文
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

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
}

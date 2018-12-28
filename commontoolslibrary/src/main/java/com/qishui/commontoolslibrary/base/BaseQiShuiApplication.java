package com.qishui.commontoolslibrary.base;

import android.app.Application;
import android.content.Context;

import com.qishui.commontoolslibrary.core.TinkerUtils;
import com.qishui.commontoolslibrary.http.HttpManager;
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

    }

    public static Context getContext() {
        return context;
    }
}

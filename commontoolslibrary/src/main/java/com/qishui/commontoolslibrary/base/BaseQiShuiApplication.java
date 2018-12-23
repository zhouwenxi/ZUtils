package com.qishui.commontoolslibrary.base;

import android.app.Application;
import android.content.Context;

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
    }

    public static Context getContext() {
        return context;
    }
}

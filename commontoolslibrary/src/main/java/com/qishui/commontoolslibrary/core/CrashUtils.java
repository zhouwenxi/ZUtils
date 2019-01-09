package com.qishui.commontoolslibrary.core;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.qishui.commontoolslibrary.activity.QiShuiCrashActivity;
import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;

/**
 * Created by zhou on 2018/12/22.
 */

public class CrashUtils implements Thread.UncaughtExceptionHandler {

    //默认处理机制
    private Thread.UncaughtExceptionHandler mExceptionHandler;
    //上下文
    private Context mContext;
    private String errMsg;
    public static final String KEY_ERRMSG = "ERRMSG";

    //单例
    private static CrashUtils crashUtils;

    public static CrashUtils with(Context mContext) {

        if (crashUtils == null) {
            synchronized (CrashUtils.class) {
                crashUtils = new CrashUtils(mContext);
            }
        }
        return crashUtils;
    }

    private CrashUtils(Context mContext) {
        this.mContext = mContext;
        mExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        if (!handleException(throwable) && mExceptionHandler != null) {
            //采用默认处理，如果没有自己处理
            mExceptionHandler.uncaughtException(thread, throwable);
        } else {

            //调转页面
            Intent intent = new Intent(mContext, QiShuiCrashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(KEY_ERRMSG, errMsg);
            mContext.startActivity(intent);
            BaseQiShuiApplication.exitApp();

        }

    }

    /**
     * 处理异常
     *
     * @param throwable
     * @return
     */
    private boolean handleException(Throwable throwable) {

        if (throwable == null) {
            return false;
        }
        saveCrashInfo2File(throwable);
        return true;
    }

    /**
     * 收集异常信息和设备
     *
     * @param throwable
     */
    private void saveCrashInfo2File(Throwable throwable) {

        StringBuilder sb = new StringBuilder();
        //获取设备参数
        sb.append("手机系统 ").append(Build.VERSION.SDK_INT).append(" 设备型号 ").append(Build.MODEL).append(" 设备厂商 ").append(Build.MANUFACTURER).append("\n");
        //获取异常信息
        String message = FileUtils.getThrowable(throwable);
        sb.append(message);
        errMsg = sb.toString();
    }
}

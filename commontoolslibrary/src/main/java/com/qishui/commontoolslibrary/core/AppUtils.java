package com.qishui.commontoolslibrary.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;

/**
 * Created by zhou on 2018/12/22.
 */

public class AppUtils {

    private static Context context = BaseQiShuiApplication.getContext();

    /**
     * 获取版本名
     *
     * @return
     */
    public static String getAppVirsionName() {

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0.0";
        }

    }

    /**
     * 获取版本号
     * @return
     */
    public static long getAppVirsionCode() {

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }

    }

}

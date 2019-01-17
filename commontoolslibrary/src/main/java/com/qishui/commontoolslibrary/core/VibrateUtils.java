package com.qishui.commontoolslibrary.core;

import android.content.Context;
import android.os.Vibrator;

import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;

/**
 * 震动相关
 * 实现手机震动其实很简单，手机震动使用是Vibrator类，然后震动也是需要权限的，在使用之前在AndroidManifest.xml文件中添加
 *
 * 这里的this代表的当前的Context，然后最后一局代码设置的是震动的时间，这里的单位是ms，这里只是震动一下，如果想要实现类似接电话的时候的连续的震动，可以使用另外一种方法。
 *
 * 首先，这里使用的是一个长整型数组，数组的a[0]表示静止的时间，a[1]代表的是震动的时间，然后数组的a[2]表示静止的时间，a[3]代表的是震动的时间……依次类推下去，然后这里的代码有一点小小的改变：
 *
 * Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
 * long[] patter = {1000, 1000, 2000, 50};
 * vibrator.start(patter, 0);
 *
 * 最后一行中vibrate的第二参数表示从哪里开始循环，比如这里的0表示这个数组在第一次循环完之后会从下标0开始循环到最后，这里的如果是-1表示不循环。
 *
 */
public class VibrateUtils {

    private static Vibrator vibrator;

    private VibrateUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Vibrator getVibrator() {
        if (vibrator == null) {
            vibrator = (Vibrator) BaseQiShuiApplication.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        }
        return vibrator;
    }

    /**
     * 简单设置时间
     * @param milliseconds
     */
    public static void start(final long milliseconds) {
        Vibrator vibrator = getVibrator();
        if (vibrator != null) {
            vibrator.vibrate(milliseconds);
        }
    }


    /**
     * 这里的0表示这个数组在第一次循环完之后会从下标0开始循环到最后，这里的如果是-1表示不循环
     * @param pattern
     * @param repeat
     */
    public static void start(final long[] pattern, final int repeat) {
        Vibrator vibrator = getVibrator();
        if (vibrator != null) {
            vibrator.vibrate(pattern, repeat);
        }
    }

    /**
     * 取消
     */
    public static void cancel() {
        Vibrator vibrator = getVibrator();
        if (vibrator != null) {
            vibrator.cancel();
        }
    }


}

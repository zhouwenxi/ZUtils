package com.qishui.commontoolslibrary.notice;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;
import com.qishui.commontoolslibrary.core.UiUtils;

/**
 * Created by zhou on 2018/12/23.
 */

public class ToastUtils {

    private static Toast toast;

    public static void show(String message) {
        Toast.makeText(BaseQiShuiApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 提示框
     *
     * @param strId
     */
    public static void showToastOnUiThread(int strId) {
        showToastOnUiThread(UiUtils.getString(strId));
    }

    public static void showToastOnUiThread(final String message) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @SuppressLint("ShowToast")
            @Override
            public void run() {

                if (toast == null) {
                    toast = Toast.makeText(BaseQiShuiApplication.getContext(), message, Toast.LENGTH_LONG);
                } else {
                    toast.setText(message);
                }
                toast.show();
            }
        });

    }
}

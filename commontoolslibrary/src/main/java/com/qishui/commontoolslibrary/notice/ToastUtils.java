package com.qishui.commontoolslibrary.notice;

import android.widget.Toast;

import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;

/**
 * Created by zhou on 2018/12/23.
 */

public class ToastUtils {

    public static void show(String message){
        Toast.makeText(BaseQiShuiApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}

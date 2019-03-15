package com.qishui.commontoolslibrary.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * 作者: create by qishui
 * 日期：2019/3/15  17:25
 * 邮箱: qishuichixi@163.com
 * 描述：
 */

public class QiShuiException extends Throwable {

    public QiShuiException() {
    }

    public QiShuiException(String message) {
        super(message);
    }

    public QiShuiException(String message, Throwable cause) {
        super(message, cause);
    }

    public QiShuiException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public QiShuiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

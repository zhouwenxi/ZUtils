package com.qishui.commontoolslibrary.http.callback;

import android.os.Handler;
import android.os.Looper;

/**
 * 返回字符串
 */
public abstract class StringCallBack implements ICallBack {

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onSuccess(final String result) {

        if (Looper.getMainLooper() == Looper.getMainLooper()) {
            onEasySuccess(result);
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onEasySuccess(result);
                }
            });
        }

    }

    @Override
    public void onfalure(final String message) {

        if (Looper.getMainLooper() == Looper.getMainLooper()) {
            onEasyError(message);
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onEasyError(message);
                }
            });
        }
    }

    /**
     * @param result
     */
    protected abstract void onEasySuccess(String result);

    /**
     * @param message
     */
    protected abstract void onEasyError(String message);
}

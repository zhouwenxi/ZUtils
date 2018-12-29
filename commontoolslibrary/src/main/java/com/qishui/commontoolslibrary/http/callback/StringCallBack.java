package com.qishui.commontoolslibrary.http.callback;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;

/**
 * 返回字符串
 */
public abstract class StringCallBack implements ICallBack {

    private Activity mActivity;
    private Handler handler = new Handler(Looper.getMainLooper());

    public StringCallBack() {

    }

    public StringCallBack(Activity activity) {
        if (activity != null) {
            this.mActivity = activity;
        }
    }

    public StringCallBack(Fragment fragment) {
        if (fragment != null) {
            this.mActivity = fragment.getActivity();
        }
    }

    @Override
    public void onSuccess(final String result) {

        if (result == null || "".equals(result)) {
            onNull();
            return;
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            onEasySuccess(result);
            onLast();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onEasySuccess(result);
                    onLast();
                }
            });
        }

    }

    @Override
    public void onfalure(final String message) {

        if (Looper.getMainLooper() == Looper.myLooper()) {
            onEasyError(message);
            onLast();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onEasyError(message);
                    onLast();
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

    @Override
    public void onLast() {

    }

    @Override
    public void onNull() {

    }

    @Override
    public void inProgress(int progress) {

    }
}

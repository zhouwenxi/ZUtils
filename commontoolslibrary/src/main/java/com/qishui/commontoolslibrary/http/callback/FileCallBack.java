package com.qishui.commontoolslibrary.http.callback;

import android.os.Handler;
import android.os.Looper;

/**
 *
 */
public abstract class FileCallBack implements ICallBack {

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void inProgress(final float progress) {

        if (Looper.getMainLooper() == Looper.myLooper()) {
            onEasyInProgress(progress);
            onLast();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onEasyInProgress(progress);
                    onLast();
                }
            });
        }
    }
    @Override
    public void onSuccess(final String result) {

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

    protected abstract void onEasyInProgress(float progress);

    protected abstract void onEasySuccess(String result);

    protected abstract void onEasyError(String message);


    @Override
    public void onLast() {

    }

    @Override
    public void onNull() {

    }


}

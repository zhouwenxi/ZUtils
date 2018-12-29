package com.qishui.commontoolslibrary.http.callback;

import android.os.Handler;
import android.os.Looper;

public abstract class FileCallBack implements ICallBack {

    private Handler handler = new Handler(Looper.getMainLooper());
    private String dir;
    private String name;

    public FileCallBack(String dir, String name) {
        this.dir = dir;
        this.name = name;
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

    abstract void onEasySuccess(String result);

    abstract void onEasyError(String message);

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

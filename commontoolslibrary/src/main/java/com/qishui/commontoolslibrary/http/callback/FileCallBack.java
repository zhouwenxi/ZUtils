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

    abstract void onEasySuccess(String result);

    abstract void onEasyError(String message);
}

package com.qishui.commontoolslibrary.http.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by zhou on 2018/12/31.
 */

public abstract class BitmapCallBack implements ICallBack {

    private Handler handler = new Handler(Looper.getMainLooper());
    private Bitmap bitmap = null;

    @Override
    public void onSuccess(String result) {

        try {
            InputStream is = new ByteArrayInputStream(result.getBytes("UTF-8"));
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            onfalure("errorMsg_" + e.getMessage());
        }

        if (Looper.getMainLooper() == Looper.myLooper()) {
            onEasySuccess(bitmap);
            onLast();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onEasySuccess(bitmap);
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

    @Override
    public void onLast() {

    }

    @Override
    public void onNull() {

    }

    @Override
    public void inProgress(float progress) {

    }

    protected abstract void onEasySuccess(Bitmap bitmap);

    protected abstract void onEasyError(String message);
}

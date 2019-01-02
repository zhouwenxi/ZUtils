package com.qishui.commontoolslibrary.http.callback;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

public abstract class GsonCallBack<T> implements ICallBack {

    private Handler handler = new Handler(Looper.getMainLooper());

    private Class<T> clazz;
    private T bean;


    @Override
    public void onSuccess(String response) {

        if (response == null || "".equals(response)) {
            onNull();
            return;
        }
        try {
            Gson gson = new Gson();
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            this.clazz = (Class<T>) type.getActualTypeArguments()[0];
            bean = gson.fromJson(response, clazz);

        } catch (Exception e) {
            onfalure("Error-" + e.toString() + "-" + response);
        }
        if (bean == null) {
            onNull();
            return;
        }

        if (Looper.getMainLooper() == Looper.myLooper()) {
            onEasySuccess(bean);
            onLast();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onEasySuccess(bean);
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


    protected abstract void onEasySuccess(T t);

    protected abstract void onEasyError(String message);

    @Override
    public void onLast() {

    }

    @Override
    public void onNull() {

    }

    @Override
    public void inProgress(float progress) {

    }
}

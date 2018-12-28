package com.qishui.commontoolslibrary.http.callback;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GsonCallBack<T> implements ICallBack {

    private Handler handler = new Handler(Looper.getMainLooper());
    private T clazz = null;


    @Override
    public void onSuccess(String response) {
        try {
            clazz = (T) new Gson().fromJson(response, getType(this).getClass());
        } catch (Exception e) {
            onfalure("Error-" + e.toString());
        }
        if (clazz == null) {
            return;
        }

        if (Looper.getMainLooper() == Looper.myLooper()) {
            onEasySuccess(clazz);
            onLast();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onEasySuccess(clazz);
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


    private T getType(GsonCallBack<T> callBack) {

        Type t = callBack.getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        Type[] ts = pt.getActualTypeArguments();
        return (T) ts[0];

    }

    protected abstract void onEasySuccess(T result);

    protected abstract void onEasyError(String message);

    @Override
    public void onLast() {

    }
}

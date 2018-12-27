package com.qishui.commontoolslibrary.http.callback;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by zhou on 2018/12/27.
 */

public abstract class GsonCallBack<T> implements ICallBack {

    @Override
    public void onSuccess(String response) {

        T t = getType(this);
        T clazz = (T) new Gson().fromJson(response, t.getClass());
        onSuccess(clazz);

    }


    private T getType(GsonCallBack<T> callBack) {

        Type t = callBack.getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        Type[] ts = pt.getActualTypeArguments();
        return (T) ts[0];

    }


    protected abstract void onSuccess(T result);
}

package com.qishui.commontoolslibrary.http.okhttp;

import com.google.gson.Gson;
import com.qishui.commontoolslibrary.core.FileUtils;
import com.qishui.commontoolslibrary.core.LogUtils;
import com.qishui.commontoolslibrary.core.StringUtils;
import com.qishui.commontoolslibrary.exception.ErrorHandle;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhou on 2019/4/6.
 */

public abstract class GsonCallBackResult<T>  extends Callback<T> {

    private Class<T> clazz;

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {

        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<T>) type.getActualTypeArguments()[0];

        return new Gson().fromJson(response.body().string(), clazz);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        ErrorHandle.handleFailure(e);
        LogUtils.e("调用返回错误信息:\n", FileUtils.getException(e), "\n");
        onFailure(e);
    }

    @Override
    public void onResponse(T data, int id) {
        LogUtils.e(StringUtils.addString("返回网络数据:", data, "\n"));
        onSuccess(data);
    }

    /**
     * 失败
     * @param e
     */
    public abstract void onFailure(Exception e);

    /**
     * 成功
     * @param data
     */
    public abstract void onSuccess(T data);
}

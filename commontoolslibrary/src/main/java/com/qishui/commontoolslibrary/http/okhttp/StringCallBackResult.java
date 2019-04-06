package com.qishui.commontoolslibrary.http.okhttp;

import com.qishui.commontoolslibrary.core.FileUtils;
import com.qishui.commontoolslibrary.core.LogUtils;
import com.qishui.commontoolslibrary.core.StringUtils;
import com.qishui.commontoolslibrary.exception.ErrorHandle;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhou on 2019/4/6.
 *
 */
public abstract class StringCallBackResult extends Callback<String> {

    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        return response.body().string();
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        ErrorHandle.handleFailure(e);
        LogUtils.e("调用返回错误信息:\n", FileUtils.getException(e), "\n");
        onFailure(e);
    }

    @Override
    public void onResponse(String response, int id) {
        LogUtils.e(StringUtils.addString("返回网络数据:", response, "\n"));
        onSuccess(response);
    }

    /**
     * 失败
     * @param e
     */
    public abstract void onFailure(Exception e);

    /**
     * 成功
     * @param response
     */
    public abstract void onSuccess(String response);



}

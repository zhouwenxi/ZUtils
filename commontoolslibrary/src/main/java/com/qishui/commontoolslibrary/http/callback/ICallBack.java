package com.qishui.commontoolslibrary.http.callback;

/**
 * Created by zhou on 2018/12/27.
 */

public interface ICallBack {

    /**
     * 请求成功
     *
     * @param result
     */
    void onSuccess(String result);

    /**
     * 请求失败
     *
     * @param message
     */
    void onfalure(String message);
}

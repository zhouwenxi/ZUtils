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

    /**
     * 最后都通过
     */
    void onLast();

    /**
     * 空处理
     */
    void onNull();


    /**
     * 进度条
     *
     * @param progress
     */
    void inProgress(float progress);
}

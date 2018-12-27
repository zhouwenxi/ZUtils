package com.qishui.commontoolslibrary.http.callback;

/**
 * Created by zhou on 2018/12/27.
 */

public interface ICallBack {

    void onSuccess(String result);

    void onFail(String message);
}

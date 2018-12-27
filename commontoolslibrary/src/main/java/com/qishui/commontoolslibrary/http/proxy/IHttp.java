package com.qishui.commontoolslibrary.http.proxy;

import com.qishui.commontoolslibrary.http.callback.ICallBack;

import java.util.HashMap;

/**
 * Created by zhou on 2018/12/27.
 */

public interface IHttp {

    void get(String url, HashMap<String, Object> params, ICallBack callBack);

    void get(String url, ICallBack callBack);

    void post(String url, HashMap<String, Object> params, ICallBack callBack);

    void post(String url, ICallBack callBack);

    void postJson(String url, String json, ICallBack callBack);

}

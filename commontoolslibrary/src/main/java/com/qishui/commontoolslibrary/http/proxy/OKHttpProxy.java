package com.qishui.commontoolslibrary.http.proxy;

import com.qishui.commontoolslibrary.http.callback.ICallBack;
import com.qishui.commontoolslibrary.http.callback.IHttp;
import com.qishui.commontoolslibrary.http.okhttp.OkHttp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * 添加人: add by qishui
 * 添加时间: 2019/3/15  20:12
 * 添加注释: 代理OKHttp
 */
public class OKHttpProxy implements IHttp {

    @Override
    public void get(String url, HashMap<String, Object> params, ICallBack callBack) {

    }

    @Override
    public void get(String url, ICallBack callBack) {

    }

    @Override
    public void post(String url, HashMap<String, Object> params, ICallBack callBack) {

    }

    @Override
    public void post(String url, ICallBack callBack) {

    }

    @Override
    public void postJson(String url, String json, ICallBack callBack) {

    }

    @Override
    public void postFile(String url, File file, ICallBack callBack) {

    }

    @Override
    public void uploadFile(String url, Map<String, Object> params, String fileParam, String file, ICallBack callBack) {

    }

    @Override
    public void downloadFile(String url, String path, String name, ICallBack callBack) {

    }

    @Override
    public void downloadFile(String url, Map<String, Object> params, String path, String name, ICallBack callBack) {

    }

    @Override
    public void downloadFile(String url, Map<String, Object> params, String path, ICallBack callBack) {

    }

    @Override
    public void downloadFile(String url, String dir, ICallBack callBack) {

    }

    @Override
    public void cancel(String url) {

    }
}

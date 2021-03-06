package com.qishui.commontoolslibrary.http.proxy;

import com.qishui.commontoolslibrary.http.callback.IHttp;
import com.qishui.commontoolslibrary.http.easyhttp.EasyHttp;
import com.qishui.commontoolslibrary.http.callback.ICallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class EasyHttpProxy implements IHttp {
    @Override
    public void get(String url, HashMap<String, Object> params, ICallBack callBack) {
        EasyHttp.get(url, params, callBack);
    }

    @Override
    public void get(String url, ICallBack callBack) {
        EasyHttp.get(url, callBack);
    }

    @Override
    public void post(String url, HashMap<String, Object> params, ICallBack callBack) {
        EasyHttp.post(url, params, callBack);
    }

    @Override
    public void post(String url, ICallBack callBack) {
        EasyHttp.post(url, callBack);
    }

    @Override
    public void postJson(String url, String json, ICallBack callBack) {
        EasyHttp.postJson(url, json, callBack);
    }

    @Override
    public void postFile(String url, File file, ICallBack callBack) {

    }

    @Override
    public void uploadFile(String url, Map<String, Object> params, String fileParam, String file, ICallBack callBack) {
        EasyHttp.uploadFile(url, params, fileParam, file, callBack);
    }

    @Override
    public void downloadFile(String url, String path, String name, ICallBack callBack) {
        EasyHttp.downloadFile(url, path, name, callBack);
    }

    @Override
    public void downloadFile(String url, Map<String, Object> params, String path, String name, ICallBack callBack) {
        EasyHttp.downloadFile(url, params, path, name, callBack);
    }

    @Override
    public void downloadFile(String url, Map<String, Object> params, String path, ICallBack callBack) {
        EasyHttp.downloadFile(url, params, path, null, callBack);
    }

    @Override
    public void downloadFile(String url, String dir, ICallBack callBack) {
        EasyHttp.downloadFile(url, null, dir, null, callBack);
    }

    @Override
    public void cancel(String url) {

    }
}

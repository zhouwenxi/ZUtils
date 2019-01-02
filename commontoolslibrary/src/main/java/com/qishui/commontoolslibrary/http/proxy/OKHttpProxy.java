package com.qishui.commontoolslibrary.http.proxy;

import com.qishui.commontoolslibrary.http.callback.ICallBack;
import com.qishui.commontoolslibrary.http.callback.IHttp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhou on 2018/12/27.
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
    public void postJson(String url,  String json, ICallBack callBack) {

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
    public void downloadFile(String url, String dir, ICallBack callBack) {

    }

    @Override
    public void cancel(String url) {

    }
}

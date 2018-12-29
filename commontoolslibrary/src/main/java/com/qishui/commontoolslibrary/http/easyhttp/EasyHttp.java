package com.qishui.commontoolslibrary.http.easyhttp;

import com.qishui.commontoolslibrary.http.callback.ICallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EasyHttp {

    public static void get(final String url, final HashMap<String, Object> params, final ICallBack callBack) {

        HttpThreadPoolManager.with().excute(new Runnable() {
            @Override
            public void run() {
                HttpService.with().get(url, params, callBack);
            }
        });
    }

    public static void get(final String url, final ICallBack callBack) {

        HttpThreadPoolManager.with().excute(new Runnable() {
            @Override
            public void run() {
                HttpService.with().get(url, callBack);
            }
        });

    }

    public static void post(final String url, final HashMap<String, Object> params, final ICallBack callBack) {
        HttpThreadPoolManager.with().excute(new Runnable() {
            @Override
            public void run() {
                HttpService.with().post(url, params, callBack);
            }
        });

    }

    public static void post(final String url, final ICallBack callBack) {
        HttpThreadPoolManager.with().excute(new Runnable() {
            @Override
            public void run() {
                HttpService.with().post(url, callBack);
            }
        });

    }

    public static void postJson(final String url, final String json, final ICallBack callBack) {
        HttpThreadPoolManager.with().excute(new Runnable() {
            @Override
            public void run() {
                HttpService.with().postJson(url,json, callBack);
            }
        });

    }

    public void postFile(String url, File file, ICallBack callBack) {

    }

    public static void uploadFile(final String url, final Map<String, Object> params, final String fileParam, final String file, final ICallBack callBack) {
        HttpThreadPoolManager.with().excute(new Runnable() {
            @Override
            public void run() {
                HttpService.with().uploadFile(url,params,fileParam,file,callBack);
            }
        });
    }

    public static void downloadFile(final String url, final String path, final String name, final ICallBack callBack) {
        HttpThreadPoolManager.with().excute(new Runnable() {
            @Override
            public void run() {
                HttpService.with().downloadFile(url,path,name,callBack);
            }
        });
    }

    public static void downloadFile(final String url, final Map<String,Object>map, final String path, final String name, final ICallBack callBack) {
        HttpThreadPoolManager.with().excute(new Runnable() {
            @Override
            public void run() {
                HttpService.with().downloadFile(url,map,path,name,callBack);
            }
        });
    }

    public void cancel(String url) {

    }
}

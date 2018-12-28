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

    public void uploadFile(String url, Map<String, Object> params, String fileParam, File file, ICallBack callBack) {

    }

    public void downloadFile(String url, String path, String name, ICallBack callBack) {

    }

    public void cancel(String url) {

    }
}

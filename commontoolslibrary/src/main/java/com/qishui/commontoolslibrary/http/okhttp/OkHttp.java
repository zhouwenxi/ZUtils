package com.qishui.commontoolslibrary.http.okhttp;

import android.text.TextUtils;

import com.qishui.commontoolslibrary.core.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;


/**
 * 添加人: add by qishui
 * 添加时间: 2019/3/15  20:11
 * 添加注释: OKhttp请求
 */
public class OkHttp {


    // get 异步请求
    public static RequestCall get(String url, Map<String, String> params) {
        printLog(url, params);
        return OkHttpUtils.get().url(url).params(params).build();
    }

    // get 异步请求
    public static RequestCall get(String url) {
        printLog(url, null);
        return OkHttpUtils.get().url(url).build();
    }

    //post请求 异步请求
    public static RequestCall post(String url, Map<String, String> params) {
        printLog(url, params);
        return OkHttpUtils.post().url(url).params(params).build();
    }

    //post请求 异步请求
    public static RequestCall post(String url) {
        printLog(url, null);
        return OkHttpUtils.post().url(url).build();
    }

    //post一个json类型数据
    public static RequestCall postJson(String url, String jsonData) {
        printLog(url, jsonData);
        return OkHttpUtils.postString().url(url).content(jsonData).mediaType(MediaType.parse("application/json; charset=utf-8")).build();
    }

    // post一个文件
    public static RequestCall postFile(String url, File file) {
        printLog(url, file.getAbsolutePath());
        return OkHttpUtils.postFile().url(url).file(file).build();
    }

    //上传文件以表单形式
    public static RequestCall uploadFile(String url, Map<String, String> params, String fileParam, File file) {

        if (TextUtils.isEmpty(fileParam)) {
            fileParam = "file";
        }
        if (file == null || !file.exists()) {
            return null;
        }
        printLog(url, params);
        return OkHttpUtils.post().addFile(fileParam, file.getName(), file).url(url).params(params).build();
    }

    // 下载文件
    public static void downloadFile(String url, String path, String name) {
        printLog(url, path);
        get(url).execute(new com.zhy.http.okhttp.callback.FileCallBack(path, name) {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onResponse(File response, int id) {

            }

            @Override
            public void inProgress(float progress, long total, int id) {
            }

            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {

            }
        });
    }


    //取消请求
    public static void cancel(String url) {
        OkHttpUtils.get().url(url).build().cancel();
    }


    /**
     * 打印路径信息
     *
     * @param url
     */
    private static void printLog(String url, Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("请求地址: ").append(url).append("?");

        if (obj instanceof HashMap) {
            HashMap<String, String> params = (HashMap<String, String>) obj;
            sb.append("{ ");
            for (String s : params.keySet()) {
                sb.append(s).append(":").append(params.get(s)).append(",");
            }
            sb.append(" }");
        } else {
            if (obj != null) {
                sb.append(obj);
            }
        }
        LogUtils.e(sb.toString());
    }


}

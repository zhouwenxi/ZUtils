package com.qishui.commontoolslibrary.http.callback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhou on 2018/12/27.
 */

public interface IHttp {

    /**
     * Get 请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    void get(String url, HashMap<String, Object> params, ICallBack callBack);

    /**
     * Get 请求
     *
     * @param url
     * @param callBack
     */
    void get(String url, ICallBack callBack);

    /**
     * Post 请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    void post(String url, HashMap<String, Object> params, ICallBack callBack);

    /**
     * Post 请求
     *
     * @param url
     * @param callBack
     */
    void post(String url, ICallBack callBack);

    /**
     * PostJson 上传字符串
     *
     * @param url
     * @param json
     * @param callBack
     */
    void postJson(String url, String json, ICallBack callBack);

    /**
     * postFile 上传文件
     *
     * @param url
     * @param file
     * @param callBack
     */
    void postFile(String url, File file, ICallBack callBack);

    /**
     * uploadFile 上传文件
     *
     * @param url
     * @param params
     * @param fileParam
     * @param file
     * @param callBack
     */
    void uploadFile(String url, Map<String, Object> params, String fileParam, String file, ICallBack callBack);

    /**
     * 下载文件
     *
     * @param url
     * @param path
     * @param name
     * @param callBack
     */
    void downloadFile(String url, String path, String name, ICallBack callBack);

    /**
     * 网络请求
     *
     * @param url
     */
    void cancel(String url);
}

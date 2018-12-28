package com.qishui.commontoolslibrary.http;

import com.qishui.commontoolslibrary.http.proxy.IHttp;
import com.qishui.commontoolslibrary.http.proxy.OKHttpProxy;

/**
 * Created by zhou on 2018/12/27.
 * 网络管理
 */

public class HttpManager {

    private volatile static HttpManager httpManager;

    private HttpManager() {
    }

    public static HttpManager with() {
        if (httpManager == null) {
            synchronized (HttpManager.class) {
                if (httpManager == null) {
                    httpManager = new HttpManager();
                }
            }
        }
        return httpManager;
    }

    private static IHttp mHttp;

    //设置请求模式
    public HttpManager setHttp(IHttp mHttp) {
        this.mHttp = mHttp;
        return this;
    }

    //获取代理模式
    public IHttp getProxy() {
        //默认使用OKHttp
        return mHttp == null ? new OKHttpProxy() : mHttp;
    }
}

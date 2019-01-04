package com.qishui.commontoolslibrary.mvp.sample;

import com.qishui.commontoolslibrary.http.HttpManager;
import com.qishui.commontoolslibrary.http.callback.ICallBack;

import java.util.HashMap;

/**
 * 数据操作类
 */
public class QiShuiLoginModel implements QiShuiMVPContract.ILoginModel {

    @Override
    public void requestLogin(String url, HashMap<String, Object> map, ICallBack callBack) {
        /**
         * 实际请求网络
         */
        HttpManager.with().getProxy().get(url,map,callBack);
    }
}

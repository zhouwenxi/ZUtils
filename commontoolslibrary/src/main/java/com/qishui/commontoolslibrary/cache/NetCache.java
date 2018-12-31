package com.qishui.commontoolslibrary.cache;

import android.graphics.Bitmap;

import com.qishui.commontoolslibrary.http.HttpManager;
import com.qishui.commontoolslibrary.http.callback.BitmapCallBack;
import com.qishui.commontoolslibrary.http.callback.StringCallBack;

import java.util.HashMap;

/**
 * Created by zhou on 2018/12/31.
 */

public class NetCache {

    private LocalCache localCache;
    private MemoryCache memoryCache;

    public NetCache() {
        localCache = new LocalCache();
        memoryCache = new MemoryCache();
    }

    /**
     * @param url
     */
    public void getNetStringData(String url) {
        getNetStringData(url, null);
    }

    /**
     * 缓存String数据
     *
     * @param url
     * @param map
     */
    public void getNetStringData(String url, HashMap<String, Object> map) {
        final String key = CacheManager.getkey(url, map);
        HttpManager.with().getProxy().get(url, map, new StringCallBack() {
            @Override
            protected void onEasySuccess(String result) {
                localCache.putString(key, result);
            }

            @Override
            protected void onEasyError(String message) {

            }
        });
    }

    /**
     * @param url
     */
    public void getNetBitmapData(String url) {
        getNetBitmapData(url, null);
    }

    /**
     * @param url
     * @param map
     */
    public void getNetBitmapData(String url, HashMap<String, Object> map) {
        final String key = CacheManager.getkey(url, map);
        HttpManager.with().getProxy().get(url, map, new BitmapCallBack() {

            @Override
            protected void onEasySuccess(Bitmap bitmap) {
                localCache.putBitmap(key, bitmap);
                memoryCache.putBitmap(key, bitmap);
            }

            @Override
            protected void onEasyError(String message) {

            }
        });
    }


}

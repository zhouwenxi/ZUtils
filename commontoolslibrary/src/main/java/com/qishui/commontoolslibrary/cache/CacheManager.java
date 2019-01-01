package com.qishui.commontoolslibrary.cache;

import android.graphics.Bitmap;

import com.qishui.commontoolslibrary.core.EncDecUtils;

import java.util.HashMap;

/**
 * Created by zhou on 2018/12/31.
 * 内存管理
 */

public class CacheManager {

    private static CacheManager cacheManager;
    private static final String KEY_BITMAP = "_bitmap";
    private static final String KEY_OBJECT = "_object";

    private CacheManager() {
    }

    public static CacheManager with() {
        if (cacheManager == null) {
            synchronized (CacheManager.class) {
                if (cacheManager == null) {
                    cacheManager = new CacheManager();
                }
            }
        }
        return cacheManager;
    }


    /**
     * 获取key
     *
     * @param url
     * @param map
     * @return
     */
    public String getkey(String url, HashMap<String, Object> map, String tag) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url).append(tag);
        if (map != null) {
            stringBuilder.append("?");
            int size = map.size();
            int cur = 0;
            for (String key : map.keySet()) {
                cur++;
                stringBuilder.append(key).append("=").append(map.get(key));
                if (cur < size) {
                    stringBuilder.append("&");
                }
            }
        }
        return EncDecUtils.encMD5(stringBuilder.toString());
    }


    /**
     * 获取数据
     *
     * @param url
     * @return
     */
    public Bitmap getBitmap(String url) {
        return getBitmap(url, null);
    }

    public Bitmap getBitmap(String url, HashMap<String, Object> map) {
        Bitmap bitmap = null;
        String key = getkey(url, map, KEY_BITMAP);

        bitmap = MemoryCache.with().getBitmap(key);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = LocalCache.with().getBitmap(key);
        if (bitmap != null) {
            return bitmap;
        }

        return null;
    }

    /**
     * 获取数据
     *
     * @param url
     * @return
     */
    public Object getObject(String url) {
        return getObject(url, null);
    }

    public Object getObject(String url, HashMap<String, Object> map) {
        Object obj = null;
        String key = getkey(url, map, KEY_OBJECT);

        obj = MemoryCache.with().getObject(key);
        if (obj != null) {
            return obj;
        }
        obj = LocalCache.with().getObject(key);
        if (obj != null) {
            return obj;
        }

        return null;
    }

    /**
     * 存放bitmap
     *
     * @param url
     * @param map
     * @param bitmap
     */
    public CacheManager putBitmap(String url, HashMap<String, Object> map, Bitmap bitmap) {

        if (bitmap == null) {
            return this;
        }
        String key = getkey(url, map, KEY_BITMAP);
        MemoryCache.with().putBitmap(key, bitmap);
        LocalCache.with().putBitmap(key, bitmap);
        return this;
    }

    public CacheManager putBitmap(String url, Bitmap bitmap) {

        putBitmap(url, null, bitmap);
        return this;
    }

    /**
     * 存放小数据类型
     *
     * @param url
     * @param map
     * @param object
     */
    public CacheManager putObject(String url, HashMap<String, Object> map, Object object) {

        if (object == null) {
            return this;
        }
        String key = getkey(url, map, KEY_OBJECT);
        MemoryCache.with().putObject(key, object);
        LocalCache.with().putObject(key, object);
        return this;
    }

    public CacheManager putObject(String url, Object object) {
        putObject(url, null, object);
        return this;
    }

    /**
     * 清除本地缓存
     */
    public void clean() {
        LocalCache.with().delete();
    }
}

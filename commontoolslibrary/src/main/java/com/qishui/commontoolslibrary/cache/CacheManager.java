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
    private static LocalCache localCache;
    private static MemoryCache memoryCache;
    private static NetCache netCache;


    private CacheManager() {
        localCache = new LocalCache();
        memoryCache = new MemoryCache();
        netCache = new NetCache();
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
    public static String getkey(String url, HashMap<String, Object> map) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
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


    public static Bitmap getBitmap(String url, HashMap<String, Object> map) {
        Bitmap bitmap = null;
        String key = getkey(url, map);

        bitmap = memoryCache.getBitmap(key);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = localCache.getBitmap(key);
        if (bitmap != null) {
            return bitmap;
        }

        netCache.getNetBitmapData(url,map);

        return null;
    }


}

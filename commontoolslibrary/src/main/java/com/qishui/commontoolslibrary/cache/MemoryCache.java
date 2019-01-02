package com.qishui.commontoolslibrary.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by zhou on 2018/12/31.
 */

public class MemoryCache {

    /**
     * 存放bitmap
     */
    private LruCache<String, Bitmap> map;
    /**
     * 存放小数据
     */
    private LruCache<String, Object> stringMap;

    private MemoryCache() {
        long memory = Runtime.getRuntime().maxMemory();
        int size = (int) (memory / 8);
        map = new LruCache<String, Bitmap>(size) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

        int stringSize = (int) (memory / 32);
        stringMap = new LruCache<String, Object>(stringSize) {
            @Override
            protected int sizeOf(String key, Object value) {
                return 100;
            }
        };

    }

    private static MemoryCache memoryCache;

    public static MemoryCache with() {
        if (memoryCache == null) {
            synchronized (MemoryCache.class) {
                if (memoryCache == null) {
                    memoryCache = new MemoryCache();
                }
            }
        }
        return memoryCache;
    }

    /**
     * 保存数据
     *
     * @param key
     * @param result
     */
    public MemoryCache putBitmap(String key, Bitmap result) {
        map.put(key, result);
        return this;
    }

    /**
     * 获取
     *
     * @param key
     */
    public Bitmap getBitmap(String key) {
        return map.get(key);
    }


    public MemoryCache putObject(String key, Object object) {
        stringMap.put(key, object);
        return this;
    }


    public Object getObject(String key) {
        return stringMap.get(key);
    }


    /**
     * 清除数据
     *
     * @param key
     */
    public MemoryCache removeObject(String key) {
        stringMap.remove(key);
        return this;
    }

    public MemoryCache removeBitmap(String key) {
        map.remove(key);
        return this;
    }


    /**
     * 清除内存缓存
     */
    public void delete() {
        if (map != null) {
            map.evictAll();
        }
        if (stringMap != null) {
            stringMap.evictAll();
        }
    }


}

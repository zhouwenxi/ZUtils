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

        int stringSize = (int) (memory / 16);
        stringMap = new LruCache<String, Object>(stringSize) {
            @Override
            protected int sizeOf(String key, Object value) {
                return 100;
            }
        };

    }

    private static MemoryCache memoryCache;
    public static MemoryCache with() {
        if(memoryCache==null){
            synchronized (MemoryCache.class){
                if(memoryCache==null){
                    memoryCache=new MemoryCache();
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
    public void putBitmap(String key, Bitmap result) {
        map.put(key, result);
    }

    /**
     * 获取
     *
     * @param key
     */
    public Bitmap getBitmap(String key) {
        return map.get(key);
    }


    public void putObject(String key, Object object) {
        stringMap.put(key, object);
    }


    public Object getObject(String key) {
        return stringMap.get(key);
    }


    /**
     * 清除数据
     *
     * @param key
     */
    public void removeObject(String key) {
        stringMap.remove(key);
    }

    public void removeBitmap(String key) {
        map.remove(key);
    }

}

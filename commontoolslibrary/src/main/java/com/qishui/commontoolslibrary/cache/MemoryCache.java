package com.qishui.commontoolslibrary.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by zhou on 2018/12/31.
 */

public class MemoryCache {

    private LruCache<String, Bitmap> map;

    public MemoryCache() {
        long memory = Runtime.getRuntime().maxMemory();
        int size = (int) (memory / 8);
        map = new LruCache<String, Bitmap>(size) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
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


}

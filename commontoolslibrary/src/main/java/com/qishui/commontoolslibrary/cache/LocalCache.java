package com.qishui.commontoolslibrary.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qishui.commontoolslibrary.core.FileUtils;
import com.qishui.commontoolslibrary.core.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by zhou on 2018/12/31.
 */

public class LocalCache {

    public static final String KEY_CACHE = FileUtils.KEY_FILE_CACHE;
    //最大存放量 100MB
    private static final int MAX = 100 * 1024 * 1024;

    private static LocalCache localCache;

    private LocalCache() {
    }

    public static LocalCache with() {
        if (localCache == null) {
            synchronized (CacheManager.class) {
                if (localCache == null) {
                    localCache = new LocalCache();
                }
            }
        }
        return localCache;
    }

    /**
     * 保存String数据
     *
     * @param key
     * @param object
     */
    public void putObject(String key, Object object) {

        FileUtils.writeNewFile(KEY_CACHE, key, String.valueOf(object));
    }

    /**
     * 保存bitmap文件
     *
     * @param key
     * @param bitmap
     */
    public void putBitmap(String key, Bitmap bitmap) {
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(FileUtils.createFileDelOld(KEY_CACHE, key)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取String
     *
     * @param key
     */
    public Object getObject(String key) {

        try {
            FileInputStream is = new FileInputStream(new File(KEY_CACHE, key));
            return FileUtils.is2String(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取bitmap
     *
     * @param key
     * @return
     */
    public Bitmap getBitmap(String key) {
        try {
            FileInputStream is = new FileInputStream(new File(KEY_CACHE, key));
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 清除数据
     */
    public void delete() {
        long dirLength = FileUtils.getDirLength(new File(KEY_CACHE));
        LogUtils.e("SD缓存数据大小:" + (dirLength / 1024) + " KB");
        if (dirLength >= MAX) {
            FileUtils.deleteDirAndFiles(new File(KEY_CACHE));
        }
    }

}

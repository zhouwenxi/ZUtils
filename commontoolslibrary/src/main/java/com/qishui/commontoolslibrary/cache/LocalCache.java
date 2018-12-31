package com.qishui.commontoolslibrary.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qishui.commontoolslibrary.core.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by zhou on 2018/12/31.
 */

public class LocalCache {

    public static final String KEY_CACHE = FileUtils.KEY_FILE_CACHE;


    /**
     * 保存String数据
     *
     * @param key
     * @param result
     */
    public void putString(String key, String result) {
        FileUtils.writeNewFile(KEY_CACHE, key, result);
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
    public String getString(String key) {

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

}

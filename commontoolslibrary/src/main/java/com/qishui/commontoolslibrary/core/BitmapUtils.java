package com.qishui.commontoolslibrary.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qishui.commontoolslibrary.exception.ErrorHandle;

/**
 * Created by zhou on 2018/12/22.
 */

public class BitmapUtils {

    /**
     * desc 文件到bitmap
     * @param path
     * @return
     */
    public static Bitmap getBitmap(String path) {

        if (FileUtils.isExistFile(path)) {
            return BitmapFactory.decodeFile(path);
        }

        throw new ErrorHandle("暂无路径!");
    }
}

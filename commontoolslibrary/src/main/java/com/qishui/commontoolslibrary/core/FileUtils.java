package com.qishui.commontoolslibrary.core;

import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;
import com.qishui.commontoolslibrary.exception.ErrorHandle;

/**
 * Created by zhou on 2018/12/22.
 */

public class FileUtils {

    private FileUtils() {
        throw new ErrorHandle("禁止实例化!");
    }

    /**
     * desc sd卡是否存在
     */
    public static Boolean isExistSD() {

        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * desc 获取sd卡路径
     *
     * @return
     */
    public static String getSDPath() {
        if (isExistSD()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        throw new ErrorHandle("暂无SD卡!");
    }


    /**
     * desc 获得SD卡总大小
     */
    public static String getSDTotalSize() {
        StatFs stat = new StatFs(getSDPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(BaseQiShuiApplication.getContext(), blockSize * totalBlocks);
    }

    /**
     * desc SD卡是否大于50MB
     */
    public static Boolean isSD50MB() {

        StatFs stat = new StatFs(getSDPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getAvailableBlocks();
        return blockSize * totalBlocks > 50 * 1024 * 1024;

    }

    /**
     * desc 获得sd卡剩余容量，即可用大小
     */
    public static String getSDAvailableSize() {
        StatFs stat = new StatFs(getSDPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(BaseQiShuiApplication.getContext(), blockSize * availableBlocks);
    }

    /**
     * desc 获得机身内存总大小
     */
    public static String getRomTotalSize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(BaseQiShuiApplication.getContext(), blockSize * totalBlocks);
    }

    /**
     * desc 获得机身可用内存
     */
    public static String getRomAvailableSize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(BaseQiShuiApplication.getContext(), blockSize * availableBlocks);
    }




}

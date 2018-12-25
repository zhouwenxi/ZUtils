package com.qishui.commontoolslibrary.core;

import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;
import com.qishui.commontoolslibrary.exception.ErrorHandle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;

/**
 * Created by zhou on 2018/12/22.
 */

public class FileUtils {

    /**
     * 总目录
     */
    public static final String KEY_BASE_PATH = "/qishui";

    /**
     * 日志目录
     */
    public static final String KEY_FILE_LOG = KEY_BASE_PATH + "/log/";

    /**
     * assest目录
     */
    public static final String KEY_FILE_ASSEST = KEY_BASE_PATH + "/assest/";

    /**
     * 图片目录
     */
    public static final String KEY_FILE_PICTURE = KEY_BASE_PATH + "/picture/";


    /**
     * 最小50MB
     */
    public static final long MIN_MB = 50 * 1024 * 1024;
    /**
     * 编码格式
     */
    private static final String KEY_CHART = "utf-8";

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
        return blockSize * totalBlocks > MIN_MB;

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

    /**
     * desc 是否是目录
     *
     * @param dir
     * @return
     */
    public static Boolean isExistDir(String dir) {

        if (dir == null) {
            return false;
        }
        File file = new File(dir);
        if (file.exists()) {
            return file.isDirectory();
        }
        return false;
    }

    /**
     * desc 是否是文件
     *
     * @param path
     * @return
     */
    public static Boolean isExistFile(String path) {

        if (path == null) {
            return false;
        }
        File file = new File(path);
        if (file.exists()) {
            return file.isFile();
        }
        return false;
    }

    /**
     * desc 创建目录
     *
     * @param dir
     */
    public static Boolean createDir(String dir) {

        File file = new File(dir);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * desc 创建文件 删除存在文件
     */
    public static String createFileDelOld(String dir, String name) {

        createDir(dir);
        File file = new File(dir + File.separator + name);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();

    }

    /**
     * desc 创建文件,如果文件
     */
    public static String createFile(String dir, String name) {

        createDir(dir);
        File file = new File(dir + File.separator + name);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    /**
     * 创建png文件
     *
     * @return
     */
    public static String createNewPngFile() {
        return createFileDelOld(getSDPath() + KEY_FILE_PICTURE, "_" + System.currentTimeMillis() + ".png");
    }

    /**
     * desc 追加信息
     *
     * @param dir
     * @param name
     * @param text
     */
    public static void write(String dir, String name, String text) {
        Boolean hasAllPermission = PermissionUtils.hasAllPermission(BaseQiShuiApplication.getContext(), PermissionUtils.GROUP_STORAGE);
        if (!hasAllPermission) {
            LogUtils.e("u has not the permissions ");
            return;
        }

        String path = createFile(dir, name);

        if (!isExistFile(path)) {
            return;
        }

        try {
            //新建一个FileOutputStream()，把文件的路径传进去
            FileOutputStream fileOutputStream = new FileOutputStream(path, true);
            //给定一个字符串，将其转换成字节数组
            byte[] bytes = text.getBytes(KEY_CHART);
            //通过输出流对象写入字节数组
            fileOutputStream.write(bytes);
            //关流
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * desc 写入新文件、删除存在文件
     *
     * @param dir
     * @param name
     * @param text
     */
    public static void writeNewFile(String dir, String name, String text) {

        String path = createFileDelOld(dir, name);

        if (!isExistFile(path)) {
            return;
        }

        try {
            //新建一个FileOutputStream()，把文件的路径传进去
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            //给定一个字符串，将其转换成字节数组
            byte[] bytes = text.getBytes(KEY_CHART);
            //通过输出流对象写入字节数组
            fileOutputStream.write(bytes);
            //关流
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * desc 记录日志文件
     *
     * @param text
     */
    public static void writeLog(String text) {
        long currentTimeMillis = System.currentTimeMillis();
        String format = new SimpleDateFormat("yyyy年MM月dd日").format(currentTimeMillis);
        String dir = getSDPath() + KEY_FILE_LOG + format;
        String name = new SimpleDateFormat("HH").format(currentTimeMillis) + ".log";
        String last = SimpleDateFormat.getDateTimeInstance().format(currentTimeMillis) + "\n" + text;
        write(dir, name, last);
    }


    /**
     * desc 获取异常信息
     */
    public static String getException(Exception ex) {
        String ret = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pout = new PrintStream(out);
            ex.printStackTrace(pout);
            ret = new String(out.toByteArray(), KEY_CHART);
            pout.close();
            out.close();
            return ret;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * desc 获取异常信息
     */
    public static String getThrowable(Throwable throwable) {

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        String temp = writer.toString();
        printWriter.close();
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }

    /**
     * desc 复制文件数据
     *
     * @param name
     * @return
     */
    public static File copyAssestFile(String name) {

        String dir = getSDPath() + KEY_FILE_ASSEST;
        InputStream is;
        String filePath = createFileDelOld(dir, name);

        try {
            is = BaseQiShuiApplication.getContext().getAssets().open(name);
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            is.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(filePath);

    }

    /**
     * desc 获取assest文件字符串
     *
     * @param name
     * @return
     */
    public static String getAssestString(String name) {
        InputStream is = null;
        try {
            is = BaseQiShuiApplication.getContext().getAssets().open(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is2String(is);
    }

    /**
     * desc 将输入流转化为字符串
     *
     * @param is
     * @return
     */
    public static String is2String(final InputStream is) {
        if (is == null) return null;
        try {
            return new String(is2Bytes(is), KEY_CHART);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * desc 将输入流转化为字节
     *
     * @param is
     * @return
     */
    public static byte[] is2Bytes(final InputStream is) {
        if (is == null) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b, 0, 1024)) != -1) {
                os.write(b, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * desc 格式化目录大小
     *
     * @param path
     * @return
     */
    public static String getDirSize(String path) {
        return Formatter.formatFileSize(BaseQiShuiApplication.getContext(), getDirLength(new File(path)));
    }

    /**
     * desc 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirLength(final File dir) {
        if (!(dir.isDirectory())) return -1;
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    /**
     * 删除文件
     *
     * @param fileDir
     */
    public static void deleteDirAndFiles(File fileDir) {
        // 输出当前目录名称
        if (!fileDir.exists()) {
            return;
        }
        File[] file = fileDir.listFiles();
        if (file == null) {
            return;
        }
        if (file.length == 0) {
            fileDir.delete();
            return;
        }

        for (int i = 0; i < file.length; i++) {
            // 判断，如果是文件夹，则把当前文件夹的名称传回给这个函数，递归调用。
            if (file[i].isDirectory()) {
                // 把当前文件夹的名称和层级传回给本函数
                deleteDirAndFiles(file[i]);
            } else {
                file[i].delete();
            }
        }
    }

    /**
     * desc 获取文件名字
     *
     * @param filePath
     * @return
     */
    public static String getFileName(final String filePath) {
        if (filePath == null || !isExistFile(filePath)) return "";
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * desc 获取文件长度
     *
     * @param filePath
     * @return
     */
    public static long getFileSize(final String filePath) {
        if (filePath == null || !isExistFile(filePath)) return 0;
        return new File(filePath).length();
    }


}

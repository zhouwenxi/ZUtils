package com.qishui.commontoolslibrary.core;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by zhou on 2018/12/22.
 */

public class LogUtils {

    private static final String KEY_TAG = "QiShui";

    /**
     * 调试开关
     */
    private static boolean isDebug = true;
    /**
     * 是否需要文件记录
     */
    private static boolean needFileLog = true;

    /**
     * 日志打印
     *
     * @param objects
     */
    public static void e(Object... objects) {
        if (isDebug) {
            Log.e(KEY_TAG, getStringBuilder(objects).toString());
        }

    }

    /**
     * 日志记录
     *
     * @param objects
     */
    public static void toFile(Object... objects) {
        if (needFileLog) {
            FileUtils.writeLog(getStringBuilder(objects).toString());
        }
    }


    /**
     * 获取StringBuilder
     *
     * @param objects
     * @return
     */
    private static StringBuilder getStringBuilder(Object... objects) {
        StringBuilder sb = new StringBuilder();
        if (objects != null) {
            for (int i = 0; i < objects.length; i++)
                if (objects[i] == null) {
                    sb.append("下标为").append(i).append("打印结果输出为空!").append("\n");
                } else {
                    if (objects[i] instanceof HashMap) {
                        HashMap<Object, Object> map = (HashMap) objects[i];
                        for (Object obj : map.keySet()) {
                            sb.append("第").append(i + 1).append("参数，输出map\n").append("key: ").append(obj).append("value: ").append(map.get(obj)).append("\n");
                        }
                    } else if (objects[i] instanceof String && objects[i].toString().startsWith("<") && objects[i].toString().endsWith(">") && StringUtils.isXml(objects[i].toString())) {
                        String last = StringUtils.String2Xml(objects[i].toString());
                        sb.append("第").append(i + 1).append("参数，输出xml文档\n").append(last).append("\n");
                    } else {
                        sb.append("第").append(i + 1).append("参数，输出 ").append(objects[i]).append("\n");
                    }
                }
        } else {
            sb.append("打印结果输出为空!");
        }
        return sb;
    }


}

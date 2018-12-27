package com.qishui.commontoolslibrary.core;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * author： qishui
 * date: 2018/12/26  10:12
 * email: qihsuichixi@163.com
 * qq: 798150439
 * blog: http://zhouwenxi.top
 * desc: 热修复
 */
public class TinkerUtils {

    //类名
    private static final String DEX_BASECLASSLOADER_CLASS_NAME = "dalvik.system.BaseDexClassLoader";
    //扩展名
    private static final String DEX_FILE_E = "dex";
    //pathList中的dexElements字段
    private static final String DEX_ELEMENTS_FIELD = "dexElements";
    //BaseClassLoader中的pathList字段
    private static final String DEX_PATHLIST_FIELD = "pathList";



    public static void loadDex(Context context) {

        List<File> dexFiles = new ArrayList<>();
        File filesDir = context.getDir("dexs", Context.MODE_PRIVATE);
        // dex 缓存目录
        File dexCache = new File(filesDir, "cache");
        if (!dexCache.exists()) {
            dexCache.mkdirs();
        }

        File[] listFiles = filesDir.listFiles();

        for (File file : listFiles) {
            if (file.getName().endsWith(DEX_FILE_E)) {
                dexFiles.add(file);
            }
        }

        if (dexFiles.size() > 0) {
            loadDex(context, dexFiles.get(0), dexCache);
        }

    }

    /**
     * 加载 dex
     *
     * @param context
     * @param dexFile            需要加载的 dex
     * @param optimizedDirectory dex 加载缓存目录
     */
    private static void loadDex(Context context, File dexFile, File optimizedDirectory) {

        try {
            // 系统的ClassLoader
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
            Class baseDexClazzLoader = Class.forName(DEX_BASECLASSLOADER_CLASS_NAME);
            Field pathListFiled = baseDexClazzLoader.getDeclaredField(DEX_PATHLIST_FIELD);
            pathListFiled.setAccessible(true);
            Object pathListObject = pathListFiled.get(pathClassLoader);

            Class systemPathClazz = pathListObject.getClass();
            Field systemElementsField = systemPathClazz.getDeclaredField(DEX_ELEMENTS_FIELD);
            systemElementsField.setAccessible(true);
            Object systemElements = systemElementsField.get(pathListObject);

            // 自定义 ClassLoader
            DexClassLoader dexClassLoader = new DexClassLoader(dexFile.getAbsolutePath(), optimizedDirectory.getAbsolutePath(), null, context.getClassLoader());
            Class myDexClazzLoader = Class.forName(DEX_BASECLASSLOADER_CLASS_NAME);
            Field myPathListFiled = myDexClazzLoader.getDeclaredField(DEX_PATHLIST_FIELD);
            myPathListFiled.setAccessible(true);
            Object myPathListObject = myPathListFiled.get(dexClassLoader);

            Class myPathClazz = myPathListObject.getClass();
            Field myElementsField = myPathClazz.getDeclaredField(DEX_ELEMENTS_FIELD);
            myElementsField.setAccessible(true);
            Object myElements = myElementsField.get(myPathListObject);

            // 合并数组
            Class<?> sigleElementClazz = systemElements.getClass().getComponentType();
            int systemLength = Array.getLength(systemElements);
            int myLength = Array.getLength(myElements);
            int newSystenLength = systemLength + myLength;
            // 生成一个新的 数组，类型为Element类型
            Object newElementsArray = Array.newInstance(sigleElementClazz, newSystenLength);
            for (int i = 0; i < newSystenLength; i++) {
                if (i < myLength) {
                    Array.set(newElementsArray, i, Array.get(myElements, i));
                } else {
                    Array.set(newElementsArray, i, Array.get(systemElements, i - myLength));
                }
            }
            // 覆盖新数组
            Field elementsField = pathListObject.getClass().getDeclaredField(DEX_ELEMENTS_FIELD);
            elementsField.setAccessible(true);
            elementsField.set(pathListObject, newElementsArray);

            Toast.makeText(context, "修复完成!", Toast.LENGTH_SHORT).show();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

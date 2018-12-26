package com.qishui.commontoolslibrary.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by zhou on 2018/12/22.
 */

public class ReflectionUtils {


    /**
     * 反射获得指定对象(当前-》父类-》父类...)中的成员属性
     *
     * @param instance 指定对象
     * @param name     属性名
     * @return
     * @throws NoSuchFieldException
     */
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        Class clazz = instance.getClass();
        //反射获得
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(name);
                //如果无法访问 设置为可访问
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                //如果找不到往父类找
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }


    /**
     * 反射获得指定对象(当前-》父类-》父类...)中的函数
     *
     * @param instance       指定对象
     * @param name           方法名
     * @param parameterTypes 方法参数类型
     * @return
     * @throws NoSuchMethodException
     */
    public static Method findMethod(Object instance, String name, Class... parameterTypes) throws NoSuchMethodException {
        Class clazz = instance.getClass();
        while (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                //如果找不到往父类找
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
    }
}

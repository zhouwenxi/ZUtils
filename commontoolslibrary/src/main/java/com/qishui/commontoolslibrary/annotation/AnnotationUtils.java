package com.qishui.commontoolslibrary.annotation;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qishui.commontoolslibrary.click.QiShuiClick;
import com.qishui.commontoolslibrary.core.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhou on 2019/1/5.
 */

public class AnnotationUtils {

    /**
     * 绑定注册 activity绑定注册控件及点击事件
     *
     * @param activity
     */
    public static void initBinds(AppCompatActivity activity) {
        initBindView(activity);
        initBindOnclick(activity);
    }

    /**
     * fragment 绑定注册控件及点击事件
     *
     * @param fragment
     * @param view
     */
    public static void initBinds(Object fragment, View view) {
        initBindView(fragment, view);
        initBindOnclick(fragment, view);
    }


    /**
     * 注册添加布局
     *
     * @param activity
     */
    public static void initBindLayout(AppCompatActivity activity) {

        boolean flag = activity.getClass().isAnnotationPresent(QBingLayout.class);
        if (flag) {
            QBingLayout annotation = activity.getClass().getAnnotation(QBingLayout.class);
            int value = annotation.value();
            activity.setContentView(value);
        }

    }

    /**
     * 绑定控件
     *
     * @param activity
     */
    public static void initBindView(AppCompatActivity activity) {
        Field[] fields = activity.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if (fields[i].isAnnotationPresent(QBindView.class)) {
                QBindView view = fields[i].getAnnotation(QBindView.class);
                int value = view.value();
                try {
                    fields[i].set(activity, activity.findViewById(value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * fragment
     *
     * @param fragment
     * @param view
     */
    private static void  initBindView(Object fragment, View view) {

        Field[] fields = fragment.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if (fields[i].isAnnotationPresent(QBindView.class)) {
                QBindView bindView = fields[i].getAnnotation(QBindView.class);
                int value = bindView.value();
                try {
                    fields[i].set(fragment, view.findViewById(value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * fragment onclick
     *
     * @param fragment
     * @param view
     */
    public static void initBindOnclick(final Object fragment, View view) {

        final Method[] declaredMethods = fragment.getClass().getDeclaredMethods();

        for (int i = 0; i < declaredMethods.length; i++) {
            final Method method = declaredMethods[i];
            method.setAccessible(true);
            if (method.isAnnotationPresent(QBindOnclick.class)) {
                QBindOnclick onclick = method.getAnnotation(QBindOnclick.class);
                final int[] value = onclick.value();
                for (final int id : value) {

                    View viewById = view.findViewById(id);
                    if (viewById == null) {
                        LogUtils.e("no id ");
                        return;
                    }
                    viewById.setOnClickListener(new QiShuiClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.invoke(fragment, v);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }));
                }
            }
        }

    }

    /**
     * 注册点击事件
     *
     * @param activity
     */
    public static void initBindOnclick(final AppCompatActivity activity) {

        final Method[] declaredMethods = activity.getClass().getDeclaredMethods();

        for (int i = 0; i < declaredMethods.length; i++) {
            final Method method = declaredMethods[i];
            method.setAccessible(true);
            if (method.isAnnotationPresent(QBindOnclick.class)) {
                QBindOnclick onclick = method.getAnnotation(QBindOnclick.class);
                final int[] value = onclick.value();
                for (final int id : value) {
                    View view = activity.findViewById(id);
                    if (view == null) {
                        LogUtils.e("no id ");
                        return;
                    }
                    view.setOnClickListener(new QiShuiClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.invoke(activity, v);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }));
                }
            }
        }

    }


}

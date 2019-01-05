package com.qishui.commontoolslibrary.annotation;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhou on 2019/1/5.
 */

public class AnnotationUtils {

    /**
     * 绑定注册
     *
     * @param activity
     */
    public static void initBinds(AppCompatActivity activity) {
        initBindView(activity);
        initBindOnclick(activity);
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
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.invoke(activity,v);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }

    }


}

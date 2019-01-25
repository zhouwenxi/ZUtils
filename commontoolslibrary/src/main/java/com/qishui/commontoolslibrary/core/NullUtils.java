package com.qishui.commontoolslibrary.core;

import android.os.Build;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import com.qishui.commontoolslibrary.exception.ErrorHandle;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * Created by zhou on 2018/12/22.
 */

public class NullUtils {

    private NullUtils() {
        throw new ErrorHandle("禁止实例化");
    }

    /**
     * desc 是否为空
     *
     * @param obj
     * @return
     */
    public static Boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * desc 是否不为空
     *
     * @param obj
     * @return
     */
    public static Boolean isNotNull(Object obj) {
        return obj != null;
    }


    /**
     * desc 是否存在空值
     *
     * @param obj
     * @return
     */
    public static Boolean isExistNull(Object... obj) {

        if (isNull(obj)) {
            return true;
        }
        try {
            for (int i = 0; i < obj.length; i++) {
                if (isNull(obj[i])) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return {@code true}: 为空<br>{@code false}: 不为空
     */
    public static boolean isEmpty(Object obj) {

        if (obj == null) {
            return true;
        }
        if (obj instanceof String && obj.toString().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0;
        }
        return false;
    }

}

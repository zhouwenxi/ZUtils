package com.qishui.commontoolslibrary.core;

import com.qishui.commontoolslibrary.exception.ErrorHandle;

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
}

package com.qishui.commontoolslibrary.exception;

import android.text.TextUtils;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.bean.HttpBean;
import com.qishui.commontoolslibrary.constant.Keys;
import com.qishui.commontoolslibrary.core.GsonUtils;
import com.qishui.commontoolslibrary.notice.DIYToastUtils;
import com.qishui.commontoolslibrary.notice.ToastUtils;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

/**
 * Created by zhou on 2018/12/23.
 */

public class ErrorHandle extends RuntimeException {

    public ErrorHandle() {
    }

    public ErrorHandle(String message) {
        super(message);
    }

    public ErrorHandle(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorHandle(Throwable cause) {
        super(cause);
    }


    /**
     * 当网络请求没有正常响应的时候，根据异常类型进行相应的处理。
     *
     * @param e 异常实体类
     */
    public static String handleFailure(Exception e) {

        if (e != null) {
            if (e instanceof ConnectException) {
                return "网络连接异常";
            } else if (e instanceof SocketTimeoutException) {
                return "网络连接超时";
            } else if (e instanceof NoRouteToHostException) {
                return "无法连接到服务器";
            } else if (e instanceof ParseException) {
                return "数据解析异常";
            } else {
                return "发生未知错误";
            }
        } else {
            return "发生未知错误";
        }
    }

    /**
     * 当网络请求正常响应的时候，根据状态码处理通用部分的逻辑。
     *
     * @param httpBean
     * @return
     */
    public static Boolean handleResponse(HttpBean httpBean) {

        if (httpBean == null) {
            ToastUtils.showToastOnUiThread(R.string.network_parse_error);
            return false;
        }
        //请求成功
        if (httpBean.getCode() == Keys.KEY_SUCCESS_CODE_200) {
            return true;
        }
        ToastUtils.showToastOnUiThread(R.string.network_response_code_error + httpBean.getCode());
        return false;
    }

    /**
     * 处理字符串
     *
     * @param response
     * @return
     */
    public static Boolean handleResponse(String response) {

        String value = GsonUtils.getFieldValue(response, Keys.KEY_CODE);

        if (TextUtils.isEmpty(value)) {
            ToastUtils.showToastOnUiThread(R.string.network_parse_error);
            return false;
        }
        //请求成功
        if (TextUtils.equals(value, Keys.KEY_SUCCESS_STRING_200)) {
            return true;
        }

        ToastUtils.showToastOnUiThread(R.string.network_response_code_error + value);
        return false;
    }
}

package com.qishui.commontoolslibrary.bean;

import java.io.Serializable;

/**
 * Created by zhou on 2018/12/22.
 * 常用解析
 */

public class HttpBean<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

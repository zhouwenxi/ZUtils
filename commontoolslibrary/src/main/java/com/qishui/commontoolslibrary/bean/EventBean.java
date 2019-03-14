package com.qishui.commontoolslibrary.bean;

/**
 * 作者: create by qishui
 * 日期：2019/3/8  17:14
 * 邮箱: qishuichixi@163.com
 * 描述：传输event类型
 */
public class EventBean {

    private int type;
    private Object object;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public EventBean(int type, Object object) {
        this.type = type;
        this.object = object;
    }
}

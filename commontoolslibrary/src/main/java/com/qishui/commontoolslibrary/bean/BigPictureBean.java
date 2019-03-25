package com.qishui.commontoolslibrary.bean;

/**
 * Created by zhou on 2019/3/25.
 */
public class BigPictureBean {

    private String title;
    private Object path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

    public BigPictureBean(String title, Object path) {
        this.title = title;
        this.path = path;
    }
}

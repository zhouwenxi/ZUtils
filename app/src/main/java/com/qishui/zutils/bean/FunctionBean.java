package com.qishui.zutils.bean;

/**
 * Created by qishu on 2019/8/12.
 */

public class FunctionBean {

    private String name;
    private String desc;
    private int id;

    public FunctionBean() {
    }

    public FunctionBean(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public FunctionBean(String name, String desc, int id) {
        this.name = name;
        this.desc = desc;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

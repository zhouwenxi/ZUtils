package com.qishui.zutils.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.qishui.zutils.adapter.HomeMessageAdapter;

/**
 * 作者: create by qishui
 * 日期：2019/4/15  16:18
 * 邮箱: qishuichixi@163.com
 * 描述：
 */

public class MessageBean implements MultiItemEntity {

    private int itemType = HomeMessageAdapter.CONTENT;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public MessageBean(int itemType) {
        this.itemType = itemType;
    }
}

package com.qishui.zutils.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qishui.zutils.R;
import com.qishui.zutils.bean.MessageBean;


import java.util.List;

/**
 * 作者: create by qishui
 * 日期：2019/4/8  14:56
 * 邮箱: qishuichixi@163.com
 * 描述：消息页面处理
 */

public class HomeMessageAdapter extends BaseMultiItemQuickAdapter<MessageBean, BaseViewHolder> {

    //信息头部
    public static final int HEAD = 1;
    //信息内容
    public static final int CONTENT = 2;

    public HomeMessageAdapter(List<MessageBean> data) {
        super(data);
        addItemType(HEAD, R.layout.home_page_message_1);
        addItemType(CONTENT, R.layout.item_single_text_banner);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {


    }
}

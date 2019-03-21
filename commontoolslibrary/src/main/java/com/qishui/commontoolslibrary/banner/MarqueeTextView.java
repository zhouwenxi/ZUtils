package com.qishui.commontoolslibrary.banner;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 作者: create by qishui
 * 日期：2019/3/21  16:16
 * 邮箱: qishuichixi@163.com
 * 描述：单行文字跑马灯
 */

public class MarqueeTextView extends android.support.v7.widget.AppCompatTextView {

    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //实现了都获取焦点
    @Override
    public boolean isFocused() {
        return true;
    }

}

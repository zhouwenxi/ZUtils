package com.qishui.commontoolslibrary.click;

import android.view.View;

public class QiShuiClick implements View.OnClickListener {

    //源对象
    private View.OnClickListener origin;

    private long lastclick = 0;
    private long timems = 1000;

    public QiShuiClick(View.OnClickListener origin) {
        this.origin = origin;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastclick >= timems) {
            origin.onClick(v);
            lastclick = System.currentTimeMillis();
        }
    }

}
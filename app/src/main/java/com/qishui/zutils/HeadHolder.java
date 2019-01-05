package com.qishui.zutils;

import android.view.View;

import com.qishui.commontoolslibrary.holder.BaseHolder;

/**
 * Created by zhou on 2019/1/5.
 */

public class HeadHolder extends BaseHolder {

    public HeadHolder(int layoutID) {
        super(layoutID);
    }

    public static HeadHolder with(int layout) {
        return new HeadHolder(layout);
    }

    @Override
    protected void initEvent(View view) {

    }
}

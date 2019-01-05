package com.qishui.commontoolslibrary.holder;

import android.view.LayoutInflater;
import android.view.View;

import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;

/**
 * Created by zhou on 2019/1/5.
 */

public abstract class BaseHolder {

    private View mView;

    public BaseHolder(int layoutID) {
        try {
            mView = LayoutInflater.from(BaseQiShuiApplication.getContext()).inflate(layoutID, null);
            initEvent(mView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 处理事件
     *
     * @param view
     */
    protected abstract void initEvent(View view);

    public View getView() {
        return mView;
    }
}

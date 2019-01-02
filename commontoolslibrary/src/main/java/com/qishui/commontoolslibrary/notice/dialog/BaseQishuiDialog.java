package com.qishui.commontoolslibrary.notice.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/**
 * Created by zhou on 2018/12/22.
 */

public abstract class BaseQishuiDialog extends Dialog {

    private Activity mContext;

    public BaseQishuiDialog(@NonNull Activity context) {
        this(context, 0);
    }

    public BaseQishuiDialog(@NonNull Activity context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        initEvent(savedInstanceState);
    }

    /**
     * 显示dialog
     */
    public void showDialog() {

        //检查环境是否允许
        if (mContext != null && !mContext.isFinishing() && !isShowing()) {
            show();
        }

    }

    /**
     * 并没有完全被回收,关闭dialog
     */
    public void hideDialog() {

        //检查环境是否允许
        if (mContext != null && !mContext.isFinishing() && isShowing()) {
            hide();
        }

    }

    /**
     * 释放对话框所占的资源,在onDestroy中
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void dismissDialog() {

        //检查环境是否允许
        if (mContext != null && !mContext.isDestroyed() ) {
            dismiss();
        }
    }

    /**
     * 设置dialog其他屏幕地方是否触摸消失
     *
     * @param isSetCanceledOnTouchOutside
     * @return
     */
    public BaseQishuiDialog setCanceledOnTouchOutside(Boolean isSetCanceledOnTouchOutside) {
        setCanceledOnTouchOutside(isSetCanceledOnTouchOutside);
        return this;
    }

    /**
     * 设置回退键是否有效
     *
     * @param isCancelable
     * @return
     */
    public BaseQishuiDialog setCancelable(Boolean isCancelable) {
        setCancelable(isCancelable);
        return this;
    }

    /**
     * 加载布局
     *
     * @return
     */
    protected abstract int initLayout();

    /**
     * 处理事件
     *
     * @param savedInstanceState
     */
    protected abstract void initEvent(Bundle savedInstanceState);


}

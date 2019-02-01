package com.qishui.commontoolslibrary.notice.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.qishui.commontoolslibrary.R;

/**
 * 分享、支付、拍照片等底部弹出框
 */
public class BottomDialog extends BaseQishuiDialog {

    private int resId;
    private static BottomDialog bottomDialog;

    private BottomDialog(@NonNull Activity context) {
        this(context, 0);
    }

    private BottomDialog(@NonNull Activity context, int themeResId) {
        this(context, themeResId, 0);
    }

    private BottomDialog(@NonNull Activity context, int themeResId, int resId) {
        super(context, themeResId);
        this.resId = resId;
    }

    /**
     * 获取对象
     *
     * @param activity
     * @param resId
     * @return
     */
    public static BottomDialog with(Activity activity, int resId) {

        if (bottomDialog == null) {
            synchronized (BottomDialog.class) {
                if (bottomDialog == null) {
                    bottomDialog = new BottomDialog(activity, 0, resId);
                }
            }
        }

        return bottomDialog;
    }

    /**
     * 获取对象
     *
     * @return
     */
    public static BottomDialog getInstance() {
        return bottomDialog;
    }

    @Override
    protected int initLayout() {
        return resId;
    }

    @Override
    protected void initEvent(View view) {

        if (callBack != null) {
            callBack.handle(view);
        }
    }

    @Override
    public Window setWidthAndHeight() {
        Window window = super.setWidthAndHeight();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialogWindowAnimButtomToTop);
        return window;
    }

    /**
     * 点击事件
     */
    private CallBack callBack;

    public BottomDialog setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public interface CallBack {
        void handle(View view);
    }
}

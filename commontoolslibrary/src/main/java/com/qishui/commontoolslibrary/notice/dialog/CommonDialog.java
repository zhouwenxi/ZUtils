package com.qishui.commontoolslibrary.notice.dialog;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.click.QiShuiClick;

/**
* author： qishui
* date: 2019/1/2  16:28
* email: qihsuichixi@163.com
* qq: 798150439
* blog: http://zhouwenxi.top
* desc:
*/
public class CommonDialog extends BaseQishuiDialog {

    private LinearLayout mDialog_common_ll;
    private TextView mDialog_common_tv_content;
    private TextView mDialog_common_tv_left;
    private TextView mDialog_common_tv_right;
    private String mContent;
    private String mLeft;
    private String mRight;

    private void bindViews() {

        mDialog_common_ll = findViewById(R.id.dialog_common_ll);
        mDialog_common_tv_content = findViewById(R.id.dialog_common_tv_content);
        mDialog_common_tv_left = findViewById(R.id.dialog_common_tv_left);
        mDialog_common_tv_right = findViewById(R.id.dialog_common_tv_right);
    }

    public static CommonDialog with(Activity activity){
        return new CommonDialog(activity);
    }

    private CommonDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    protected int initLayout() {
        return R.layout.dialog_common;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        bindViews();
        setText();
        clickOut();
        clickLeft();
        clickRight();
    }

    /**
     * 设置文本
     */
    private void setText() {
        getDialogContentTv().setText(TextUtils.isEmpty(mContent) ? "请输入提示内容" : mContent);
        getDialogLeftTv().setText(TextUtils.isEmpty(mLeft) ? "取消" : mLeft);
        getDialogRightTv().setText(TextUtils.isEmpty(mRight) ? "确认" : mRight);
    }

    /**
     * 点击外面部分，关闭dialog
     */
    private void clickOut() {
        getDialogRoot().setOnClickListener(new QiShuiClick(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        }));
    }


    /**
     * 点击左边按钮
     */
    private void clickLeft() {
        getDialogLeftTv().setOnClickListener(new QiShuiClick(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.left();
                    dismissDialog();
                }
            }
        }));
    }

    /**
     * 点击右边按钮
     */
    private void clickRight() {
        getDialogRightTv().setOnClickListener(new QiShuiClick(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.right();
                    dismissDialog();
                }
            }
        }));
    }


    /**
     * 获取根布局
     *
     * @return
     */
    public LinearLayout getDialogRoot() {
        return mDialog_common_ll;
    }


    /**
     * 获取文字内容控件
     *
     * @return
     */
    public TextView getDialogContentTv() {
        return mDialog_common_tv_content;
    }

    /**
     * 设置文本内容
     *
     * @param text
     * @return
     */
    public CommonDialog setDialogContent(String text) {
        this.mContent=text;
        return this;
    }

    /**
     * 获取左边控件
     *
     * @return
     */
    public TextView getDialogLeftTv() {
        return mDialog_common_tv_left;
    }

    /**
     * 设置按钮名字
     *
     * @param text
     * @return
     */
    public CommonDialog setDialogLeftText(String text) {
        this.mLeft=text;
        return this;
    }

    /**
     * 获取右边控件
     *
     * @return
     */
    public TextView getDialogRightTv() {
        return mDialog_common_tv_right;
    }

    /**
     * 设置按钮名字
     *
     * @param text
     * @return
     */
    public CommonDialog setDialogRightText(String text) {
        this.mRight=text;
        return this;
    }

    private CallBack callBack;

    public interface CallBack {
        /**
         * 响应左边点击按钮点击事件
         */
        void left();

        /**
         * 点击右边点击处理事件
         */
        void right();
    }

    public CommonDialog setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }
}

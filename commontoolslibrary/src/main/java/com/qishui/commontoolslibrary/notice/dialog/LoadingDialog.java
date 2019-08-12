package com.qishui.commontoolslibrary.notice.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;

/**
 * author： qishui
 * date: 2019/1/3  9:35
 * email: qihsuichixi@163.com
 * qq: 798150439
 * blog: http://zhouwenxi.top
 * desc: 加载dialog
 */
public class LoadingDialog extends BaseQishuiDialog {


    private TextView tv;

    public LoadingDialog(@NonNull Activity context) {
        super(context);
    }

    @Override
    protected int initLayout() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void initEvent(View view) {

        setCanceledOnTouchOutside(false);
        setCancelable(false);
        tv = findViewById(R.id.dialog_loading_tv);

    }

    /**
     * 设置提示文本
     *
     * @param text
     * @return
     */
    public LoadingDialog setText(String text) {
        tv.setText(text);
        return this;
    }


}

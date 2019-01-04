package com.qishui.commontoolslibrary.notice.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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

    private String mText;

    private static LoadingDialog loadingDialog;

    private LoadingDialog(@NonNull Activity context) {
        super(context);
    }

    public static LoadingDialog with(Activity activity){
        if(loadingDialog==null){
            synchronized (LoadingDialog.class){
                if(loadingDialog==null){
                    loadingDialog=new LoadingDialog(activity);
                }
            }
        }
        return loadingDialog;
    }

    @Override
    protected int initLayout() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        setCanceledOnTouchOutside(false);
        TextView tv = findViewById(R.id.dialog_loading_tv);
        tv.setText(TextUtils.isEmpty(mText)?"加载中...":mText);

    }

    /**
     * 设置提示文本
     * @param text
     * @return
     */
    public LoadingDialog setText(String text){
        this.mText=text;
        return this;
    }

}

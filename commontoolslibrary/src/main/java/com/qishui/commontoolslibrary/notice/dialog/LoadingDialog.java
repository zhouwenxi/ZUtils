package com.qishui.commontoolslibrary.notice.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

public class LoadingDialog extends BaseQishuiDialog {

    public LoadingDialog(@NonNull Activity context) {
        super(context);
    }

    @Override
    protected int initLayout() {
        return 0;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

    }
}

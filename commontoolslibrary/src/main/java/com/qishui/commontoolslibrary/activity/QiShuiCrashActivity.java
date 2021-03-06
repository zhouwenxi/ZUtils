package com.qishui.commontoolslibrary.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.core.CrashUtils;
import com.qishui.commontoolslibrary.core.FileUtils;

public class QiShuiCrashActivity extends BaseQiShuiActivity {

    @Override
    protected int initLayout() {
        return R.layout.activity_qi_shui_crash;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        String extra = getIntent().getStringExtra(CrashUtils.KEY_ERRMSG);
        TextView qishui_crash_tv=findViewById(R.id.qishui_crash_tv);
        qishui_crash_tv.setText(extra);

        FileUtils.writeCrash(extra);
    }
}

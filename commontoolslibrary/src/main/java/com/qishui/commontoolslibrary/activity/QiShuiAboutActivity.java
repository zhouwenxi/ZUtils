package com.qishui.commontoolslibrary.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;


public class QiShuiAboutActivity extends BaseQiShuiActivity {

    /**
     * 设置背景颜色
     */
    private RelativeLayout qishui_splash_rl;

    /**
     * 应用图标
     */
    private ImageView qishui_splash_iv;

    /**
     * 提示文字
     */
    private TextView qishui_splash_tv;

    public static final String KEY_bg_color = "bg_color";
    private int color;

    @Override
    protected int initLayout() {
        return R.layout.activity_qi_shui_about;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {
        bindView();

    }

    private void bindView() {
        qishui_splash_rl=findViewById(R.id.qishui_splash_rl);
        qishui_splash_iv=findViewById(R.id.qishui_splash_iv);
        qishui_splash_tv=findViewById(R.id.qishui_splash_tv);
    }


}

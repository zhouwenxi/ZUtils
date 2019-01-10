package com.qishui.commontoolslibrary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.core.AppUtils;
import com.qishui.commontoolslibrary.core.CrashUtils;
import com.qishui.commontoolslibrary.core.FileUtils;

/**
 * author： qishui
 * date: 2019/1/10  9:21
 * email: qihsuichixi@163.com
 * qq: 798150439
 * blog: http://zhouwenxi.top
 * desc: 启动页模板
 */

public abstract class QiShuiSplashActivity extends BaseQiShuiActivity {

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

    private TextView qishui_splash_tv_copyright;

    private int color = 0;
    private int icon = 0;
    private String versionMsg;
    private String copyrightMsg;
    private int time = 3;


    @Override
    protected int initLayout() {
        //设置全屏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_qi_shui_splash;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {
        bindView();
        setData(color, icon, versionMsg,copyrightMsg,time);
        toNext(setActivity());
    }

    /**
     * 设置下一步
     * @return
     */
    protected abstract Class setActivity();


    private void bindView() {
        qishui_splash_rl = findViewById(R.id.qishui_splash_rl);
        qishui_splash_iv = findViewById(R.id.qishui_splash_iv);
        qishui_splash_tv = findViewById(R.id.qishui_splash_tv);
        qishui_splash_tv_copyright=findViewById(R.id.qishui_splash_tv_copyright);
    }


    /**
     * 设置是否需要图标
     * @param flag
     */
    public void setIconVisibility(Boolean flag) {
        if(flag){
            this.qishui_splash_iv.setVisibility(View.VISIBLE);
        }else {
            this.qishui_splash_iv.setVisibility(View.GONE);
        }
    }

    /**
     * 设置版本信息
     * @param flag
     */
    public void setVersionVisibility(Boolean flag) {
        if(flag){
            this.qishui_splash_tv.setVisibility(View.VISIBLE);
        }else {
            this.qishui_splash_tv.setVisibility(View.GONE);
        }
    }

    /**
     * 设置版权信息
     * @param flag
     */
    public void setCopyrightVisibility(Boolean flag) {

        if(flag){
            this.qishui_splash_tv_copyright.setVisibility(View.VISIBLE);
        }else {
            this.qishui_splash_tv_copyright.setVisibility(View.GONE);
        }
    }

    /**
     * 设置数据
     *
     * @param color
     * @param icon
     * @param msg
     */
    public void setData(int color, int icon, String msg,String copyrightMsg,int time) {

        this.color = color;
        this.icon = icon;
        this.versionMsg = msg;
        this.copyrightMsg=copyrightMsg;
        this.time=time;

        if (color != 0) {
            qishui_splash_rl.setBackgroundColor(color);
        }
        if (icon != 0) {
            qishui_splash_iv.setImageResource(icon);
        }

        if (TextUtils.isEmpty(copyrightMsg)) {
            copyrightMsg="copyright@ 2018 by QiShui";
        }
        qishui_splash_tv_copyright.setText(copyrightMsg);

        if (TextUtils.isEmpty(msg)) {
            msg="当前版本: "+AppUtils.getAppVirsionName();
        }
        qishui_splash_tv.setText(msg);

        //设置最大时间5s
        if (time >= 7) {
            time = 7;
        }
        //设置最小时
        if (time <= 0) {
            time = 1;
        }

    }

    /**
     * time s  跳转 clazz
     */
    public void toNext(final Class clazz) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    startActivityFinish(clazz);
                } catch (Exception e) {
                    String exception = FileUtils.getException(e);
                    Intent intent = new Intent(QiShuiSplashActivity.this, QiShuiCrashActivity.class);
                    intent.putExtra(CrashUtils.KEY_ERRMSG, exception);
                    startActivity(intent);
                    finish();
                }
            }
        }, time * 1000);
    }


}

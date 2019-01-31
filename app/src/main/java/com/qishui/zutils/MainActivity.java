package com.qishui.zutils;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.core.PermissionUtils;
import com.qishui.commontoolslibrary.core.TimerUtils;
import com.qishui.commontoolslibrary.update.UpdateCheckUtils;


public class MainActivity extends BaseQiShuiActivity {

    @QBindView(R.id.btn1)
    Button btn1;
    @QBindView(R.id.btn2)
    Button btn2;
    @QBindView(R.id.tv_timer)
    TextView tv_timer;
    @QBindView(R.id.tv_timer2)
    TextView tv_timer2;


    TimerUtils timer;
    TimerUtils timer2;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initEvent(Bundle savedInstanceState) {
        initPermissions();

        timer = TimerUtils.with();
        timer2 = TimerUtils.with();
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.setCallBack(new TimerUtils.CallBack() {

                    @Override
                    public void updateUI(String time) {
                        tv_timer.setText("倒計時：" + time + " s");
                    }

                    @Override
                    public void endUI() {

                        toast("结束倒计时!");
                    }
                }).countdownStart(60, 1);

                timer2.setCallBack(new TimerUtils.CallBack() {
                    @Override
                    public void updateUI(String time) {
                        tv_timer2.setText("计时：" + time);
                    }

                    @Override
                    public void endUI() {

                    }
                }).strat(1);
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://www.mzict.com:8081/tongtaiOA/api/android/download?filepath=2019011704051248241.apk&filename=OA9_last.apk";

                UpdateCheckUtils.with(MainActivity.this)
                        .setMode(UpdateCheckUtils.VERSONCODE)
                        .setVersionCode(90)
                        .setUrl(url)
                        .check();

            }
        });


    }

    void initPermissions() {
        PermissionUtils.with(this)
                .addPermissions(PermissionUtils.GROUP_STORAGE)
                .addPermissions(PermissionUtils.GROUP_CAMERA)
                .addPermissions(PermissionUtils.GROUP_LOCATION)
                .request()
                .setCallback(new PermissionUtils.Callback() {
                    @Override
                    public void refuse() {
                        PermissionUtils.goSetInfo(MainActivity.this, PermissionUtils.getPermission(), new PermissionUtils.SetCallback() {
                            @Override
                            public void onclikCancle() {

                                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timer != null) {
            timer.cancel();
        }

        if (timer2 != null) {
            timer2.cancel();
        }
    }
}

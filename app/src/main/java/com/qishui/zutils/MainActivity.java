package com.qishui.zutils;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.core.PermissionUtils;
import com.qishui.commontoolslibrary.notice.PopupUtils;
import com.qishui.commontoolslibrary.update.UpdateCheckUtils;
import com.qishui.commontoolslibrary.view.HeadView;


public class MainActivity extends BaseQiShuiActivity {

    @QBindView(R.id.btn1)
    Button btn1;
    @QBindView(R.id.btn2)
    Button btn2;
    @QBindView(R.id.hv)
    HeadView hv;


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initEvent(Bundle savedInstanceState) {
        initPermissions();

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupUtils.with(MainActivity.this)
                        .setTargetView(btn2)
                        .setContentView(R.layout.popup_left_right_center)
                        .setBackGroundLevel(0.8f)
                        .setWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setCallBack(new PopupUtils.CallBack() {
                            @Override
                            public void dissmiss() {

                            }

                            @Override
                            public void handle(View view) {

                            }
                        })
                        .show(PopupUtils.Position.top_left);
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


}

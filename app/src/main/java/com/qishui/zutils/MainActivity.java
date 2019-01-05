package com.qishui.zutils;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.annotation.QBingLayout;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.core.PermissionUtils;

@QBingLayout(R.layout.activity_main)
public class MainActivity extends BaseQiShuiActivity {

    @QBindView(R.id.btn1)
    Button btn1;
    @QBindView(R.id.btn2)
    Button btn2;

    @Override
    protected void initEvent(Bundle savedInstanceState) {
        initPermissions();

        btn1.setText("Hello");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("~~~~~~~~~~~~~~~~~~~~~~");
            }
        });

        new HeadHolder(0).getView();
    }

    void  initPermissions(){
        PermissionUtils.with(this).
                addPermissions(PermissionUtils.GROUP_STORAGE)
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

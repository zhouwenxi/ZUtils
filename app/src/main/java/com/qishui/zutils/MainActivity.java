package com.qishui.zutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qishui.commontoolslibrary.core.PermissionUtils;
import com.qishui.commontoolslibrary.notice.dialog.CommonDialog;
import com.qishui.commontoolslibrary.notice.dialog.LoadingDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionUtils.with(this).
                addPermissions(PermissionUtils.GROUP_STORAGE)
                .addPermissions(PermissionUtils.GROUP_CAMERA)
                .addPermissions(PermissionUtils.GROUP_LOCATION)
                .request()
                .setCallback(new PermissionUtils.Callback() {
                    @Override
                    public void refuse() {
                        PermissionUtils.goSetInfo(MainActivity.this, PermissionUtils.getPermission(), null);
                    }
                });


    }

    public void onclick1(View view) {

        CommonDialog.with(this,CommonDialog.STYLE_ANDROID).setDialogTitle("添书").setDialogContent("是否将本书加入书架?").setDialogRightText("加入书架").setDialogLeftText("不了").setCallBack(new CommonDialog.CallBack() {
            @Override
            public void left() {

            }

            @Override
            public void right() {

            }
        }).showDialog();

    }


}

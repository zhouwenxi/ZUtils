package com.qishui.zutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qishui.commontoolslibrary.core.PermissionUtils;
import com.qishui.commontoolslibrary.notice.dialog.CommonDialog;

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

        CommonDialog.with(this).setDialogContent("Hello World ").setCallBack(new CommonDialog.CallBack() {
            @Override
            public void left() {
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void right() {
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
        }).showDialog();

    }


}

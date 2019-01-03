package com.qishui.zutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qishui.commontoolslibrary.core.PermissionUtils;

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
                        PermissionUtils.goSetInfo(MainActivity.this, PermissionUtils.getPermission(), new PermissionUtils.SetCallback() {
                            @Override
                            public void onclikCancle() {

                                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


    }

    public void onclick1(View view) {

    }

    public void onclick2(View view) {

    }

}

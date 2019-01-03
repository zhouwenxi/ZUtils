package com.qishui.zutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qishui.commontoolslibrary.core.PermissionUtils;
import com.qishui.commontoolslibrary.notice.dialog.ListDialog;

import java.util.ArrayList;
import java.util.List;

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

        List<Bean> list=new ArrayList<Bean>();
        list.add(new Bean("0x1233","hello world 0","江西"));
        list.add(new Bean("0x1234","hello world 1","北京"));
        list.add(new Bean("0x1235","hello world 2","上海"));


        new ListDialog<Bean>(this).setDialogLv(list).setCallback(new ListDialog.CallBack<Bean>() {
            @Override
            public void disPlay(Bean bean, int position) {

                Toast.makeText(MainActivity.this, bean.getCode(), Toast.LENGTH_SHORT).show();
            }
        }).showDialog();



    }

    public void onclick2(View view) {


    }

}

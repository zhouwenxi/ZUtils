package com.qishui.zutils;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qishui.commontoolslibrary.core.FileUtils;
import com.qishui.commontoolslibrary.core.PermissionUtils;
import com.qishui.commontoolslibrary.core.TinkerUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

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
        String[] s = new String[]{"A", "B", "C"};
        int index = new Random().nextInt(10);
        if ("A".equals(s[index])) {
            Toast.makeText(this, "~~~~~~~~~~~~~", Toast.LENGTH_SHORT).show();
        }
      //  Toast.makeText(this, "last"+"~~~~~~~~~~~~~"+index, Toast.LENGTH_SHORT).show();
    }

    public void onclick2(View view) {

        // 修复包保存为 /data/data/包名/app_dexs/out.dex
        File filesDir = getDir("dexs", Context.MODE_PRIVATE);
        String name = "out.dex";

        String dexFile = FileUtils.createFileDelOld(filesDir.getAbsolutePath(), name);

        // 获取 sd 卡下的 out.dex
        try {
            InputStream is = new FileInputStream(new File(Environment.getExternalStorageDirectory(), name));
            FileOutputStream os = new FileOutputStream(dexFile);
            FileUtils.copyIS2OS(is, os);
            TinkerUtils.loadDex(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}

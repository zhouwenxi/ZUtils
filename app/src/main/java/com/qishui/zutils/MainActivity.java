package com.qishui.zutils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qishui.commontoolslibrary.cache.CacheManager;
import com.qishui.commontoolslibrary.core.FileUtils;
import com.qishui.commontoolslibrary.core.LogUtils;
import com.qishui.commontoolslibrary.core.PermissionUtils;
import com.qishui.commontoolslibrary.http.HttpManager;
import com.qishui.commontoolslibrary.http.callback.FileCallBack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

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

        String destFileDir = FileUtils.KEY_FILE_CACHE;
        final String url = "https://imgsa.baidu.com/exp/w=500/sign=6aaae3d9163853438ccf8721a312b01f/8435e5dde71190ef2946826ac81b9d16fcfa60c1.jpg";
        HttpManager.with().getProxy().downloadFile(url, destFileDir, "", new FileCallBack() {
            @Override
            protected void onEasyInProgress(float progress) {
                LogUtils.e("下载进度:" + progress);
            }

            @Override
            protected void onEasySuccess(String result) {

                CacheManager.with().putObject(url,result);
                CacheManager.with().putBitmap(url, BitmapFactory.decodeFile(result));
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();

            }

            @Override
            protected void onEasyError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}

package com.qishui.zutils;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qishui.commontoolslibrary.core.FileUtils;
import com.qishui.commontoolslibrary.core.LogUtils;
import com.qishui.commontoolslibrary.core.PermissionUtils;
import com.qishui.commontoolslibrary.http.HttpManager;
import com.qishui.commontoolslibrary.http.callback.GsonCallBack;
import com.qishui.commontoolslibrary.http.callback.StringCallBack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
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

        String url = "http://www.mzict.com:8081/tongtaiOA/api/data/list";
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", "0ffe1106c6a84ed8b4a38a17c0646f95");
        HttpManager.with().getProxy().get(url, params, new GsonCallBack<Bean>() {


            @Override
            public void onLast() {
                Toast.makeText(MainActivity.this, "end~~~~", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onEasySuccess(Bean result) {

                Toast.makeText(MainActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                List<Bean.DataBean> data = result.getData();
                LogUtils.e(data);
            }

            @Override
            protected void onEasyError(String message) {

                LogUtils.e(message);
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

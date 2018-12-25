package com.qishui.zutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qishui.commontoolslibrary.core.LogUtils;
import com.qishui.commontoolslibrary.core.PermissionUtils;

import java.util.HashMap;

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


        LogUtils.e(1, 2, 3, 4, "hello world !");
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";
        LogUtils.e(xml);

        HashMap map=new HashMap<>();
        map.put("1",2);
        map.put("2",2.999);
        map.put("age","ttt");
        LogUtils.e(map);

        LogUtils.toFile(1,2,3,"hello world !",xml,map);

    }
}

package com.qishui.zutils;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qishui.commontoolslibrary.activity.QiShuiMainStyle04Activity;
import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.banner.BannerView;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.core.PermissionUtils;
import com.qishui.commontoolslibrary.core.UiUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseQiShuiActivity {

    @QBindView(R.id.btn1)
    Button btn1;
    @QBindView(R.id.btn2)
    Button btn2;
    @QBindView(R.id.banner)
    BannerView bannerView;

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
                startActivity(QiShuiMainStyle04Activity.class);
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AboutActivity.class);
            }
        });


     //   List<Integer> list = new ArrayList<>();
       // list.add((R.layout.state_data_network_error));
       // bannerView.setListResIds(list).showView();

        List<View> list = new ArrayList<>();
        list.add(UiUtils.inflate(R.layout.state_data_network_error));
        bannerView.setListViews(list).showView();
    }

    void initPermissions() {
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


    @Override
    protected void onStart() {
        super.onStart();
        bannerView.stratPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bannerView.stopPlay();
    }
}

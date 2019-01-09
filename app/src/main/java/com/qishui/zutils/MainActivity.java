package com.qishui.zutils;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.core.PermissionUtils;
import com.qishui.commontoolslibrary.state.StateLayoutManager;


public class MainActivity extends BaseQiShuiActivity {

    private static final String TAG = "MainActivity";
    @QBindView(R.id.btn1)
    Button btn1;
    @QBindView(R.id.btn2)
    Button btn2;
    @QBindView(R.id.ll_state)
    LinearLayout ll_state;
    StateLayoutManager stateLayoutManager;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setStateLayoutAttrs() {
        stateLayoutManager = StateLayoutManager.with(ll_state);
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {
        initPermissions();


        stateLayoutManager.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stateLayoutManager.setCallBack(new StateLayoutManager.CallBack() {
                    @Override
                    public void handle(View view) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "xxoxoxoxoxoxo", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).showDataEmpty();
            }
        }, 2000);


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





}

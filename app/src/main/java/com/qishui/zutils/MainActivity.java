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
import com.qishui.commontoolslibrary.state.StateInterface;
import com.qishui.commontoolslibrary.state.StateManager;


public class MainActivity extends BaseQiShuiActivity {

    @QBindView(R.id.btn1)
    Button btn1;
    @QBindView(R.id.btn2)
    Button btn2;
    @QBindView(R.id.ll_state)
    LinearLayout ll_state;
    StateManager  loadingController;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {
        initPermissions();

        btn1.setText("Hello");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingController.showError();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("~~~~~~~~~~~~~~~~~~~~~~");
                loadingController.showLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingController.showError();
                    }
                },3000);


            }
        });

        loadingController = new StateManager.Builder(MainActivity.this, ll_state)
                .setLoadingMessage("加载中...")
                .setErrorMessage("加载失败，请稍后重试~")
                .setEmptyMessage("暂无数据~")
                .setOnNetworkErrorRetryClickListener(new StateManager.CallBack() {
                    @Override
                    public void onClick() {
                        if(loadingController!=null){
                            loadingController.showError();
                        }
                    }
                })
                .setOnErrorRetryClickListener("点我重试", new StateManager.CallBack() {
                    @Override
                    public void onClick() {
                        loadingController.showContent();
                    }
                })
                .with();

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

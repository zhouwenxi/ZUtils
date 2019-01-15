package com.qishui.zutils;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
    @QBindView(R.id.banner2)
    BannerView bannerView2;

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

        //数据
        List<Object> list = new ArrayList<>();
        list.add(R.drawable.banner);
        bannerView.setListViews(list).setImageLoader(new BannerView.ImageLoader() {
            @Override
            public void show(Context context, Object obj, ImageView iv) {
                iv.setImageResource((Integer) obj);
            }
        }).setBannerClick(new BannerView.BannerCallBack() {
            @Override
            public void click(View view, int position) {
                toast("QQQQQQQQQQQQQQQ");
            }
        }).showView();

        //数据
        List<Integer> list2 = new ArrayList<>();
        list2.add(R.layout.state_data_empty);
        list2.add(R.layout.state_data_error);

        final List<String> listTitle = new ArrayList<>();
        listTitle.add("康熙有多数老年人都存在的“不服输”的心理。虽然年龄摆在那儿了，但是仍然不承认自己力不从心");
        listTitle.add("年迈的狮子是最敏感的");

        //控件
        bannerView2.setLoadView(R.layout.view_banner_1);
        View bannerView = bannerView2.getBannerView();
        ViewPager vp = UiUtils.findViewById(bannerView, R.id.banner_vp);
        final TextView titleTv = UiUtils.findViewById(bannerView, R.id.banner_tv);
        titleTv.setText(listTitle.get(0));
        bannerView2.setViewPager(vp).setListResIds(list2).setDelayTime(3500).sePageChangeListenert(new BannerView.PageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                titleTv.setText(listTitle.get(position));
            }
        }).showView();
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
        bannerView2.stratPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bannerView.stopPlay();
        bannerView2.stopPlay();
    }
}

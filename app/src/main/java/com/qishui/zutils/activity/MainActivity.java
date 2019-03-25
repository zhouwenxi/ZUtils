package com.qishui.zutils.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qishui.commontoolslibrary.adapter.CommonFragmentPagerAdapter;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.core.PermissionUtils;
import com.qishui.commontoolslibrary.view.CustomTabView;
import com.qishui.zutils.R;
import com.qishui.zutils.fragment.InFragment;
import com.qishui.zutils.fragment.UIFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * sample
 */
public class MainActivity extends BaseQiShuiActivity {

    private CustomTabView customTabView;
    private ViewPager vp;
    public static final String[] mTabTitle = new String[]{"外在", "内部"};


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initEvent(Bundle savedInstanceState) {
        initPermissions();
        init();
    }

    private void init() {
        vp = findViewById(R.id.home_container_vp);
        customTabView = findViewById(R.id.custom_tab_container);
        vp.setAdapter(new CommonFragmentPagerAdapter(getSupportFragmentManager(), getFragments()));
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        customTabView.addTabView(R.layout.view_custom, mTabTitle.length).setOnTabCheckListener(new CustomTabView.OnTabCheckListener() {

            @Override
            public void onSetTabAttrs(List<View> mTabViews) {
                for (int position = 0; position < mTabViews.size(); position++) {
                    TextView textView = mTabViews.get(position).findViewById(R.id.custom_tv);
                    textView.setText(mTabTitle[position]);
                }
            }

            @Override
            public void onTabSelected(View view, int position) {

                TextView textView = view.findViewById(R.id.custom_tv);
                ImageView iv = view.findViewById(R.id.custom_iv);
                textView.setTextColor(Color.BLUE);
                iv.setImageResource(R.drawable.ic_tab_strip_icon_feed_selected);
                vp.setCurrentItem(position, false);
            }

            @Override
            public void onTabUnSelected(View view, int position) {

                TextView textView = view.findViewById(R.id.custom_tv);
                ImageView iv = view.findViewById(R.id.custom_iv);
                textView.setTextColor(Color.BLACK);
                iv.setImageResource(R.drawable.ic_tab_strip_icon_feed);
            }
        }).setCurrentItem(0);
    }

    class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            customTabView.setCurrentItem(position);
        }
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new UIFragment());
        fragments.add(new InFragment());
        return fragments;
    }

    void initPermissions() {
        PermissionUtils.with(this)
                .addPermissions(PermissionUtils.GROUP_STORAGE)
                .addPermissions(PermissionUtils.GROUP_CAMERA)
                .addPermissions(PermissionUtils.GROUP_LOCATION)
                .request();
    }
}

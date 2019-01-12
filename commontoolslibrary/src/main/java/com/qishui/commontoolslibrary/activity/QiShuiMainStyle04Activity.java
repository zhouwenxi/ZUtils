package com.qishui.commontoolslibrary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.adapter.CommonFragmentPagerAdapter;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.fragment.HomeFragment1;
import com.qishui.commontoolslibrary.fragment.HomeFragment2;
import com.qishui.commontoolslibrary.fragment.HomeFragment3;
import com.qishui.commontoolslibrary.fragment.HomeFragment4;
import com.qishui.commontoolslibrary.view.CustomTabView;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义控件实现主页面框架
 */
public class QiShuiMainStyle04Activity extends BaseQiShuiActivity {

    private CustomTabView customTabView;
    private ViewPager vp;
    public static final String[] mTabTitle = new String[]{"首页", "发现", "关注", "我的"};

    @Override
    protected int initLayout() {
        return R.layout.activity_qi_shui_main_style04;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        vp = findViewById(R.id.home_container_vp);
        customTabView = findViewById(R.id.custom_tab_container);
        vp.setAdapter(new CommonFragmentPagerAdapter(getSupportFragmentManager(), getFragments()));
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        customTabView.addTabView(R.layout.view_custom, 4).setOnTabCheckListener(new CustomTabView.OnTabCheckListener() {

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
                textView.setTextColor(Color.RED);
                iv.setImageResource(R.drawable.ic_tab_strip_icon_feed_selected);
                toast(mTabTitle[position]);
                vp.setCurrentItem(position, false);
            }

            @Override
            public void onTabUnSelected(View view, int position) {

                TextView textView = view.findViewById(R.id.custom_tv);
                ImageView iv = view.findViewById(R.id.custom_iv);
                textView.setTextColor(Color.BLUE);
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
        fragments.add(new HomeFragment1());
        fragments.add(new HomeFragment2());
        fragments.add(new HomeFragment3());
        fragments.add(HomeFragment4.newInstance("tab"));
        return fragments;
    }

}

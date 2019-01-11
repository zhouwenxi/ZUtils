package com.qishui.commontoolslibrary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.view.CustomTabView;

import java.util.List;

/**
 * 自定义控件实现主页面框架
 */
public class QiShuiMainStyle04Activity extends BaseQiShuiActivity {

    private CustomTabView customTabView;
    private Fragment home_container;
    public static final String[] mTabTitle = new String[]{"首页", "发现", "关注", "我的"};


    @Override
    protected int initLayout() {
        return R.layout.activity_qi_shui_main_style04;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        customTabView = findViewById(R.id.custom_tab_container);
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
}

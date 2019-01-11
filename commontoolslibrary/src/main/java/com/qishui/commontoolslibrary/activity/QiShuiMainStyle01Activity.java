package com.qishui.commontoolslibrary.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.core.UiUtils;
import com.qishui.commontoolslibrary.fragment.HomeFragment1;
import com.qishui.commontoolslibrary.fragment.HomeFragment2;
import com.qishui.commontoolslibrary.fragment.HomeFragment3;
import com.qishui.commontoolslibrary.fragment.HomeFragment4;

/**
 * TabLayout + Fragment 实现主页框架
 */
public class QiShuiMainStyle01Activity extends BaseQiShuiActivity {

    private TabLayout mTabLayout;
    private Fragment[]mFragmensts;

    @Override
    protected int initLayout() {
        return R.layout.activity_qi_shui_main_style01;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        mFragmensts = DataGenerator.getFragments();
        mTabLayout = findViewById(R.id.bottom_tab_layout);
        mTabLayout.addOnTabSelectedListener(new MyTabSelectedListener());
        // 提供自定义的布局添加Tab
        for(int i=0;i<mFragmensts.length;i++){
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setCustomView(DataGenerator.getTabView(0,i));
            LinearLayout layout=tab.view;
            layout.setBackgroundColor(UiUtils.getColor(R.color.colorWhite));
            mTabLayout.addTab(tab);
        }

    }

    /**
     * 监听事件
     */
    class MyTabSelectedListener implements TabLayout.OnTabSelectedListener{

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            onTabItemSelected(tab.getPosition());
            // Tab 选中之后，改变各个Tab的状态
            for (int position=0;position<mTabLayout.getTabCount();position++){
                View view = mTabLayout.getTabAt(position).getCustomView();
                DataGenerator.setAttrs(tab.getPosition(), position, view);
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }



    /**
     * 切换
     * @param position
     */
    private void onTabItemSelected(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = mFragmensts[0];
                break;
            case 1:
                fragment = mFragmensts[1];
                break;
            case 2:
                fragment = mFragmensts[2];
                break;
            case 3:
                fragment = mFragmensts[3];
                break;
        }
        if(fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
        }
    }

    /**
     * 设置数据
     */
    public static class DataGenerator {

        public static final int []mTabRes = new int[]{R.drawable.tab_home_selector,R.drawable.tab_home_selector,R.drawable.tab_home_selector,R.drawable.tab_home_selector};
        public static final int []mTabResPressed = new int[]{R.drawable.ic_tab_strip_icon_feed_selected,R.drawable.ic_tab_strip_icon_feed_selected,R.drawable.ic_tab_strip_icon_feed_selected,R.drawable.ic_tab_strip_icon_feed_selected};
        public static final String []mTabTitle = new String[]{"首页","发现","关注","我的"};

        public static Fragment[] getFragments(){
            Fragment fragments[] = new Fragment[4];
            fragments[0] = new HomeFragment1();
            fragments[1] = new HomeFragment2();
            fragments[2] = new HomeFragment3();
            fragments[3] = HomeFragment4.newInstance("tab");
            return fragments;
        }

        /**
         * 获取Tab 显示的内容
         * @param position
         * @return
         */
        public static View getTabView(int curPosition,int position){
            View view =UiUtils.inflate(R.layout.home_tab_content);
            setAttrs(curPosition,position,view);
            return view;
        }

        /**
         * 设置属性
         * @param curPosition
         * @param position
         * @param view
         */
        private static void setAttrs(int curPosition, int position, View view) {
            ImageView icon = UiUtils.findViewById(view,R.id.tab_content_image);
            TextView text = UiUtils.findViewById(view,R.id.tab_content_text);
            text.setText(mTabTitle[position]);
            if(position == curPosition){ // 选中状态
                icon.setImageResource(DataGenerator.mTabResPressed[position]);
                text.setTextColor(UiUtils.getColor(android.R.color.black));
            }else{// 未选中状态
                icon.setImageResource(DataGenerator.mTabRes[position]);
                text.setTextColor(UiUtils.getColor(android.R.color.darker_gray));
            }
        }
    }


}

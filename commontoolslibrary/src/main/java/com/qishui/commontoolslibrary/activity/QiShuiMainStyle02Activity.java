package com.qishui.commontoolslibrary.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.fragment.HomeFragment1;
import com.qishui.commontoolslibrary.fragment.HomeFragment2;
import com.qishui.commontoolslibrary.fragment.HomeFragment3;
import com.qishui.commontoolslibrary.fragment.HomeFragment4;

/**
 * author： qishui
 * date: 2019/1/11  14:46
 * email: qihsuichixi@163.com
 * qq: 798150439
 * blog: http://zhouwenxi.top
 * desc: BottomNavigationView + Fragment  BottomNavigatonView 的tab 只能是3-5个，多了或者少了是会报错
 */
public class QiShuiMainStyle02Activity extends BaseQiShuiActivity {

    private BottomNavigationView mBottomNavigationView;
    private Fragment[] mFragments;

    @Override
    protected int initLayout() {
        return R.layout.activity_qi_shui_main_style02;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        mFragments = getFragments();
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 由于第一次进来没有回调onNavigationItemSelected，因此需要手动调用一下切换状态的方法
        onTabItemSelected(R.id.tab_menu_home);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onTabItemSelected(item.getItemId());
                return true;
            }
        });

    }


    private void onTabItemSelected(int id) {
        Fragment fragment = null;
        if (id == R.id.tab_menu_home) {
            fragment = mFragments[0];
        }

        if (id == R.id.tab_menu_discovery) {
            fragment = mFragments[1];
        }

        if (id == R.id.tab_menu_attention) {
            fragment = mFragments[2];
        }

        if (id == R.id.tab_menu_profile) {
            fragment = mFragments[3];
        }


        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container, fragment).commit();
        }
    }


    /**
     * 设置数据
     */
    public Fragment[] getFragments() {
        Fragment fragments[] = new Fragment[5];
        fragments[0] = new HomeFragment1();
        fragments[1] = new HomeFragment2();
        fragments[2] = new HomeFragment3();
        fragments[3] = HomeFragment4.newInstance("tab");
        return fragments;
    }

}

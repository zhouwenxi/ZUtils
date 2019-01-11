package com.qishui.commontoolslibrary.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.fragment.HomeFragment1;
import com.qishui.commontoolslibrary.fragment.HomeFragment2;
import com.qishui.commontoolslibrary.fragment.HomeFragment3;
import com.qishui.commontoolslibrary.fragment.HomeFragment4;
import com.qishui.commontoolslibrary.fragment.HomeFragment5;

/**
 * author： qishui
 * date: 2019/1/11  16:18
 * email: qihsuichixi@163.com
 * qq: 798150439
 * blog: http://zhouwenxi.top
 * desc: RadioGroup + RadioButton + Fragment
 */
public class QiShuiMainStyle03Activity extends BaseQiShuiActivity {

    private Fragment[] mFragments;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButtonHome;
    private ImageView imageView;
    private int index=0;

    @Override
    protected int initLayout() {
        return R.layout.activity_qi_shui_main_style03;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        setFragment();
    }

    private void setFragment() {
        mFragments = new Fragment[5];
        mFragments[0] = new HomeFragment1();
        mFragments[1] = new HomeFragment2();
        mFragments[2] = new HomeFragment3();
        mFragments[3] = HomeFragment4.newInstance("tab");
        mFragments[4] = new HomeFragment5();
        imageView = findViewById(R.id.radio_middle_iv);
        mRadioGroup = findViewById(R.id.radio_group_button);
        mRadioButtonHome = findViewById(R.id.radio_button_home);
        mRadioButtonHome.setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            Fragment mFragment = null;

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == R.id.radio_button_home) {
                    mFragment = mFragments[0];
                    index=0;
                }
                if (checkedId == R.id.radio_button_discovery) {
                    mFragment = mFragments[1];
                    index=1;
                }
                if (checkedId == R.id.radio_button_attention) {
                    mFragment = mFragments[2];
                    index=2;
                }
                if (checkedId == R.id.radio_button_profile) {
                    mFragment = mFragments[3];
                    index=3;
                }

                imageView.setImageResource(R.drawable.send);
                if (mFragments != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_container, mFragment).commit();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageView.setImageResource(R.drawable.send_select);
                if (mFragments[4] != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_container, mFragments[4]).commit();
                }
            }
        });

    }

}

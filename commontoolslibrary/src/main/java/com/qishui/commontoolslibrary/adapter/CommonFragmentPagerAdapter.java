package com.qishui.commontoolslibrary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zhou on 2019/1/12.
 * viewpager+fragment
 */

public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    public CommonFragmentPagerAdapter(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }
}
package com.qishui.zutils;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qishui.commontoolslibrary.annotation.QBindOnclick;
import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.base.BaseQiShuiFragment;

/**
 * Created by zhou on 2019/1/6.
 */

public class MainFragment extends BaseQiShuiFragment {

    @QBindView(R.id.fragment_tv1)
    TextView tv;
    @QBindView(R.id.fragment_tv2)
    TextView tv2;


    @Override
    protected int initLayout() {
        return R.layout.fragment_main_test;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        tv.setText("Hello");
        tv2.setText("world");

    }

    @QBindOnclick({R.id.fragment_tv1, R.id.fragment_tv2})
    void test(View view) {
        if (view.getId() == R.id.fragment_tv1) {
            toast("!!!!!!!!!!!!!!!!!!!!");
        }

        if (view.getId() == R.id.fragment_tv2) {
            toast("222222222222222");
        }
    }
}

package com.qishui.zutils;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qishui.commontoolslibrary.annotation.QBindOnclick;
import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.base.BaseQiShuiFragment;
import com.qishui.commontoolslibrary.state.StateLayoutManager;

/**
 * Created by zhou on 2019/1/6.
 */

public class MainFragment extends BaseQiShuiFragment {

    @QBindView(R.id.fragment_tv1)
    TextView tv;
    @QBindView(R.id.fragment_tv2)
    TextView tv2;
    @QBindView(R.id.fragment_ll)
    LinearLayout ll;

    private StateLayoutManager layoutManager;

    @Override
    protected int initLayout() {
        return R.layout.fragment_main_test;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        tv.setText("Hello");
        tv2.setText("world");

        layoutManager = StateLayoutManager.with(ll);

    }

    @QBindOnclick({R.id.fragment_tv1, R.id.fragment_tv2})
    void test(View view) {
        if (view.getId() == R.id.fragment_tv1) {
            toast("!!!!!!!!!!!!!!!!!!!!");
            layoutManager.showLoading();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    layoutManager.showContent();
                }
            }, 5000);
        }

        if (view.getId() == R.id.fragment_tv2) {
            toast("222222222222222");
        }
    }
}

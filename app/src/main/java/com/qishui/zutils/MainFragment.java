package com.qishui.zutils;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qishui.commontoolslibrary.annotation.QBindOnclick;
import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.base.BaseQiShuiFragment;
import com.qishui.commontoolslibrary.core.LogUtils;
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
    public void setStateLayoutAttrs() {
        layoutManager = StateLayoutManager
                .with(ll)
                .setLoadingColor(0xffFF4081)
                .setNetErrorColor(0xffFF4081);

    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {


        layoutManager.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutManager.showNetworkError();
            }
        }, 5000);

    }




}

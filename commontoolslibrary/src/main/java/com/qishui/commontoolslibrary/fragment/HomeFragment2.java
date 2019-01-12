package com.qishui.commontoolslibrary.fragment;

import android.view.View;
import android.widget.RelativeLayout;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiFragment;
import com.qishui.commontoolslibrary.core.LogUtils;
import com.qishui.commontoolslibrary.state.StateLayoutManager;

public class HomeFragment2 extends BaseQiShuiFragment {

    private RelativeLayout rl;


    private StateLayoutManager layoutManager;

    @Override
    public void setStateLayoutAttrs(View view) {
        rl=view.findViewById(R.id.home_fragment_rl);
        layoutManager = StateLayoutManager
                .with(rl)
                .setLoadingColor(0xffFF4081)
                .setNetErrorColor(0xffFF4081);
    }

    @Override
    protected int initLayout() {
        return R.layout.home_fragment_layout;
    }

    @Override
    protected void initEvent(View view) {

        layoutManager.showDataEmpty();
        LogUtils.e("HomeFragment2 initEvent");
    }
}

package com.qishui.zutils.fragment;

import android.view.View;
import android.widget.ImageView;

import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.base.BaseQiShuiFragment;
import com.qishui.commontoolslibrary.image.GlideUtils;
import com.qishui.commontoolslibrary.view.RatingStarView;
import com.qishui.commontoolslibrary.view.SwitchView;
import com.qishui.zutils.R;

public class UIFragment extends BaseQiShuiFragment {


    @QBindView(R.id.iv)
    ImageView iv;
    @QBindView(R.id.sw)
    SwitchView sw;
    @QBindView(R.id.rsv)
    RatingStarView rsv;

    @Override
    protected int initLayout() {
        return R.layout.fragment_ui;
    }

    @Override
    protected void initEvent(View view) {


       GlideUtils.display(getActivity(), "http://guolin.tech/book.png", iv);
       //GlideUtils.displayBigPic(getActivity(),"http://www.qqddc.com/downServer/dxh/2019/03/cp1_01.jpg",iv);
        sw.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {

                view.toggleSwitch(true);
                toast("toggleToOn...................");
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false);
                toast("toggleToOff...................");
            }
        });

        rsv.setRating(3.5f);

    }
}
package com.qishui.zutils;

import android.os.Bundle;
import android.view.View;

import com.qishui.commontoolslibrary.activity.QiShuiAboutActivity;

public class AboutActivity extends QiShuiAboutActivity {

    @Override
    protected void setEvent(Bundle savedInstanceState) {

        getmQishui_about_itemview1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("~~~~~~~~~~~~~~~~~~~~~~");
            }
        });
    }
}

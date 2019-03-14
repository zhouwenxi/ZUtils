package com.qishui.zutils.fragment;

import android.view.View;
import android.widget.ListView;

import com.qishui.commontoolslibrary.adapter.CommonLvAdapter;
import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.base.BaseQiShuiFragment;
import com.qishui.zutils.R;

/**
 * 添加人: add by qishui
 * 添加时间: 2019/3/14  17:18
 * 添加注释: 内在功能
 */
public class InFragment extends BaseQiShuiFragment {

    @QBindView(R.id.in_lv)
    ListView lv;

    @Override
    protected int initLayout() {
        return R.layout.fragment_in;
    }

    @Override
    protected void initEvent(View view) {

        lv.setAdapter(new CommonLvAdapter<String>(getActivity(),null,0) {
            @Override
            public void setData(BaseHolder holder, String item, int position) {

            }
        });
    }
}

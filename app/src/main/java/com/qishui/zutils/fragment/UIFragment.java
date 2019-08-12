package com.qishui.zutils.fragment;

import android.view.View;
import android.widget.ListView;

import com.qishui.commontoolslibrary.adapter.CommonLvAdapter;
import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.base.BaseQiShuiFragment;
import com.qishui.commontoolslibrary.core.UiUtils;
import com.qishui.commontoolslibrary.notice.DIYToastUtils;
import com.qishui.commontoolslibrary.view.HeadView;
import com.qishui.zutils.R;

import java.util.ArrayList;
import java.util.List;

public class UIFragment extends BaseQiShuiFragment {

    @QBindView(R.id.lv)
    ListView lv;
    @QBindView(R.id.home_hv)
    HeadView home_hv;

    @Override
    protected int initLayout() {
        return R.layout.fragment_ui;
    }

    @Override
    protected void initEvent(View view) {

        setTitle();

        showList();

    }

    /**
     * 展示列表数据
     */
    private void showList() {
        List<String> list = setListData();

        lv.setAdapter(new CommonLvAdapter<String>(getActivity(), list, R.layout.item_single_text) {
            @Override
            public void setData(BaseHolder holder, String item, final int position) {

                holder.setText(R.id.tv, item);

                holder.getView(R.id.ll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handle(position);
                    }
                });

            }
        });
    }


    /**
     * 返回列表数据
     *
     * @return
     */
    private List<String> setListData() {
        List<String> list = new ArrayList<>();
        list.add("开关");
        list.add("星星");
        return list;
    }

    private void setTitle() {
        home_hv.getLeftTv().setVisibility(View.GONE);
        home_hv.getLeftIv().setVisibility(View.GONE);
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/20  16:47
     * 添加注释: 设置跳转
     */
    private void handle(int position) {

        switch (position) {

            default:
                // toast("wait to handle ...");
                DIYToastUtils toastUtils = DIYToastUtils.with(getActivity());
                toastUtils.getTV().setTextColor(UiUtils.getColor(R.color.colorRedaa));
                toastUtils.getLL().setBackgroundColor(UiUtils.getColor(R.color.colorMain));
                toastUtils.setText("wait to handle ...").show();
                break;
        }

    }

}
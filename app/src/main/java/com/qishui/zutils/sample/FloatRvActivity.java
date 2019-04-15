package com.qishui.zutils.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.view.HeadView;
import com.qishui.zutils.R;
import com.qishui.zutils.adapter.HomeMessageAdapter;
import com.qishui.zutils.bean.MessageBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class FloatRvActivity extends BaseQiShuiActivity {

    @QBindView(R.id.home_message_hv)
    HeadView home_message_hv;
    @QBindView(R.id.home_message_rv)
    RecyclerView home_message_rv;
    private HomeMessageAdapter homeMessageAdapter;
    @QBindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @QBindView(R.id.message_head)
    LinearLayout message_head;
    private int headHeight;
    private int mCurrentPosition = 0;
    private List<MessageBean> list = new ArrayList<>();


    @Override
    protected int initLayout() {
        return R.layout.activity_float_rv;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {
        setList();
    }

    /**
     * 设置列表
     */
    private void setList() {

        list.add(new MessageBean(HomeMessageAdapter.HEAD));
        for (int i = 0; i < 15; i++) {
            list.add(new MessageBean(HomeMessageAdapter.CONTENT));
        }
        list.add(new MessageBean(HomeMessageAdapter.HEAD));
        for (int i = 0; i < 10; i++) {
            list.add(new MessageBean(HomeMessageAdapter.CONTENT));
        }
        list.add(new MessageBean(HomeMessageAdapter.HEAD));
        for (int i = 0; i < 15; i++) {
            list.add(new MessageBean(HomeMessageAdapter.CONTENT));
        }

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        home_message_rv.setLayoutManager(linearLayoutManager);
        homeMessageAdapter = new HomeMessageAdapter(list);
        home_message_rv.setAdapter(homeMessageAdapter);
        home_message_rv.setHasFixedSize(true);
        home_message_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                headHeight = message_head.getHeight();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (homeMessageAdapter.getItemViewType(mCurrentPosition + 1) == HomeMessageAdapter.HEAD) {
                    View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                    if (view != null) {
                        if (view.getTop() <= headHeight) {
                            message_head.setY(-(headHeight - view.getTop()));
                        } else {
                            message_head.setY(0);
                        }
                    }
                }

                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    message_head.setY(0);
                    updateHead();
                }
            }
        });

    }

    /**
     * 更新头部局
     */
    private void updateHead() {


    }


}

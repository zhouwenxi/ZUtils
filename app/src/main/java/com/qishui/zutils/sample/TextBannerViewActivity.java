package com.qishui.zutils.sample;

import android.os.Bundle;

import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.banner.TextBannerView;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.zutils.R;

import java.util.ArrayList;
import java.util.List;

public class TextBannerViewActivity extends BaseQiShuiActivity {

    @QBindView(R.id.tv_banner)
    private TextBannerView mTvBanner;
    @QBindView(R.id.tv_banner1)
    private TextBannerView mTvBanner1;
    @QBindView(R.id.tv_banner2)
    private TextBannerView mTvBanner2;
    @QBindView(R.id.tv_banner3)
    private TextBannerView mTvBanner3;
    private List<String> mList;

    @Override
    protected int initLayout() {
        return R.layout.activity_text_banner_view;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        initData();
        setListener();

    }


    private void initData() {
        mList = new ArrayList<>();
        mList.add("学好Java、Android、html+css+js");
        mList.add("走遍天下都不怕");
        mList.add("不是我吹，就怕你做不到，哈哈");
        mList.add("你是最棒的，奔跑吧孩子！");

        mTvBanner.setDatas(mList);
        mTvBanner.setDatas(mList);
        mTvBanner1.setDatas(mList);
        mTvBanner2.setDatas(mList);
        mTvBanner3.setDatas(mList);
    }

    private void setListener() {
        mTvBanner.setItemOnClickListener(new TextBannerView.ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                toast(String.valueOf(position) + ">>" + data);
            }
        });

        mTvBanner1.setItemOnClickListener(new TextBannerView.ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                toast(String.valueOf(position) + ">>" + data);
            }
        });

        mTvBanner2.setItemOnClickListener(new TextBannerView.ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                toast(String.valueOf(position) + ">>" + data);
            }
        });

        mTvBanner3.setItemOnClickListener(new TextBannerView.ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                toast(String.valueOf(position) + ">>" + data);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvBanner.startViewAnimator();
        mTvBanner1.startViewAnimator();
        mTvBanner2.startViewAnimator();
        mTvBanner3.startViewAnimator();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTvBanner.stopViewAnimator();
        mTvBanner1.stopViewAnimator();
        mTvBanner2.stopViewAnimator();
        mTvBanner3.stopViewAnimator();
    }

}

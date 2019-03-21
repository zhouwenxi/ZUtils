package com.qishui.zutils.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.banner.MarqueeView;
import com.qishui.commontoolslibrary.banner.TextBannerView;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.core.LogUtils;
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
    @QBindView(R.id.tv_banner4)
    private TextBannerView mTvBanner4;
    @QBindView(R.id.marquee_view)
    MarqueeView marqueeView;
    @QBindView(R.id.marquee_view2)
    MarqueeView marqueeView2;


    private List<Object> mList;


    @Override
    protected int initLayout() {
        return R.layout.activity_text_banner_view;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        initData();
        setListener();
        setMv();

    }

    private void setMv() {
        TextView tv = new TextView(this);
        tv.setTextSize(20);
        tv.setText("1111111111111666666666666666777777hahahahhhhhhhhh");
        marqueeView.addViewInQueue(tv);
        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.drawable.github);
        marqueeView.addViewInQueue(iv1);
        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.email);
        marqueeView.addViewInQueue(iv2);
        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.website);
        marqueeView.addViewInQueue(iv3);

        TextView tv2 = new TextView(this);
        tv2.setText("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        tv2.setTextSize(20);

        marqueeView.addViewInQueue(tv2);
        marqueeView.setScrollSpeed(8).setScrollDirection(MarqueeView.RIGHT_TO_LEFT).setViewMargin(15).startScroll();

        TextView tv3 = new TextView(this);
        tv3.setText("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        tv3.setTextSize(20);

        TextView tv4 = new TextView(this);
        tv4.setText("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        tv4.setTextSize(20);

        marqueeView2.addViewInQueue(tv3).addViewInQueue(tv4);
        marqueeView2.setScrollSpeed(10).setScrollDirection(MarqueeView.LEFT_TO_RIGHT).setViewMargin(15).startScroll();
    }

    private void initData() {

        mList = new ArrayList<>();
        mList.add("学好Java、Android、html+css+js~~~~~~~~~~~~");
        mList.add("走遍天下都不怕");
        mList.add("不是我吹，就怕你做不到，哈哈");
        mList.add("66666666666666666666666666666");
        mList.add("你是最棒的，奔跑吧孩子！");
    }

    private void setListener() {

        mTvBanner.setItemOnClickListener(new TextBannerView.OnItemClickListener() {
            @Override
            public void onItemClick(Object data, int position) {
                toast(String.valueOf(position) + ">>" + data);
            }

        }).setOnItemBindData(new TextBannerView.OnItemBindData() {
            @Override
            public void handle(int positon, Object obj, View view) {
                LogUtils.e("---------" + obj);
                TextView tv = (TextView) view;
                tv.setGravity(Gravity.RIGHT);
                tv.setTextColor(Color.RED);
            }
        }).setAnimDuration(TextBannerView.Right2Left)
                .setAnimDuration(3000)
                .setInterval(2000)
                .setDatas(mList).startViewAnimator();


        mTvBanner1.setItemOnClickListener(new TextBannerView.OnItemClickListener() {
            @Override
            public void onItemClick(Object data, int position) {
                toast(String.valueOf(position) + ">>" + data);
            }
        }).setAnimDirection(TextBannerView.Left2Right).setDatas(mList).startViewAnimator();

        mTvBanner2.setItemOnClickListener(new TextBannerView.OnItemClickListener() {
            @Override
            public void onItemClick(Object data, int position) {
                toast(String.valueOf(position) + ">>" + data);
            }

        }).setAnimDirection(TextBannerView.Top2Bottom)
                .setDatas(mList)
                .startViewAnimator();


        mTvBanner3.setItemOnClickListener(new TextBannerView.OnItemClickListener() {
            @Override
            public void onItemClick(Object data, int position) {
                toast(String.valueOf(position) + ">>" + data);
            }
        }).setAnimDirection(TextBannerView.Bottom2Top)
                .setDatas(mList)
                .startViewAnimator();


        mTvBanner4.setItemOnClickListener(new TextBannerView.OnItemClickListener() {
            @Override
            public void onItemClick(Object data, int position) {
                toast(String.valueOf(position) + ">>" + data);
            }

        }).setOnItemBindData(new TextBannerView.OnItemBindData() {
            @Override
            public void handle(int position, Object obj, View view) {

                int next = position + 1;
                if (next == mList.size()) {
                    next = 0;
                }
                TextView top = view.findViewById(R.id.item_tv_top);
                TextView bottom = view.findViewById(R.id.item_tv_bottom);

                top.setText(String.valueOf(obj));
                bottom.setText(String.valueOf((mList.get(next))));

            }
        }).setAnimDirection(TextBannerView.Bottom2Top)
                .setAnimDuration(3000)
                .setInterval(2000)
                .setDatas(mList, R.layout.item_mul_text_banner)
                .startViewAnimator();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvBanner.startViewAnimator();
        mTvBanner1.startViewAnimator();
        mTvBanner2.startViewAnimator();
        mTvBanner3.startViewAnimator();
        mTvBanner4.startViewAnimator();

        marqueeView.startScroll();
        marqueeView2.startScroll();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTvBanner.stopViewAnimator();
        mTvBanner1.stopViewAnimator();
        mTvBanner2.stopViewAnimator();
        mTvBanner3.stopViewAnimator();
        mTvBanner4.stopViewAnimator();


        marqueeView.stopScroll();
        marqueeView2.stopScroll();
    }

}

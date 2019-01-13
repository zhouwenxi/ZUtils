package com.qishui.commontoolslibrary.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.adapter.CommonViewPagerAdapter;
import com.qishui.commontoolslibrary.core.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 2019/1/13.
 */

public class BannerView extends RelativeLayout {

    private List<View> mList;
    private int times;
    private boolean isAutoPlay;
    private RelativeLayout rl;
    private ViewPager viewPager;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mList = new ArrayList<>();
        init(context, attrs);
    }

    /**
     * 设置展示列表
     *
     * @param mList
     * @return
     */
    public BannerView setListViews(List<View> mList) {
        this.mList = mList;
        return this;
    }

    public void showView() {
        viewPager.setAdapter(new CommonViewPagerAdapter(mList));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 获取viewpager
     *
     * @return
     */
    public ViewPager getViewPager() {
        return viewPager;
    }

    /**
     * 开启播放
     *
     * @return
     */
    public BannerView stratPlay() {
        return this;
    }

    /**
     * 停止播放
     *
     * @return
     */
    public BannerView stopPlay() {
        return this;
    }

    /**
     * 设置时间
     *
     * @param times
     * @return
     */
    public BannerView setTimes(int times) {
        this.times = times;
        return this;
    }

    /**
     * 设置是否自动播放
     *
     * @param autoPlay
     * @return
     */
    public BannerView setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
        return this;
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {

        View view = UiUtils.inflate(R.layout.view_banner, this, true);
        rl = UiUtils.findViewById(view, R.id.qishui_banner_rl);
        viewPager = UiUtils.findViewById(view, R.id.qishui_banner_vp);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        if (attributes != null) {
            isAutoPlay = attributes.getBoolean(R.styleable.BannerView_bannerLooper, true);
            times = attributes.getInteger(R.styleable.BannerView_bannerTime, 1);
            attributes.recycle();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}

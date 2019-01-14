package com.qishui.commontoolslibrary.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.click.QiShuiClick;
import com.qishui.commontoolslibrary.core.LogUtils;
import com.qishui.commontoolslibrary.core.UiUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 2019/1/13.
 */

public class BannerView extends RelativeLayout {

    private List<View> mList;
    private int delayTime = 3000;
    private boolean isAutoPlay = true;
    private RelativeLayout rl;
    private ViewPager viewPager;
    private int count = 0;
    private int currentItem;
    private int WHAT_AUTO_PLAY = 1000;


    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        mList = new ArrayList<>();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == WHAT_AUTO_PLAY) {
                if (viewPager != null && isAutoPlay) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                    handler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, delayTime);
                }
            }
            return false;
        }
    });

    /**
     * 设置展示列表
     *
     * @param mList
     * @return
     */
    public BannerView setListViews(List<View> mList) {

        this.count = mList.size();
        if (count == 0) {
            return this;
        }
        //保证数量不少于3
        if (count == 1) {
            View view = mList.get(0);
            mList.add(view);
            mList.add(view);
        } else if (count == 2) {
            View view0 = mList.get(0);
            View view1 = mList.get(1);
            mList.add(view0);
            mList.add(view1);
        }
        this.mList = mList;

        return this;
    }

    public void showView() {
        viewPager.setAdapter(new MyViewPagerAdapter(mList));
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        int targetItemPosition = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % count;
        currentItem = targetItemPosition;
        viewPager.setCurrentItem(targetItemPosition);
        setSliderTransformDuration(1000);
    }

    /**
     * 设置viewpager
     *
     * @return
     */

    public BannerView setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        return this;
    }

    /**
     * 开启播放
     *
     * @return
     */
    public BannerView stratPlay() {
        if (isAutoPlay) {
            stopPlay();
            handler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, delayTime);
        }
        return this;
    }

    /**
     * 停止播放
     *
     * @return
     */
    public BannerView stopPlay() {
        if (viewPager != null) {
            viewPager.setCurrentItem(viewPager.getCurrentItem(), false);
        }
        if (isAutoPlay) {
            handler.removeMessages(WHAT_AUTO_PLAY);
            if (viewPager != null) {
                viewPager.setCurrentItem(viewPager.getCurrentItem(), false);
            }
        }
        return this;
    }


    /**
     * 设置时间
     *
     * @param delayTime
     * @return
     */
    public BannerView setDelayTime(int delayTime) {
        this.delayTime = delayTime;
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
            delayTime = attributes.getInteger(R.styleable.BannerView_bannerTime, 3000);
            attributes.recycle();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
                stratPlay();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isAutoPlay) {
            stopPlay();
            LogUtils.e("onDetachedFromWindow stopPlay");
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isAutoPlay) {
            stratPlay();
            LogUtils.e("onAttachedToWindow stratPlay");
        }
    }

    /**
     * 监听
     */
    class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            currentItem = position;
        }
    }


    class MyViewPagerAdapter extends PagerAdapter {

        private List<View> mList;
        private int size;

        public MyViewPagerAdapter(List<View> mList) {
            this.mList = mList;
            this.size = mList.size();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, int position) {

            View tempView = null;
            if (mList.size() > 0) {
                //position % view.size()是指虚拟的position会在[0，view.size()）之间循环
                tempView = mList.get(position % size);
                //添加view之前，先移除
                if (container.equals(tempView.getParent())) {
                    container.removeView(tempView);
                }
                container.addView(tempView);
                //设置点击事件
                tempView.setOnClickListener(new QiShuiClick(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (callBack != null) {
                            callBack.click(view);
                        }
                    }
                }));

                return tempView;
            }
            throw new RuntimeException();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        }
    }

    public void setSliderTransformDuration(int duration) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), null, duration);
            mScroller.set(viewPager, scroller);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     *
     */
    public class FixedSpeedScroller extends Scroller {

        private int mDuration = 1000;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, int duration) {
            this(context, interpolator);
            mDuration = duration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }

    private CallBack callBack;

    public BannerView setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public interface CallBack {
        void click(View view);
    }


    /**
     * 辅助
     */
    public static class BannerFragment extends Fragment {

        public BannerFragment() {
        }

        @Override
        public void onStart() {
            super.onStart();

        }

        @Override
        public void onStop() {
            super.onStop();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }
    }
}

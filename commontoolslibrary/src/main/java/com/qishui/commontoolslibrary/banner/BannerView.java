package com.qishui.commontoolslibrary.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    //延迟时间
    private int delayTime = 3000;
    //是否轮播
    private boolean isAutoPlay = true;
    private ViewPager viewPager;
    //列表数量
    private int count = 0;
    //传入数量
    private int relCount = 0;
    //当前位置
    private int currentItem;
    //信息标志
    private int WHAT_AUTO_PLAY = 1000;
    //加载imageview
    private static final int TYPE_IMAGEVIEW = 0;
    //加载布局
    private static final int TYPE_LAYOUT = 1;
    //默认类型
    private static int TYPE = TYPE_IMAGEVIEW;
    //最后view
    private List<View> mList;
    //指示器
    private List<View> mListPoints;
    //选中资源
    private int selectId;
    //未选择资源指示器
    private int unSelectId;
    //
    List<Integer> tempLayout;
    List<Object> tempImageView;
    private View loadView;

    //设置位置 mListPoints
    private int location;
    /**
     * 初始化空间
     */
    private View view;
    private LinearLayout bannerLl;

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
        tempLayout = new ArrayList<>();
        tempImageView = new ArrayList<>();
        mListPoints = new ArrayList<>();
    }

    /**
     * 处理循环
     */
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
     * @return
     */
    public BannerView setListViews(List<Object> list) {
        TYPE = TYPE_IMAGEVIEW;
        tempImageView = list;
        return this;
    }

    /**
     * 转换ImageView
     *
     * @param url
     * @return
     */
    private ImageView getImageView(Object url) {

        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (imageLoader != null) {
            imageLoader.show(getContext(), url, imageView);
        }
        return imageView;
    }

    /**
     * 添加布局
     *
     * @param list
     * @return
     */
    public BannerView setListResIds(List<Integer> list) {
        TYPE = TYPE_LAYOUT;
        tempLayout = list;
        return this;
    }

    /**
     * 设置属性
     */
    public void showView() {
        bindViews();
        setData();
        setBannerPoint();

        if (relCount > 0 && viewPager != null) {
            viewPager.setAdapter(new MyViewPagerAdapter(mList));
            viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
            currentItem = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % count;
            LogUtils.e("currentItem:" + currentItem);
            viewPager.setCurrentItem(currentItem);
            setSliderTransformDuration(1000);
            if (isAutoPlay) {
                stratPlay();
            }
        }
    }

    /**
     * 设置点的位置
     *
     * @return
     */
    public BannerView setLocationRight() {
        this.location = Gravity.RIGHT;
        return this;
    }

    public BannerView setLocationLeft() {
        this.location = Gravity.LEFT;
        return this;
    }

    public BannerView setLocationCenter() {
        this.location = Gravity.CENTER;
        return this;
    }

    /**
     * 设置指示点
     */
    private void setBannerPoint() {

        if (bannerLl != null && relCount > 1 && unSelectId != 0) {
            for (int i = 0; i < relCount; i++) {
                View view = new View(getContext());
                view.setBackgroundResource(unSelectId);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UiUtils.dp2px(10), UiUtils.dp2px(10));
                params.leftMargin = UiUtils.dp2px(5);
                mListPoints.add(view);
                bannerLl.addView(view, params);
            }
            if (location != 0) {
                bannerLl.setGravity(location);
            }
        }
    }


    private void bindViews() {

        if (loadView == null) {
            view = UiUtils.inflate(R.layout.view_banner, this, true);
            viewPager = UiUtils.findViewById(view, R.id.qishui_banner_vp);
            bannerLl = UiUtils.findViewById(view, R.id.qishui_banner_ll);
        } else {
            view = loadView;
        }

    }

    /**
     * 设置加载数据
     */
    private void setData() {
        //加载布局
        if (TYPE == TYPE_LAYOUT) {
            this.count = tempLayout.size();
            this.relCount = this.count;

            if (count == 1) {
                Integer id = tempLayout.get(0);
                tempLayout.add(id);
                tempLayout.add(id);
                tempLayout.add(id);
            } else if (count == 2) {
                Integer id0 = tempLayout.get(0);
                Integer id1 = tempLayout.get(1);
                tempLayout.add(id0);
                tempLayout.add(id1);
                tempLayout.add(id0);
                tempLayout.add(id1);
            }
            for (int i : tempLayout) {
                mList.add(UiUtils.inflate(i));
            }
        }

        //加载图片
        if (TYPE == TYPE_IMAGEVIEW) {
            this.count = tempImageView.size();
            this.relCount = this.count;


            if (count == 1) {
                Object value = tempImageView.get(0);
                mList.add(getImageView(value));
                mList.add(getImageView(value));
                mList.add(getImageView(value));
            } else if (count == 2) {
                Object value1 = tempImageView.get(0);
                Object value2 = tempImageView.get(1);
                mList.add(getImageView(value1));
                mList.add(getImageView(value2));
                mList.add(getImageView(value1));
                mList.add(getImageView(value2));
            } else {
                for (int i = 0; i < tempImageView.size(); i++) {
                    mList.add(getImageView(tempImageView.get(i)));
                }
            }
        }

        this.count = mList.size();
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
     * 设置选中资源
     *
     * @param selectId
     * @return
     */
    public BannerView setSelectId(int selectId) {
        this.selectId = selectId;
        return this;
    }

    /**
     * 设置未选中资源
     *
     * @param unSelectId
     * @return
     */
    public BannerView setUnSelectId(int unSelectId) {
        this.unSelectId = unSelectId;
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
     * 设置加载view
     *
     * @return
     */

    public BannerView setLoadView(int resId) {
        this.loadView = UiUtils.inflate(resId, this, true);
        return this;
    }

    /**
     * 获取banner视图view
     *
     * @return
     */
    public View getBannerView() {
        if (loadView != null) {
            return loadView;
        }
        return view;
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {

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


    /**
     * 监听
     */
    class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            final int relPos = position % relCount;

            for (int index = 0; index < relCount && index < mListPoints.size(); index++) {
                if (index == relPos) {
                    if (selectId != 0) {
                        mListPoints.get(index).setBackgroundResource(selectId);
                    }
                } else {
                    if (unSelectId != 0) {
                        mListPoints.get(index).setBackgroundResource(unSelectId);
                    }
                    if (listener != null) {
                        listener.onPageUnSelected(index);
                    }
                }
            }

            if (listener != null) {
                listener.onPageSelected(relPos);
            }

        }
    }

    /**
     * 适配器
     */
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

            final int relPos = position % size;
            View view = mList.get(relPos);
            if (container.equals(view.getParent())) {
                container.removeView(view);
            }
            container.addView(view);
            view.setOnClickListener(new QiShuiClick(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack != null) {
                        callBack.click(v, relPos);
                    }
                }
            }));
            return view;
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

    /**
     * 点击回调处理
     */
    private BannerCallBack callBack;

    public BannerView setBannerClick(BannerCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public interface BannerCallBack {
        void click(View view, int position);
    }


    /**
     * 设置图片展示
     */
    private ImageLoader imageLoader;

    public BannerView setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public interface ImageLoader {
        void show(Context context, Object obj, ImageView iv);
    }

    /**
     * 点击回调处理
     */
    private PageChangeListener listener;

    public BannerView sePageChangeListenert(PageChangeListener listener) {
        this.listener = listener;
        return this;
    }

    public interface PageChangeListener {
        /**
         * 选中
         *
         * @param position
         */
        void onPageSelected(int position);

        /**
         * 没选择
         *
         * @param position
         */
        void onPageUnSelected(int position);
    }

}

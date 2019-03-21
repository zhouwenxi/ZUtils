package com.qishui.commontoolslibrary.banner;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.core.LogUtils;
import com.qishui.commontoolslibrary.core.UiUtils;

import java.util.List;

/**
 * 添加人: add by qishui
 * 添加时间: 2019/3/21  14:00
 * 添加注释: 文字自动轮播（跑马灯） 支持添加自定义页面
 */
public class TextBannerView extends RelativeLayout {
    //载体
    private ViewFlipper mViewFlipper;
    //切换时间间隔,默认3s
    private int mInterval;
    private int inAnimResId;
    private int outAnimResId;
    private int animDuration;

    private List<Object> mDatas;
    private OnItemClickListener mListener;
    private OnItemBindData mBindData;
    private boolean isStarted;

    public static final int Bottom2Top = 0;
    public static final int Top2Bottom = 1;
    public static final int Right2Left = 2;
    public static final int Left2Right = 3;

    public TextBannerView(Context context) {
        super(context);
        init();
    }

    public TextBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {

        //页面切换
        mInterval = 3000;
        //动画持续
        animDuration = 1500;
        //方向
        inAnimResId = R.anim.anim_right_in;
        outAnimResId = R.anim.anim_left_out;

        //new 一个ViewAnimator
        mViewFlipper = new ViewFlipper(getContext());
        mViewFlipper.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mViewFlipper);

        //设置点击事件
        mViewFlipper.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mViewFlipper.getDisplayedChild();//当前显示的子视图的索引位置
                if (mListener != null) {
                    mListener.onItemClick(mDatas.get(position), position);
                }
            }
        });


    }


    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/21  14:08
     * 添加注释: 设置文字切换时间
     */
    public TextBannerView setInterval(int mInterval) {
        this.mInterval = mInterval;
        return this;
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/21  14:09
     * 添加注释: 设置动画时间
     */
    public TextBannerView setAnimDuration(int animDuration) {
        this.animDuration = animDuration;
        return this;
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/21  14:11
     * 添加注释: 设置动画方向
     */
    public TextBannerView setAnimDirection(int direction) {

        switch (direction) {
            case Bottom2Top:
                inAnimResId = R.anim.anim_bottom_in;
                outAnimResId = R.anim.anim_top_out;
                break;
            case Top2Bottom:
                inAnimResId = R.anim.anim_top_in;
                outAnimResId = R.anim.anim_bottom_out;
                break;
            case Right2Left:
                inAnimResId = R.anim.anim_right_in;
                outAnimResId = R.anim.anim_left_out;
                break;
            case Left2Right:
                inAnimResId = R.anim.anim_left_in;
                outAnimResId = R.anim.anim_right_out;
                break;
            default:
                inAnimResId = R.anim.anim_right_in;
                outAnimResId = R.anim.anim_left_out;
                break;
        }
        return this;
    }

    /**
     * 暂停动画
     */
    public void stopViewAnimator() {
        if (isStarted) {
            removeCallbacks(mRunnable);
            isStarted = false;
        }
    }

    /**
     * 开始动画
     */
    public void startViewAnimator() {
        if (!isStarted) {
            isStarted = true;
            postDelayed(mRunnable, mInterval);
        }
    }

    /**
     * 设置延时间隔
     */
    private AnimRunnable mRunnable = new AnimRunnable();

    private class AnimRunnable implements Runnable {

        @Override
        public void run() {
            if (isStarted) {
                setInAndOutAnimation(inAnimResId, outAnimResId);
                mViewFlipper.showNext();//手动显示下一个子view。
                postDelayed(this, mInterval + animDuration);
            } else {
                stopViewAnimator();
            }

        }
    }


    /**
     * 设置进入动画和离开动画
     *
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private void setInAndOutAnimation(@AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), inAnimResId);
        inAnim.setDuration(animDuration);
        mViewFlipper.setInAnimation(inAnim);

        Animation outAnim = AnimationUtils.loadAnimation(getContext(), outAnimResID);
        outAnim.setDuration(animDuration);
        mViewFlipper.setOutAnimation(outAnim);
    }


    /**
     * 设置数据集合
     */
    public TextBannerView setDatas(List<Object> datas, int resId) {
        this.mDatas = datas;
        mViewFlipper.removeAllViews();
        if (mDatas != null && mDatas.size() == 0) {
            return this;
        }

        for (int i = 0; i < mDatas.size(); i++) {

            View inflate = UiUtils.inflate(resId);
            if (mBindData != null) {
                mBindData.handle(i, mDatas.get(i), inflate);
            }
            //添加子view,并标识子view位置
            mViewFlipper.addView(inflate, i);
        }

        return this;

    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/21  14:32
     * 添加注释: 默认布局设置
     */
    public TextBannerView setDatas(List<Object> datas) {
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() == 0) {
            return this;
        }
        mViewFlipper.removeAllViews();
        for (int i = 0; i < mDatas.size(); i++) {
            TextView tv = (TextView) UiUtils.inflate(R.layout.item_single_text_banner);
            tv.setText(String.valueOf(mDatas.get(i)));
            if (mBindData != null) {
                mBindData.handle(i, mDatas.get(i), tv);
            }

            mViewFlipper.addView(tv, i);//添加子view,并标识子view位置
        }
        return this;

    }


    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/21  13:55
     * 添加注释: 条目点击事件
     */

    public TextBannerView setItemOnClickListener(OnItemClickListener listener) {
        this.mListener = listener;
        return this;
    }

    public interface OnItemClickListener {
        void onItemClick(Object data, int position);
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/21  14:28
     * 添加注释: 处理数据
     */
    public TextBannerView setOnItemBindData(OnItemBindData bindData) {
        this.mBindData = bindData;
        return this;
    }

    public interface OnItemBindData {
        void handle(int position, Object obj, View view);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtils.e("关闭效果...");
        stopViewAnimator();
    }
}

package com.qishui.commontoolslibrary.notice;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;

/**
 * Created by zhou on 2019/4/6.
 * 自定义toast
 */

public class DIYToastUtils {

    private static DIYToastUtils mInstance;
    // 动画时间
    private final int ANIMATION_DURATION = 1000;
    private static TextView mTextView;
    private ViewGroup container;
    private View mView;
    // 默认展示时间
    private int HIDE_DELAY = 2000;
    private LinearLayout mContainer,toast_linear;
    private AlphaAnimation mFadeOutAnimation;
    private AlphaAnimation mFadeInAnimation;
    private boolean isShow = false;
    private Handler mHandler = new Handler();

    private DIYToastUtils(Activity activity) {
        container = activity.findViewById(android.R.id.content);
        mView = activity.getLayoutInflater().inflate(R.layout.toast_layout, container);
        mContainer = mView.findViewById(R.id.mbContainer);
        toast_linear= mView.findViewById(R.id.toast_linear);
        mContainer.setVisibility(View.GONE);
        mTextView =mView.findViewById(R.id.mbMessage);
    }

    public static DIYToastUtils with(Activity context) {
        if (mInstance == null) {
            mInstance = new DIYToastUtils(context);
        }
        return mInstance;
    }

    public  DIYToastUtils setText(String message) {
        mTextView.setText(message);
        return mInstance;
    }

    /**
     * 获取内容
     * @return
     */
    public  TextView getTV(){
        return mTextView;
    }

    /**
     * 获取背景布局
     * @return
     */
    public  LinearLayout getLL(){
        return toast_linear;
    }

    /**
     * 显示
     */
    public void show() {
        if (isShow) {
            // 若已经显示，则不再次显示
            return;
        }
        isShow = true;
        // 显示动画
        mFadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        // 消失动画
        mFadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        mFadeOutAnimation.setDuration(ANIMATION_DURATION);
        mFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // 消失动画后更改状态为 未显示
                        isShow = false;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // 隐藏布局，不使用remove方法为防止多次创建多个布局
                        mContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
        mContainer.setVisibility(View.VISIBLE);

        mFadeInAnimation.setDuration(ANIMATION_DURATION);

        mContainer.startAnimation(mFadeInAnimation);
        mHandler.postDelayed(mHideRunnable, HIDE_DELAY);
    }

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mContainer.startAnimation(mFadeOutAnimation);
        }
    };


    /**
     * 此方法主要为了解决用户在重启页面后单例还会持有上一个context
     */
    public static void reset() {
        mInstance = null;
    }


}

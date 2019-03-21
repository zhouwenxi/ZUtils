package com.qishui.commontoolslibrary.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.qishui.commontoolslibrary.R;

/**
 * 作者: create by qishui 修改
 * 日期：2019/3/21  16:48
 * 邮箱: qishuichixi@163.com
 * 描述：原地址 https://github.com/GITbiubiubiu/MarqueeView/
 */

public class MarqueeView extends HorizontalScrollView implements Runnable {

    //上下文
    private Context context;
    //跑马灯滚动部分
    private LinearLayout mainLayout;
    //滚动速度 低于50
    private int scrollSpeed = 5;
    //滚动方向
    private int scrollDirection = LEFT_TO_RIGHT;
    //当前x坐标
    private int currentX;
    //View间距
    private int viewMargin = 0;
    //View总宽度
    private int viewWidth;
    //屏幕宽度
    private int screenWidth;

    //左到右
    public static final int LEFT_TO_RIGHT = 1;
    //右到左
    public static final int RIGHT_TO_LEFT = 2;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    void initView() {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;

        mainLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.scroll_content, null);
        this.addView(mainLayout);
    }

    public MarqueeView addViewInQueue(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(viewMargin, 0, 0, 0);
        lp.gravity = Gravity.CENTER_VERTICAL;
        view.setLayoutParams(lp);
        mainLayout.addView(view);
        //测量view
        view.measure(0, 0);
        viewWidth = viewWidth + view.getMeasuredWidth() + viewMargin;
        return this;
    }

    //开始滚动
    public void startScroll() {
        removeCallbacks(this);
        currentX = (scrollDirection == LEFT_TO_RIGHT ? viewWidth : -screenWidth);
        post(this);
    }

    //停止滚动
    public void stopScroll() {
        removeCallbacks(this);
    }

    //设置View间距
    public MarqueeView setViewMargin(int viewMargin) {
        this.viewMargin = viewMargin;
        return this;
    }

    //设置滚动速度
    public MarqueeView setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
        return this;
    }

    //设置滚动方向 默认从左向右
    public MarqueeView setScrollDirection(int scrollDirection) {
        this.scrollDirection = scrollDirection;
        return this;
    }

    @Override
    public void run() {
        switch (scrollDirection) {
            case LEFT_TO_RIGHT:
                mainLayout.scrollTo(currentX, 0);
                currentX--;

                if (-currentX >= screenWidth) {
                    mainLayout.scrollTo(viewWidth, 0);
                    currentX = viewWidth;
                }
                break;
            case RIGHT_TO_LEFT:
                mainLayout.scrollTo(currentX, 0);
                currentX++;

                if (currentX >= viewWidth) {
                    mainLayout.scrollTo(-screenWidth, 0);
                    currentX = -screenWidth;
                }
                break;
            default:
                break;
        }

        postDelayed(this, 50 / scrollSpeed);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }


    //实现了都获取焦点
    @Override
    public boolean isFocused() {
        return true;
    }
}

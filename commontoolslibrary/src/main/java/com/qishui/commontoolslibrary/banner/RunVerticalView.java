package com.qishui.commontoolslibrary.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.qishui.commontoolslibrary.core.LogUtils;

import java.util.ArrayList;

/**
 * @author vondear
 * 上下运行view
 */
public class RunVerticalView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    /**
     * 开始信号
     */
    private static final int FLAG_START_AUTO_SCROLL = 0;
    /**
     * 停止信号
     */
    private static final int FLAG_STOP_AUTO_SCROLL = 1;

    /**
     * 设置字体大小
     */
    private float mTextSize = 12;
    /**
     * 内边距
     */
    private int mPadding = 5;
    /**
     * 字体颜色
     */
    private int textColor = Color.BLACK;

    /**
     * 上下文环境
     */
    private Context mContext;
    /**
     * 下标
     */
    private int currentId = -1;
    /**
     * 数据源
     */
    private ArrayList<String> textList;

    private int sum;
    /**
     * 延时时间
     */
    private long delayTime;

    /**
     * 处理循环
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            LogUtils.e("================"+msg.what);


            switch (msg.what) {

                case 1:

                    LogUtils.e("================"+sum+"   "+textList.size());
                    if (sum > 0) {
                        currentId++;
                        setText(textList.get(currentId % sum));
                        LogUtils.e("下标:"+currentId);
                    }
                    handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, delayTime);
                    break;
                case 0:

                    handler.removeCallbacksAndMessages(null);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

//    @SuppressLint("HandlerLeak")
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case FLAG_START_AUTO_SCROLL:
//                    if (textList.size() > 0) {
//                        currentId++;
//                        setText(textList.get(currentId % textList.size()));
//                    }
//                    handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, delayTime);
//                    break;
//                case FLAG_STOP_AUTO_SCROLL:
//                    handler.removeMessages(FLAG_START_AUTO_SCROLL);
//                    break;
//
//                default:
//                    break;
//            }
//        }
//    };


    public RunVerticalView(Context context) {
        this(context, null);
    }

    public RunVerticalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        textList = new ArrayList<String>();
    }


    /**
     * 设置字体大小
     * @param mTextSize
     * @return
     */
    public RunVerticalView setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        return this;
    }

    /**
     * 设置边距
     * @param mPadding
     * @return
     */
    public RunVerticalView setPadding(int mPadding) {
        this.mPadding = mPadding;
        return this;
    }

    /**
     * 设置字体大小
     * @param textColor
     * @return
     */
    public RunVerticalView setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    /**
     * 设置
     * @param textList
     * @return
     */
    public RunVerticalView setTextList(ArrayList<String> textList) {
        this.textList = textList;
        return this;
    }

    /**
     * 开启动画
     * @param animDuration
     * @return
     */
    public RunVerticalView setAnimTime(long animDuration) {
        setFactory(this);
        Animation in = new TranslateAnimation(0, 0, animDuration, 0);
        in.setDuration(animDuration);
        in.setInterpolator(new AccelerateInterpolator());
        Animation out = new TranslateAnimation(0, 0, 0, -animDuration);
        out.setDuration(animDuration);
        out.setInterpolator(new AccelerateInterpolator());
        setInAnimation(in);
        setOutAnimation(out);
        return this;
    }

    /**
     * 间隔时间
     *
     * @param time
     */
    public RunVerticalView setDelayTime(long time) {
        this.delayTime = time;
        return this;
    }

    /**
     * 设置数据源
     *
     * @param titles
     */
    public RunVerticalView setList(ArrayList<String> titles) {
        textList.clear();
        textList.addAll(titles);
        sum=textList.size();
        currentId = -1;
        return this;
    }

    /**
     * 开始滚动
     */
    public RunVerticalView startPlay() {
        if (handler != null) {
            handler.sendEmptyMessage(FLAG_STOP_AUTO_SCROLL);
            handler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
            LogUtils.e("startPlay================");
        }
        return this;
    }

    /**
     * 停止滚动
     */
    public RunVerticalView stopPlay() {
        if (handler != null) {
            handler.sendEmptyMessage(FLAG_STOP_AUTO_SCROLL);
        }
        return this;
    }

    @Override
    public View makeView() {
        TextView t = new TextView(mContext);
        t.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        t.setMaxLines(1);
        t.setPadding(mPadding, mPadding, mPadding, mPadding);
        t.setTextColor(textColor);
        t.setTextSize(mTextSize);
        t.setClickable(true);
        t.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && textList.size() > 0 && currentId != -1) {
                    listener.onItemClick(currentId % textList.size());
                }
            }
        });
        return t;
    }


    /**
     * 设置点击事件监听
     */
    private RunViewListener listener;

    /**
     * 设置点击事件监听
     *
     * @param listener
     */
    public RunVerticalView setOnItemClickListener(RunViewListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 轮播文本点击监听器
     */
    public interface RunViewListener {
        /**
         * 点击回调
         *
         * @param position 当前点击ID
         */
        void onItemClick(int position);
    }

}

package com.qishui.commontoolslibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.click.QiShuiClick;

/**
 * Created by zhou on 2018/12/22.
 */

public class HeadView extends RelativeLayout {

    private ImageView mHead_left_iv;
    private TextView mHead_left_tv;
    private TextView mHead_middle_tv;
    private TextView mHead_right_tv;
    private ImageView mHead_right_iv;
    private RelativeLayout head_left_rl;

    private void bindViews(View view) {

        mHead_left_iv = (ImageView) view.findViewById(R.id.head_left_iv);
        mHead_left_tv = (TextView) view.findViewById(R.id.head_left_tv);
        mHead_middle_tv = (TextView) view.findViewById(R.id.head_middle_tv);
        mHead_right_tv = (TextView) view.findViewById(R.id.head_right_tv);
        mHead_right_iv = (ImageView) view.findViewById(R.id.head_right_iv);
        head_left_rl = findViewById(R.id.head_left_rl);
    }


    public HeadView(Context context) {
        this(context, null);
    }

    public HeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_head, this, true);
        bindViews(view);
        //默认不可见
        mHead_right_tv.setVisibility(View.INVISIBLE);
        mHead_right_iv.setVisibility(View.INVISIBLE);

        getAttrs(context, attrs);

    }

    /**
     * 获取自定义属性
     *
     * @param context
     * @param attrs
     */
    private void getAttrs(Context context, AttributeSet attrs) {

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.HeadView);
        if (attributes != null) {

            String left = attributes.getString(R.styleable.HeadView_leftText);
            String middle = attributes.getString(R.styleable.HeadView_middleText);
            String right = attributes.getString(R.styleable.HeadView_rightText);
            int rightIcon = attributes.getInteger(R.styleable.HeadView_rightIcon, 0);

            if (TextUtils.isEmpty(left)) {
                mHead_left_tv.setText(left);
            }

            if (TextUtils.isEmpty(middle)) {
                mHead_middle_tv.setText(middle);
            }

            if (TextUtils.isEmpty(right)) {
                mHead_right_tv.setVisibility(View.VISIBLE);
                mHead_right_iv.setVisibility(View.INVISIBLE);
                mHead_right_tv.setText(right);
            }

            if (rightIcon != 0) {
                mHead_right_iv.setVisibility(View.VISIBLE);
                mHead_right_tv.setVisibility(View.INVISIBLE);
                mHead_right_iv.setImageResource(rightIcon);
            }

            attributes.recycle();
        }
    }

    /**
     * 获取控件
     *
     * @return
     */
    public ImageView getLeftIv() {
        return mHead_left_iv;
    }

    public TextView getLeftTv() {
        return mHead_left_tv;
    }

    public TextView getMiddleTv() {
        return mHead_middle_tv;
    }

    public TextView getRightTv() {
        mHead_right_tv.setVisibility(View.VISIBLE);
        mHead_right_iv.setVisibility(View.INVISIBLE);
        return mHead_right_tv;
    }

    public ImageView getRightIv() {
        mHead_right_tv.setVisibility(View.INVISIBLE);
        mHead_right_iv.setVisibility(View.VISIBLE);
        return mHead_right_iv;
    }

    /**
     * 获取整体布局控件
     * @return
     */
    public RelativeLayout getRl() {
        return head_left_rl;
    }

    /**
     * 设置属性
     */
    public HeadView setLeftIcon(int resId) {
        this.mHead_left_iv.setImageResource(resId);
        return this;
    }

    public HeadView setLeftText(String leftText) {
        this.mHead_left_tv.setText(leftText);
        return this;
    }

    public HeadView setLeftText(int resId) {
        this.mHead_left_tv.setText(resId);
        return this;
    }

    public HeadView setMiddleText(String middleText) {
        this.mHead_middle_tv.setText(middleText);
        return this;
    }

    public HeadView setMiddleText(int resId) {
        this.mHead_middle_tv.setText(resId);
        return this;
    }

    public HeadView setRightText(String rightText) {
        mHead_right_tv.setVisibility(View.VISIBLE);
        mHead_right_iv.setVisibility(View.INVISIBLE);
        this.mHead_right_tv.setText(rightText);
        return this;
    }

    public HeadView setRightText(int resId) {
        mHead_right_tv.setVisibility(View.VISIBLE);
        mHead_right_iv.setVisibility(View.INVISIBLE);
        this.mHead_right_tv.setText(resId);
        return this;
    }

    public HeadView setRightIcon(int resId) {
        mHead_right_tv.setVisibility(View.INVISIBLE);
        mHead_right_iv.setVisibility(View.VISIBLE);
        this.mHead_right_iv.setImageResource(resId);
        return this;
    }


    /**
     * 回调处理
     */
    public interface CallBack {
        void leftClick(View view);

        void rightClick(View view);
    }

    /**
     * 点击事件
     */
    public HeadView setOnClickListener(final CallBack callBack) {

        mHead_left_iv.setOnClickListener(new QiShuiClick(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (callBack != null) {
                    callBack.leftClick(view);
                }
            }
        }));

        mHead_left_tv.setOnClickListener(new QiShuiClick(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (callBack != null) {
                    callBack.leftClick(view);
                }
            }
        }));

        mHead_right_iv.setOnClickListener(new QiShuiClick(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (callBack != null) {
                    callBack.rightClick(view);
                }
            }
        }));

        mHead_right_tv.setOnClickListener(new QiShuiClick(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (callBack != null) {
                    callBack.rightClick(view);
                }
            }
        }));

        return this;
    }

}
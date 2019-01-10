package com.qishui.commontoolslibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;

/**
 * 自定义样式布局1
 */
public class ItemView1 extends RelativeLayout {

    private LinearLayout mItem_view_iv_ll;
    private ImageView mItem_view_iv_left;
    private ImageView mItem_view_iv_right;
    private View mItem_view_iv_view;
    private TextView mItem_view_tv_middle;


    private void bindViews(View view) {
        mItem_view_iv_ll = (LinearLayout) view.findViewById(R.id.item_view_iv_ll);
        mItem_view_iv_left = (ImageView) view.findViewById(R.id.item_view_iv_left);
        mItem_view_iv_right = (ImageView) view.findViewById(R.id.item_view_iv_right);
        mItem_view_iv_view = (View) view.findViewById(R.id.item_view_iv_view);
        mItem_view_tv_middle = view.findViewById(R.id.item_view_tv_middle);
    }


    public ItemView1(Context context) {
        this(context, null);
    }

    public ItemView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(final Context context, AttributeSet attrs) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_item1, this, true);
        bindViews(view);
        getAttrs(context, attrs);

    }


    /**
     * 获取自定义属性
     *
     * @param context
     * @param attrs
     */
    private void getAttrs(Context context, AttributeSet attrs) {

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemView1);
        if (attributes != null) {
            int bgColor = attributes.getColor(R.styleable.ItemView1_itemBgColor, 0);
            int leftResId = attributes.getResourceId(R.styleable.ItemView1_itemLeftIcon, 0);
            String middleText = attributes.getString(R.styleable.ItemView1_itemMiddleText);
            int rightResId = attributes.getResourceId(R.styleable.ItemView1_itemRightIcon, 0);

            if (bgColor != 0) {
                setItemViewBgColor(bgColor);
            }
            if (leftResId != 0) {
                setItemViewLeftIcon(leftResId);
            }

            if (!TextUtils.isEmpty(middleText)) {
                setItemViewMiddleText(middleText);
            }
            if (rightResId != 0) {
                setItemViewRightIcon(rightResId);
            }

            attributes.recycle();
        }
    }

    /**
     * 设置背景颜色
     *
     * @param color
     * @return
     */
    public ItemView1 setItemViewBgColor(int color) {
        mItem_view_iv_ll.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置左边背景
     *
     * @param resId
     * @return
     */
    public ItemView1 setItemViewLeftIcon(int resId) {
        mItem_view_iv_left.setImageResource(resId);
        return this;
    }

    /**
     * 设置右边图标
     *
     * @param resId
     * @return
     */
    public ItemView1 setItemViewRightIcon(int resId) {
        mItem_view_iv_right.setImageResource(resId);
        return this;
    }

    /**
     * 设置文字
     *
     * @param text
     * @return
     */
    public ItemView1 setItemViewMiddleText(String text) {
        mItem_view_tv_middle.setText(text);
        return this;
    }

    /**
     * 设置下划线颜色
     *
     * @param color
     * @return
     */
    public ItemView1 setItemViewLineColor(int color) {
        mItem_view_iv_view.setBackgroundColor(color);
        return this;
    }
}

package com.qishui.commontoolslibrary.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qishui.commontoolslibrary.click.QiShuiClick;
import com.qishui.commontoolslibrary.core.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * zhou
 */
public class CustomTabView extends LinearLayout {

    /**
     * 保存TabView
     */
    private List<View> mTabViews;
    private ArrayList<Integer> mHistoryPosition;


    /**
     * 点击回调
     */
    private OnTabCheckListener mOnTabCheckListener;

    public interface OnTabCheckListener {

        /**
         * 设置属性
         *
         * @param mTabViews
         */
        void onSetTabAttrs(List<View> mTabViews);

        /**
         * 选中
         *
         * @param view
         * @param position
         */
        void onTabSelected(View view, int position);

        /**
         * 未选择
         *
         * @param view
         * @param position
         */
        void onTabUnSelected(View view, int position);
    }

    public CustomTabView setOnTabCheckListener(OnTabCheckListener onTabCheckListener) {
        mOnTabCheckListener = onTabCheckListener;
        return this;
    }

    public CustomTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        mTabViews = new ArrayList<>();
        mHistoryPosition = new ArrayList<>();
    }

    public CustomTabView(Context context) {
        this(context, null);
    }


    /**
     * 添加自定义视图
     *
     * @param layoutResId
     * @return
     */
    public CustomTabView addTabView(int layoutResId) {
        View tempView = UiUtils.inflate(layoutResId);
        tempView.setTag(mTabViews.size());
        if (tempView == null) {
            return this;
        }
        tempView.setOnClickListener(new QiShuiClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                if (getLastPosition() == position) {
                    return;
                }
                mHistoryPosition.add(position);
                updateState(position);
            }
        }));
        mTabViews.add(tempView);
        //添加到容器里面
        addView(tempView);
        return this;
    }

    /**
     * 一次添加多个view
     *
     * @param layoutResId
     * @param num
     * @return
     */
    public CustomTabView addTabView(int layoutResId, int num) {
        if (num <= 0) {
            return this;
        }
        //限制最大数量
        if (num > 5) {
            num = 5;
        }

        for (int i = 0; i < num; i++) {
            addTabView(layoutResId);
        }
        return this;
    }

    /**
     * 获取加载布局
     *
     * @return
     */
    public List<View> getTabViews() {
        return mTabViews;
    }

    /**
     * 设置选中Tab
     *
     * @param curPosition
     */
    public void setCurrentItem(int curPosition) {

        if (curPosition >= mTabViews.size()) {
            return;
        }
        View view = mTabViews.get(curPosition);
        if (view == null) {
            return;
        }
        mHistoryPosition.add(curPosition);

        //初始化属性
        if (mOnTabCheckListener != null) {
            mOnTabCheckListener.onSetTabAttrs(mTabViews);
        }
        boolean flag = view.performClick();
        if (flag) {
            updateState(curPosition);
        }

    }


    /**
     * 更新状态
     *
     * @param curPosition
     */
    private void updateState(int curPosition) {

        if (mTabViews == null) {
            return;
        }
        for (int position = 0; position < mTabViews.size(); position++) {
            View view = mTabViews.get(position);
            if (mOnTabCheckListener != null && curPosition == position) {
                mOnTabCheckListener.onTabSelected(view, curPosition);
            } else if (mOnTabCheckListener != null && curPosition != position) {
                mOnTabCheckListener.onTabUnSelected(view, position);
            }
        }
    }

    /**
     * 获取最后点击位置
     *
     * @return
     */
    public int getLastPosition() {
        int position = 0;
        if (mHistoryPosition != null && mHistoryPosition.size() > 0) {
            position = mHistoryPosition.get(mHistoryPosition.size() - 1);
        }
        return position;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabViews != null) {
            mTabViews.clear();
        }
        if (mHistoryPosition != null) {
            mHistoryPosition.clear();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 调整每个Tab的大小
        int size = mTabViews.size();
        for (int i = 0; i < size; i++) {
            View view = mTabViews.get(i);
            int width = getResources().getDisplayMetrics().widthPixels / size;
            LayoutParams params = new LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            view.setLayoutParams(params);
        }
    }
}



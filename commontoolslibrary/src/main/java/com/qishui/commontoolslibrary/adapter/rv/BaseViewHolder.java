package com.qishui.commontoolslibrary.adapter.rv;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * RecyclerView缓存使用viewholder
 *
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;
    private View mConvertView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        mConvertView = itemView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }


    /**
     * 扩展方法
     */

    public BaseViewHolder setText(int id, String text) {

        if (text == null) {
            text = "";
        }

        TextView tv = getView(id);
        tv.setText(text);

        return this;
    }


    public BaseViewHolder setRating(int id, float value) {

        RatingBar rb = getView(id);
        rb.setRating(value);

        return this;
    }

    public BaseViewHolder setImageResource(int id, int resId) {
        ImageView iv = getView(id);
        iv.setImageResource(resId);
        return this;
    }


    /**
     * 设置控件是否可见
     *
     * @param id
     * @return
     */
    public BaseViewHolder setVisible(int id) {
        getView(id).setVisibility(View.VISIBLE);
        return this;
    }

    public BaseViewHolder setInVisible(int id) {
        getView(id).setVisibility(View.INVISIBLE);
        return this;
    }

    public BaseViewHolder setGone(int id) {
        getView(id).setVisibility(View.GONE);
        return this;
    }

}
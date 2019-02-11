package com.qishui.commontoolslibrary.adapter.rv;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;

/***
 * 多类型列表
 * @param <T>
 */
public abstract class CommonRvMultipleAdapter<T> extends CommonRvAdapter<T> {

    public CommonRvMultipleAdapter(List<T> list, int layoutID) {
        super(list, layoutID);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }
}

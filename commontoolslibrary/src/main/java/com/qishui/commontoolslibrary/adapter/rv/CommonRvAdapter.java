package com.qishui.commontoolslibrary.adapter.rv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zhou on 2018/12/22.
 */

public abstract class CommonRvAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<T> mList;
    private int mLayoutID;

    public CommonRvAdapter(List<T> list, int layoutID) {
        this.mList = list;
        this.mLayoutID = layoutID;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutID, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        setData(holder, mList.get(position), position);
    }


    /**
     * 设置数据
     *
     * @param holder
     * @param t
     * @param position
     */
    public abstract void setData(BaseViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

}


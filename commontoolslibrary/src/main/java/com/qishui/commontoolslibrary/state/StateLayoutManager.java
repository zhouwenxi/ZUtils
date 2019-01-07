package com.qishui.commontoolslibrary.state;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;

/**
 * Created by zhou on 2019/1/7.
 */

public class StateLayoutManager {

    private View targetView;
    private View loadingView;
    private View loadNetWorkView;
    private View loadDataEmptyView;
    private View loadDataErrorView;

    // targetView  的父布局
    private ViewGroup parentView;
    // targetView 在父布局中的位置
    private int currentViewIndex;
    // targetView LayoutParams
    private ViewGroup.LayoutParams params;


    public static StateLayoutManager with(View targetView) {
        return new StateLayoutManager(targetView);
    }

    private StateLayoutManager(View targetView) {
        this.targetView = targetView;

        params = targetView.getLayoutParams();

        if (targetView instanceof ViewGroup) {
            parentView = (ViewGroup) targetView.getParent();
        }else {
            parentView=targetView.getRootView().findViewById(android.R.id.content);
        }

        if (parentView == null) {
            return;
        }
        int count = parentView.getChildCount();
        for (int i = 0; i < count; i++) {
            if (targetView == parentView.getChildAt(i)) {
                currentViewIndex = i;
                break;
            }
        }
    }

    /**
     * 切换view状态,移除之前的状态
     *
     * @param view 目标View
     */
    private void showView(View view) {

        if (parentView == null) {
            return;
        }
        // 如果当前状态和要切换的状态相同，则不做处理，反之切换
        if (parentView.getChildAt(currentViewIndex) != view) {
            // 先把view从父布局移除
            ViewGroup viewParent = (ViewGroup) view.getParent();
            if (viewParent != null) {
                viewParent.removeView(view);
            }
            parentView.removeViewAt(currentViewIndex);
            parentView.addView(view, currentViewIndex, params);
        }
    }

    public StateLayoutManager setLoadingView(View loadingView) {
        this.loadingView = loadingView;
        return this;
    }

    public StateLayoutManager setLoadNetWorkView(View loadNetWorkView) {
        this.loadNetWorkView = loadNetWorkView;
        return this;
    }

    public StateLayoutManager setLoadDataEmptyView(View loadDataEmptyView) {
        this.loadDataEmptyView = loadDataEmptyView;
        return this;
    }

    public StateLayoutManager setLoadDataErrorView(View loadDataErrorView) {
        this.loadDataErrorView = loadDataErrorView;
        return this;
    }

    /**
     * 加载布局
     *
     * @param resId
     * @return
     */
    private View loadView(int resId) {
        return LayoutInflater.from(BaseQiShuiApplication.getContext()).inflate(resId, null);
    }

    /**
     * 加载中视图
     */
    public void showLoading() {

        if (this.loadingView != null) {
            showView(loadingView);
            return;
        }
        View view = loadView(R.layout.state_loading);
        showView(view);
    }

    /**
     * 加载网络错误
     */
    public void showNetworkError() {

        if (this.loadNetWorkView != null) {
            showView(loadNetWorkView);
            return;
        }
        View view = loadView(R.layout.state_networkerror);
        showView(view);
    }

    /**
     * 加载数据错误
     */
    public void showDataError() {

        if (this.loadDataErrorView != null) {
            showView(loadDataErrorView);
            return;
        }
        View view = loadView(R.layout.state_error);
        showView(view);
    }

    /**
     * 加载空数据页面
     */
    public void showDataEmpty() {

        if (this.loadDataEmptyView != null) {
            showView(loadDataEmptyView);
            return;
        }
        View view = loadView(R.layout.state_emptydata);
        showView(view);
    }

    /**
     * 还原源数据视图
     */
    public void showContent() {
        showView(targetView);
    }
}

package com.qishui.commontoolslibrary.state;

import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;

/**
 * Created by zhou on 2019/1/7.
 */

public class StateLayoutManager {

    //原来视图层
    private View targetView;
    //自定义 加载中
    private int loadingViewID = 0;
    //自定义 网络错误
    private int loadNetWorkErrorViewID = 0;
    //自定义 数据为空
    private int loadDataEmptyViewID = 0;
    //自定义 数据加载失败
    private int loadDataErrorViewID = 0;
    //缓存 view
    private SparseArray<View> sparseArray = new SparseArray<>();
    //缓存 viewID
    private SparseArray<View> sparseArrayID = new SparseArray<>();
    //存放 加载中
    private static final int INDEX_LOADINGVIEW = 0;
    //存放 网络错误
    private static final int INDEX_LOADNETWORK_ERROR_VIEW = 1;
    //存放 数据为空
    private static final int INDEX_LOADDATAEMPTYVIEW = 2;
    //存放 数据错误
    private static final int INDEX_LOADDATAERRORVIEW = 3;
    //存放 自定义加载中
    private static final int INDEX_CUSTOM_LOADINGVIEW = 4;
    //存放 自定义网络错误
    private static final int INDEX_CUSTOM_LOADNETWORK_ERROR_VIEW = 5;
    //存放 自定义数据为空
    private static final int INDEX_CUSTOM_LOADDATAEMPTYVIEW = 6;
    //存放 自定义数据错误
    private static final int INDEX_CUSTOM_LOADDATAERRORVIEW = 7;
    //自定义加载中文字
    private String loadingTip;
    //自定义网络错误文字
    private String netErrorTip;
    //自定义数据为空文字
    private String dataEmptryTip;
    //自定义数据出错文字
    private String dataErrorTip;
    //自定义网络错误图片
    private int netErrorResID;
    //自定义数据为空图片
    private int dataEmptryResID;
    //自定义数据出错图片
    private int dataErrorResID;

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
        } else {
            parentView = targetView.getRootView().findViewById(android.R.id.content);
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

    /**
     * 緩存view
     *
     * @param index
     * @param resID
     */
    private void showView(int index, int resID) {

        //加载缓存view
        View view = sparseArray.get(index);
        if (view == null) {
            view = loadView(resID);
            sparseArray.put(index, view);
        }
        //加载中
        if (index == INDEX_LOADINGVIEW) {

            if (!TextUtils.isEmpty(loadingTip)) {
                TextView tv = getView(view, R.id.state_data_loading_tv);
                tv.setText(loadingTip);
            }
        }
        //网络错误
        if (index == INDEX_LOADNETWORK_ERROR_VIEW) {

            if (netErrorResID != 0) {
                ImageView iv = getView(view, R.id.state_data_network_error_iv);
                iv.setImageResource(netErrorResID);
            }
            if (!TextUtils.isEmpty(netErrorTip)) {
                TextView tv = getView(view, R.id.state_data_network_error_tv);
                tv.setText(netErrorTip);
            }

        }
        //数据为空
        if (index == INDEX_LOADDATAEMPTYVIEW) {

            if (dataEmptryResID != 0) {
                ImageView iv = getView(view, R.id.state_data_empty_iv);
                iv.setImageResource(dataEmptryResID);
            }
            if (!TextUtils.isEmpty(dataEmptryTip)) {
                TextView tv = getView(view, R.id.state_data_empty_tv);
                tv.setText(dataEmptryTip);
            }

        }
        //数据错误
        if (index == INDEX_LOADDATAERRORVIEW) {

            if (dataErrorResID != 0) {
                ImageView iv = getView(view, R.id.state_data_error_iv);
                iv.setImageResource(dataErrorResID);
            }
            if (!TextUtils.isEmpty(dataErrorTip)) {
                TextView tv = getView(view, R.id.state_data_error_tv);
                tv.setText(dataErrorTip);
            }
        }
        // 显示view
        showView(view);

        //处理view 当存于不是加载中的时候
        if (callBack != null && index != INDEX_LOADINGVIEW && index != INDEX_CUSTOM_LOADINGVIEW) {
            callBack.handle(view);
        }
    }

    /**
     * 自定义加载中文字
     *
     * @param loadingTip
     * @return
     */
    public StateLayoutManager setLoadingTip(String loadingTip) {
        this.loadingTip = loadingTip;
        return this;
    }

    /**
     * 自定义网络错误文字
     *
     * @param netErrorTip
     * @return
     */
    public StateLayoutManager setNetErrorTip(String netErrorTip) {
        this.netErrorTip = netErrorTip;
        return this;
    }

    /**
     * 自定义数据为空文字
     *
     * @param dataEmptryTip
     * @return
     */
    public StateLayoutManager setDataEmptryTip(String dataEmptryTip) {
        this.dataEmptryTip = dataEmptryTip;
        return this;
    }

    /**
     * 自定义数据出错文字
     *
     * @param dataErrorTip
     * @return
     */
    public StateLayoutManager setDataErrorTip(String dataErrorTip) {
        this.dataErrorTip = dataErrorTip;
        return this;
    }

    /**
     * 自定义网络错误图片
     *
     * @param netErrorResID
     * @return
     */
    public StateLayoutManager setNetErrorResID(int netErrorResID) {
        this.netErrorResID = netErrorResID;
        return this;
    }

    /**
     * 自定义数据为空图片
     *
     * @param dataEmptryResID
     * @return
     */
    public StateLayoutManager setDataEmptryResID(int dataEmptryResID) {
        this.dataEmptryResID = dataEmptryResID;
        return this;
    }

    /**
     * 自定义数据出错图片
     *
     * @param dataErrorResID
     * @return
     */
    public StateLayoutManager setDataErrorResID(int dataErrorResID) {
        this.dataErrorResID = dataErrorResID;
        return this;
    }

    /**
     * 设置加载中布局ID
     *
     * @param loadingViewID
     * @return
     */
    public StateLayoutManager setLoadingViewID(int loadingViewID) {
        this.loadingViewID = loadingViewID;
        return this;
    }

    /**
     * 设置网络错误
     *
     * @param loadNetWorkErrorViewID
     * @return
     */
    public StateLayoutManager setLoadNetWorkErrorViewID(int loadNetWorkErrorViewID) {
        this.loadNetWorkErrorViewID = loadNetWorkErrorViewID;
        return this;
    }

    /**
     * 设置加载数据为空
     *
     * @param loadDataEmptyViewID
     * @return
     */
    public StateLayoutManager setLoadDataEmptyViewID(int loadDataEmptyViewID) {
        this.loadDataEmptyViewID = loadDataEmptyViewID;
        return this;
    }

    /**
     * 设置数据加载错误
     *
     * @param loadDataErrorViewID
     * @return
     */
    public StateLayoutManager setLoadDataErrorViewID(int loadDataErrorViewID) {
        this.loadDataErrorViewID = loadDataErrorViewID;
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
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(View view, int viewId) {

        View tempView = sparseArrayID.get(viewId);
        if (tempView == null) {
            tempView = view.findViewById(viewId);
            sparseArrayID.put(viewId, tempView);
        }
        return (T) tempView;
    }


    /**
     * 加载中视图
     */
    public void showLoading() {

        if (this.loadingViewID != 0) {
            showView(INDEX_CUSTOM_LOADINGVIEW, loadingViewID);
            return;
        }
        showView(INDEX_LOADINGVIEW, R.layout.state_data_loading);
    }

    /**
     * 加载网络错误
     */
    public void showNetworkError() {

        if (this.loadNetWorkErrorViewID != 0) {
            showView(INDEX_CUSTOM_LOADNETWORK_ERROR_VIEW, loadNetWorkErrorViewID);
            return;
        }
        showView(INDEX_LOADNETWORK_ERROR_VIEW, R.layout.state_data_network_error);
    }

    /**
     * 加载数据错误
     */
    public void showDataError() {

        if (this.loadDataErrorViewID != 0) {
            showView(INDEX_CUSTOM_LOADDATAERRORVIEW, loadDataErrorViewID);
            return;
        }
        showView(INDEX_LOADDATAERRORVIEW, R.layout.state_data_error);
    }


    /**
     * 加载空数据页面
     */
    public void showDataEmpty() {

        if (this.loadDataEmptyViewID != 0) {
            showView(INDEX_CUSTOM_LOADDATAEMPTYVIEW, loadDataEmptyViewID);
            return;
        }
        showView(INDEX_LOADDATAEMPTYVIEW, R.layout.state_data_empty);
    }

    /**
     * 还原源数据视图
     */
    public void showContent() {
        showView(targetView);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private CallBack callBack;

    public StateLayoutManager setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    /**
     * 处理事件回调
     */
    public interface CallBack {

        void handle(View view);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

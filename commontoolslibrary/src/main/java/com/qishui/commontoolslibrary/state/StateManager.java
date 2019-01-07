package com.qishui.commontoolslibrary.state;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;

/**
 * author： qishui
 * date: 2019/1/7  15:17
 * email: qihsuichixi@163.com
 * qq: 798150439
 * blog: http://zhouwenxi.top
 * desc: 状态管理器
 */
public class StateManager implements StateInterface {

    //传入上下文
    private Context context;
    //替换控件位置
    private View targetView;
    // 加载中 提示
    private String loadingMessage;
    // 页面错误提示
    private String errorMessage;
    // 页面为空提示
    private String emptyMessage;

    // 监听相关 提示
    private String networkErrorRetryText;
    private CallBack onNetworkErrorRetryClickListener;
    private String errorRetryText;
    private CallBack onErrorRetryClickListener;
    private String emptyTodoText;
    private CallBack onEmptyTodoClickListener;

    //加载布局
    private LayoutInflater inflater;
    // targetView  的父布局
    private ViewGroup parentView;
    // targetView 在父布局中的位置
    private int currentViewIndex;
    // targetView LayoutParams
    private ViewGroup.LayoutParams params;

    //加载中布局
    private View loadingView;
    //网络错误布局
    private View networkErrorView;
    //数据加载错误布局
    private View errorView;
    //数据加载空布局
    private View emptyView;

    private StateManager(Builder builder) {

        initParams(builder);
        init();
    }

    private void initParams(Builder builder) {
        context = builder.context;
        targetView = builder.loadingTargetView;
        loadingMessage = builder.loadingMessage;
        errorMessage = builder.errorMessage;
        emptyMessage = builder.emptyMessage;
        networkErrorRetryText = builder.networkErrorRetryText;
        onNetworkErrorRetryClickListener = builder.onNetworkErrorRetryClickListener;
        errorRetryText = builder.errorRetryText;
        onErrorRetryClickListener = builder.onErrorRetryClickListener;
        emptyTodoText = builder.emptyTodoText;
        onEmptyTodoClickListener = builder.onEmptyTodoClickListener;

        if (builder.customLoadingView != null) {
            loadingView = builder.customLoadingView;
        }
        if (builder.customNetworkErrorView != null) {
            networkErrorView = builder.customNetworkErrorView;
        }
        if (builder.customErrorView != null) {
            errorView = builder.customErrorView;
        }
        if (builder.customEmptyView != null) {
            emptyView = builder.customEmptyView;
        }
    }

    /**
     * 替换布局，初始化一次即可
     */
    private void init() {
        inflater = LayoutInflater.from(context);
        params = targetView.getLayoutParams();


        if (targetView.getParent() != null) {
            parentView = (ViewGroup) targetView.getParent();
        } else {
            parentView = targetView.getRootView().findViewById(android.R.id.content);
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

    @SuppressLint("InflateParams")
    @Override
    public void showLoading() {

        if (loadingView != null) {
            showView(loadingView);
            return;
        }
        loadingView = inflater.inflate(R.layout.state_loading, null);
        ProgressBar pbLoading = loadingView.findViewById(R.id.pb_loading);
        TextView tvLoadingMessage = loadingView.findViewById(R.id.tv_loadingMessage);

        tvLoadingMessage.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.VISIBLE);

        showView(loadingView);
    }

    @SuppressLint("InflateParams")
    @Override
    public void showNetworkError() {
        if (networkErrorView != null) {
            showView(networkErrorView);
            return;
        }
        networkErrorView = inflater.inflate(R.layout.state_networkerror, null);
        TextView tvNetworkErrorMessage = networkErrorView.findViewById(R.id.tv_errorMessage);
        Button btnRetry = networkErrorView.findViewById(R.id.btn_retry);

        tvNetworkErrorMessage.setText("网络错误!");

        if (onNetworkErrorRetryClickListener != null) {
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNetworkErrorRetryClickListener.onClick();
                }
            });
        }

        showView(networkErrorView);
    }

    @SuppressLint("InflateParams")
    @Override
    public void showError() {
        if (errorView != null) {
            showView(errorView);
            return;
        }
        errorView = inflater.inflate(R.layout.state_networkerror, null);
        ImageView ivError = errorView.findViewById(R.id.iv_error);
        TextView tvErrorMessage = errorView.findViewById(R.id.tv_errorMessage);
        Button btnRetry = errorView.findViewById(R.id.btn_retry);

        if (onErrorRetryClickListener != null) {
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onErrorRetryClickListener.onClick();
                }
            });
        }

        showView(errorView);
    }

    @SuppressLint("InflateParams")
    @Override
    public void showEmpty() {
        if (emptyView != null) {
            showView(emptyView);
            return;
        }
        emptyView = inflater.inflate(R.layout.state_error, null);
        ImageView ivEmpty = emptyView.findViewById(R.id.iv_error);
        TextView tvEmptyMessage = emptyView.findViewById(R.id.tv_errorMessage);
        Button btnTodo = emptyView.findViewById(R.id.btn_retry);


        if (onEmptyTodoClickListener != null) {
            btnTodo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEmptyTodoClickListener.onClick();
                }
            });
        }

        showView(emptyView);
    }

    @Override
    public void showContent() {
        showView(targetView);
    }

    private static StateManager stateManager;

    public static class Builder {

        private Context context;
        private View loadingTargetView;
        // qlk_loading
        private String loadingMessage;
        private View customLoadingView;
        // network qlk_loading_result
        private View customNetworkErrorView;

        // normal qlk_loading_result
        private String errorMessage;
        private View customErrorView;
        // empty
        private String emptyMessage;
        private View customEmptyView;

        // listener
        private String networkErrorRetryText;
        private CallBack onNetworkErrorRetryClickListener;
        private String errorRetryText;
        private CallBack onErrorRetryClickListener;
        private String emptyTodoText;
        private CallBack onEmptyTodoClickListener;

        public Builder(@NonNull Context context, @NonNull View loadingTargetView) {
            this.context = context;
            this.loadingTargetView = loadingTargetView;
        }


        public Builder setLoadingMessage(String loadingMessage) {
            this.loadingMessage = loadingMessage;
            return this;
        }

        /**
         * 自定义loadingView
         *
         * @param loadingView view for loading
         * @return this Builder
         */
        public Builder setLoadingView(View loadingView) {
            this.customLoadingView = loadingView;
            return this;
        }

        /**
         * 自定义networkErrorView
         *
         * @param networkErrorView view for networkError
         * @return this Builder
         */
        public Builder setNetworkErrorView(View networkErrorView) {
            this.customNetworkErrorView = networkErrorView;
            return this;
        }


        public Builder setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        /**
         * 自定义errorView
         *
         * @param errorView view for error
         * @return this Builder
         */
        public Builder setErrorView(View errorView) {
            this.customErrorView = errorView;
            return this;
        }

        public Builder setEmptyMessage(String emptyMessage) {
            this.emptyMessage = emptyMessage;
            return this;
        }

        /**
         * 自定义emptyView
         *
         * @param emptyView view for empty
         * @return this Builder
         */
        public Builder setEmptyView(View emptyView) {
            this.customEmptyView = emptyView;
            return this;
        }

        public Builder setOnNetworkErrorRetryClickListener(CallBack listener) {
            this.onNetworkErrorRetryClickListener = listener;
            return this;
        }

        public Builder setOnNetworkErrorRetryClickListener(String networkErrorRetryText, CallBack listener) {
            this.networkErrorRetryText = networkErrorRetryText;
            this.onNetworkErrorRetryClickListener = listener;
            return this;
        }

        public Builder setOnErrorRetryClickListener(CallBack listener) {
            this.onErrorRetryClickListener = listener;
            return this;
        }

        public Builder setOnErrorRetryClickListener(String errorRetryText, CallBack listener) {
            this.errorRetryText = errorRetryText;
            this.onErrorRetryClickListener = listener;
            return this;
        }

        public Builder setOnEmptyTodoClickListener(CallBack listener) {
            this.onEmptyTodoClickListener = listener;
            return this;
        }

        public Builder setOnEmptyTodoClickListener(String emptyTodoText, CallBack listener) {
            this.emptyTodoText = emptyTodoText;
            this.onEmptyTodoClickListener = listener;
            return this;
        }


        public StateManager with() {
            if (stateManager == null) {
                stateManager = new StateManager(this);
            }
            return stateManager;
        }
    }


    /**
     * 点击回调 处理加载错误时
     */
    public interface CallBack {
        void onClick();
    }

}




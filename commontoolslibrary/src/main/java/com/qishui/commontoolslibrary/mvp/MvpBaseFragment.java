package com.qishui.commontoolslibrary.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.state.StateLayoutManager;

/**
 * MVP frament
 * @param <V>
 * @param <M>
 * @param <P>
 */
public abstract class MvpBaseFragment<V extends MvpBaseView, M extends MvpBaseModel, P extends MvpBasePresenter<V, M>> extends Fragment implements MvpBaseView,MvpBaseModel {

    private P presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;
        if (loadStateLayout()) {
            view = addStateLayout(inflater);
        } else {
            view = inflater.inflate(initLayout(), null);
        }


        if (presenter == null) {
            //绑定view、綁定model
            presenter = createPresenter();
        }
        presenter.attachView((V) this);
        initEvent(savedInstanceState);

        if(view!=null){
            return view;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 加载切换状态布局,默认不加载
     */
    public Boolean loadStateLayout() {
        return false;
    }

    /**
     * 正在加载
     *
     * @return
     */
    public int loadLoadingView() {
        return R.layout.state_loading;
    }

    /**
     * 加载数据错误
     *
     * @return
     */
    public int loadErrorView() {
        return R.layout.state_error;
    }

    /**
     * 设置加载空数据
     *
     * @return
     */
    public int loadEmptyView() {
        return R.layout.state_emptydata;
    }

    /**
     * 加载网络错误
     *
     * @return
     */
    public int loadNetWorkErrorView() {
        return R.layout.state_networkerror;
    }

    //状态管理器
    private StateLayoutManager statusLayoutManager;

    /**
     * 获取状态管理器
     *
     * @return
     */
    public StateLayoutManager getStatusLayoutManager() {
        return statusLayoutManager;
    }

    /**
     * 初始化布局管理器
     *
     * @param inflater
     */
    private View addStateLayout(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.fragment_base, null);
        LinearLayout ll_main = view.findViewById(R.id.fragment_root);

        StateLayoutManager.Builder builder = StateLayoutManager.with(getActivity());
        statusLayoutManager = builder
                .contentView(initLayout())
                .emptyDataView(loadEmptyView())
                .errorView(loadErrorView())
                .loadingView(loadLoadingView())
                .netWorkErrorView(loadNetWorkErrorView())
                .build()
                .showContent();

        ll_main.addView(statusLayoutManager.getRootLayout());

        return view;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 加载布局
     *
     * @return
     */
    protected abstract int initLayout();

    /**
     * 处理事件
     *
     * @param savedInstanceState
     */
    protected abstract void initEvent(Bundle savedInstanceState);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除绑定
        if (presenter != null) {
            presenter.detachModelandView();
        }
    }


    /**
     * 创建Presenter
     *
     * @return 子类自己需要的Presenter
     */
    protected abstract P createPresenter();

    /**
     * 获取Presenter
     *
     * @return 返回子类创建的Presenter
     */
    public P getPresenter() {
        return presenter;
    }
}
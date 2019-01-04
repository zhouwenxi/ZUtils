package com.qishui.commontoolslibrary.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        View view = inflater.inflate(initLayout(), null);
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
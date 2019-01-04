package com.qishui.commontoolslibrary.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * mvp Presenter 基类
 *
 * Presenter对这个View 和 model 持有弱引用
 * @param <V>
 */
public class MvpBasePresenter<V extends MvpBaseView, M extends MvpBaseModel> {

    //弱引用
    private Reference<V> mView;
    private Reference<M> mvpModel;

    public MvpBasePresenter(M m) {
        this.mvpModel =new WeakReference<M>(m);
    }

    public void attachView(V v ){
        this.mView =new WeakReference<V>(v);
    }

    /**
     * 解绑model and view
     */
    public void detachModelandView() {

        if (this.mView != null) {
            this.mView.clear();
            this.mView = null;
        }

        if (this.mvpModel != null) {
            this.mvpModel.clear();
            this.mvpModel = null;
        }

    }

    /**
     * 是否绑定了
     *
     * @return
     */
    public boolean isAttachView() {
        return this.mView != null && this.mView.get() != null;
    }

    /**
     * 是否綁定了model
     *
     * @return
     */
    public boolean isAttachModel() {
        return this.mvpModel != null && this.mvpModel.get() != null;
    }

    /**
     * 获取view
     *
     * @return
     */
    public V getView() {
        return this.mView.get();
    }

    /**
     * 获取model
     */
    public M getModel() {
        return this.mvpModel.get();
    }

}

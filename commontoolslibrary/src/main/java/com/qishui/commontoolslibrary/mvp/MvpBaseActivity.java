package com.qishui.commontoolslibrary.mvp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MVP activity 基类
 *
 *
 * @param <V>
 * @param <P>
 */
public abstract class MvpBaseActivity<V extends MvpBaseView, M extends MvpBaseModel, P extends MvpBasePresenter<V, M>> extends AppCompatActivity implements MvpBaseView {

    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置屏幕方向 垂直
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(initLayout());
        if (presenter == null) {
            //绑定view、綁定model
            presenter = createPresenter();
        }
        presenter.attachView((V) this);
        initEvent(savedInstanceState);
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
    protected void onDestroy() {
        super.onDestroy();
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


    /**
     * 获取文本
     * @param tv
     * @return
     */
    public String getText(TextView tv){
        return tv==null?"":tv.getText().toString().trim();
    }

    public String getText(EditText editText){
        return editText==null?"":editText.getText().toString().trim();
    }

    /**
     * 提示框
     * @param message
     */
    public void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转下一页
     * @param clazz
     */
    public void startActivity(Class clazz){
        startActivity(new Intent(this,clazz));
    }

    public void startActivityFinish(Class clazz){
        startActivity(new Intent(this,clazz));
        finish();
    }


}
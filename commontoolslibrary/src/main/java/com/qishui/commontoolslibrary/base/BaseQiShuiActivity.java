package com.qishui.commontoolslibrary.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.annotation.AnnotationUtils;
import com.qishui.commontoolslibrary.state.StateLayoutManager;

/**
 * Created by zhou on 2018/12/22.
 */

public abstract class BaseQiShuiActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置屏幕方向 垂直
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (loadStateLayout()) {
            addStateLayout();
        } else {
            setContentView(initLayout());
        }
        AnnotationUtils.initBinds(this);
        initEvent(savedInstanceState);
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
     */
    private void addStateLayout() {

        setContentView(R.layout.activity_base);
        LinearLayout ll_main = findViewById(R.id.activity_root);

        StateLayoutManager.Builder builder = StateLayoutManager.with(this);
        statusLayoutManager = builder
                .contentView(initLayout())
                .emptyDataView(loadEmptyView())
                .errorView(loadErrorView())
                .loadingView(loadLoadingView())
                .netWorkErrorView(loadNetWorkErrorView())
                .build()
                .showContent();

        ll_main.addView(statusLayoutManager.getRootLayout());
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


    /**
     * 获取文本
     *
     * @param tv
     * @return
     */
    public String getText(TextView tv) {
        return tv == null ? "" : tv.getText().toString().trim();
    }

    public String getText(EditText editText) {
        return editText == null ? "" : editText.getText().toString().trim();
    }

    /**
     * 提示框
     *
     * @param message
     */
    public void toast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转下一页
     *
     * @param clazz
     */
    public void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void startActivityFinish(Class clazz) {
        startActivity(new Intent(this, clazz));
        finish();
    }

}

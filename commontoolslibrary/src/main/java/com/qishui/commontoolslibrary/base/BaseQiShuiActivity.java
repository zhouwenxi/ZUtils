package com.qishui.commontoolslibrary.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qishui.commontoolslibrary.annotation.AnnotationUtils;

/**
 * Created by zhou on 2018/12/22.
 */

public abstract class BaseQiShuiActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置屏幕方向 垂直
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(initLayout());
        //绑定注解
        AnnotationUtils.initBinds(this);
        //设置状态属性
        setStateLayoutAttrs();

        initEvent(savedInstanceState);
    }

    /**
     * 重写
     * @return
     */
    public void setStateLayoutAttrs() {

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

    /**
     * 跳转
     * @param className
     */
    public void startActivityFinish(String className) {
        Intent intent=new Intent();
        intent.setClassName(this, className);
        startActivity(intent);
        finish();
    }

}

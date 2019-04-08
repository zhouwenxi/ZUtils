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
import com.qishui.commontoolslibrary.bean.EventBean;
import com.qishui.commontoolslibrary.constant.Keys;
import com.qishui.commontoolslibrary.core.StatusBarUtils;
import com.qishui.commontoolslibrary.notice.DIYToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 添加人: add by qishui
 * 添加时间: 2019/3/14  17:15
 * 添加注释:
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
        //注册
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }

        initEvent(savedInstanceState);
        //设置状态属性
        setStateLayoutAttrs();

    }


    public void setNavigationBarColor(int color){
        //设置底部导航栏颜色
        StatusBarUtils.setNavigationBarColor(this, color);
    }

    /**
     * 重写
     *
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
        DIYToastUtils.with(this).setText(message).show();
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
     *
     * @param className
     */
    public void startActivityFinish(String className) {
        Intent intent = new Intent();
        intent.setClassName(this, className);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().removeAllStickyEvents();
        //反注册
        EventBus.getDefault().unregister(this);

        DIYToastUtils.reset();
    }


    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/8  17:24
     * 添加注释: 处理消息 UI线程
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void uiEvent(EventBean eventBean) {

    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/8  17:26
     * 添加注释: 处理消息 子线程
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void threadEvent(EventBean eventBean) {

    }


    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/8  17:23
     * 添加注释: 发送粘性事件
     */
    public void postSticky(EventBean eventBean) {
        EventBus.getDefault().postSticky(eventBean);
    }


}

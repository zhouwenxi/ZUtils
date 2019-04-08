package com.qishui.commontoolslibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.annotation.AnnotationUtils;
import com.qishui.commontoolslibrary.bean.EventBean;
import com.qishui.commontoolslibrary.notice.DIYToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 添加人: add by qishui
 * 添加时间: 2019/3/14  17:17
 * 添加注释:
 */
public abstract class BaseQiShuiFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        //反注册
        EventBus.getDefault().unregister(this);

        DIYToastUtils.reset();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base, null);
        FrameLayout frameLayout = view.findViewById(R.id.fragment_root);
        View contentView = inflater.inflate(initLayout(), null);
        frameLayout.addView(contentView);

        return frameLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnnotationUtils.initBinds(this, view);

        //设置状态属性
        setStateLayoutAttrs(view);
        initEvent(view);
    }

    /**
     * 提示框
     *
     * @param message
     */
    public void toast(String message) {
        DIYToastUtils.with(getActivity()).setText(message).show();
    }


    /**
     * 设置布局管理器属性
     */
    public void setStateLayoutAttrs(View view) {

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
     * @param view
     */
    protected abstract void initEvent(View view);


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
     * 跳转下一页
     *
     * @param clazz
     */
    public void startActivity(Class clazz) {
        if (getActivity() != null) {
            startActivity(new Intent(this.getActivity(), clazz));
        }
    }

    public void startActivityFinish(Class clazz) {
        if (getActivity() != null) {
            startActivity(new Intent(this.getActivity(), clazz));
            getActivity().finish();
        }
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

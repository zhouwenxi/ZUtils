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
import android.widget.TextView;
import android.widget.Toast;

import com.qishui.commontoolslibrary.annotation.AnnotationUtils;

/**
 * Created by zhou on 2018/12/22.
 */

public abstract class BaseQiShuiFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;

        view = inflater.inflate(initLayout(), null);

        AnnotationUtils.initBinds(this, view);
        initEvent(savedInstanceState);

        if (view != null) {
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
        Toast.makeText(BaseQiShuiApplication.getContext(), message, Toast.LENGTH_SHORT).show();
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
}

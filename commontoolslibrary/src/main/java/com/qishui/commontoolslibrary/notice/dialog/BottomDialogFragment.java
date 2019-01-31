package com.qishui.commontoolslibrary.notice.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qishui.commontoolslibrary.R;

/**
 * 分享、支付、拍照片等底部弹出框
 */
public class BottomDialogFragment extends BottomSheetDialogFragment {

    public static final String KEY_RESID = "resID";

    private static BottomDialogFragment bottomDialogFragment;

    // 构造方法
    public static BottomDialogFragment with(int resId) {
        Bundle args = new Bundle();
        args.putInt(KEY_RESID, resId);
        if (bottomDialogFragment == null) {
            synchronized (BottomDialogFragment.class) {
                if (bottomDialogFragment == null) {
                    bottomDialogFragment = new BottomDialogFragment();
                }
            }
        }
        bottomDialogFragment.setArguments(args);
        return bottomDialogFragment;
    }

    /**
     * 获取对象，用于关闭dialog
     *
     * @return
     */
    public static BottomDialogFragment getInstance() {
        return bottomDialogFragment;
    }

    // show的时候调用
    public void showDialog(AppCompatActivity activity) {
        //检查环境是否允许
        if (activity != null && !activity.isFinishing()) {
            show(activity.getSupportFragmentManager(), activity.getClass().getName());
        }
    }

    /**
     * 关闭dialog
     */
    public void dismissDialog() {
        dismiss();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示圆角
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.TransparentBottomSheetDialogTheme);
    }

    // 创建View
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        int resId = args.getInt(KEY_RESID, 0);
        View view = inflater.inflate(resId, container);
        if (callBack != null) {
            callBack.handle(view);
        }
        return view;
    }


    /**
     * 点击事件
     */
    private CallBack callBack;

    public BottomDialogFragment setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public interface CallBack {
        void handle(View view);
    }
}

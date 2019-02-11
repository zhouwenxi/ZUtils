package com.qishui.commontoolslibrary.notice;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * PopupWindow 简单处理
 */
public class PopupUtils {

    private AppCompatActivity activity;
    private View popupView;
    private View targetView;
    private Boolean touchable = true;
    private Boolean clippingEnabled = false;
    private int animationStyle;
    private int width;
    private int height;

    private PopupUtils(AppCompatActivity activity) {
        this.activity = activity;
    }

    public static PopupUtils with(AppCompatActivity activity) {
        return new PopupUtils(activity);
    }

    /**
     * 准备PopupWindow的布局View
     *
     * @param resId
     * @return
     */
    public PopupUtils setContentView(int resId) {
        this.popupView = LayoutInflater.from(activity).inflate(resId, null);
        return this;
    }

    /**
     * 准备PopupWindow的布局View
     *
     * @param view
     * @return
     */
    public PopupUtils setContentView(View view) {
        this.popupView = view;
        return this;
    }

    /**
     * 点击空白区域PopupWindow消失
     *
     * @param touchable
     * @return
     */
    public PopupUtils setOutsideTouchable(Boolean touchable) {
        this.touchable = touchable;
        return this;
    }

    /**
     * 设置PopupWindow动画
     *
     * @param animationStyle
     * @return
     */
    public PopupUtils setAnimationStyle(int animationStyle) {
        this.animationStyle = animationStyle;
        return this;
    }

    /**
     * 设置是否允许PopupWindow的范围超过屏幕范围
     *
     * @param clippingEnabled
     * @return
     */
    public PopupUtils setClippingEnabled(Boolean clippingEnabled) {
        this.clippingEnabled = clippingEnabled;
        return this;
    }

    /**
     * 设置背景灰色程度
     *
     * @param level 0.0f-1.0f
     * @return
     */
    public PopupUtils setBackGroundLevel(float level) {
        Window mWindow = activity.getWindow();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.alpha = level;
        mWindow.setAttributes(params);
        return this;
    }

    /**
     * 显示位置
     *
     * @param targetView
     * @return
     */
    public PopupUtils setTargetView(View targetView) {
        this.targetView = targetView;
        return this;
    }

    /**
     * 设置宽
     *
     * @param width
     * @return
     */
    public PopupUtils setWidth(int width) {
        this.width = width;
        return this;
    }

    /**
     * 设置高
     *
     * @param height
     * @return
     */
    public PopupUtils setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * 显示
     *
     * @param position
     */
    public void show(Position position) {

        PopupWindow window = create();
        int with = window.getContentView().getMeasuredWidth();
        int height = window.getContentView().getMeasuredHeight();
        int offsetX = 0;
        int offsetY = 15;
        int gravity = Gravity.START;
        //判定条件
        if (targetView == null || popupView == null || activity == null || activity.isFinishing()) {
            return;
        }
        //左下 类似QQ、微信左下 居中显示
        if (position == Position.bottom_left) {
            offsetX = -with + targetView.getWidth() / 2;
        }
        //右下
        if (position == Position.bootom_right) {
            offsetX = targetView.getWidth() / 2;
        }
        //下方
        if (position == Position.bottom) {
            offsetX = -Math.abs(targetView.getWidth() - with) / 2;
        }
        //同水平线左边 控件左边类似于微信点赞
        if (position == Position.left) {
            offsetX = -with;
            offsetY = -(height + targetView.getHeight()) / 2;
        }
        //同水平线右边
        if (position == Position.right) {
            offsetY = -(height + targetView.getHeight()) / 2;
            gravity = Gravity.END;
        }
        //上右
        if (position == Position.top_right) {
            offsetX = targetView.getWidth() / 2;
            offsetY = -(height + targetView.getHeight());
        }
        //上方
        if (position == Position.top) {
            offsetX = -Math.abs(targetView.getWidth() - with) / 2;
            offsetY = -(height + targetView.getHeight());
        }
        //上左
        if (position == Position.top_left) {
            offsetX = -(targetView.getWidth() + with) / 2;
            offsetY = -(height + targetView.getHeight());
        }

        PopupWindowCompat.showAsDropDown(window, targetView, offsetX, offsetY, gravity);
    }

    /**
     * 构建一个窗口
     *
     * @return
     */
    public PopupWindow create() {

        if (width == 0) {
            width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        if (height == 0) {
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        /**
         * 初始化一个PopupWindow，width和height都是WRAP_CONTENT
         */
        PopupWindow popupWindow = new PopupWindow(width, height);
        if (popupView != null) {
            // 设置PopupWindow的视图内容
            popupWindow.setContentView(popupView);
        }
        //点击空白区域PopupWindow消失，这里必须先设置setBackgroundDrawable，否则点击无反应
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setTouchable(touchable);
        popupWindow.setFocusable(touchable);

        if (animationStyle != 0) {
            popupWindow.setAnimationStyle(animationStyle);
        }

        popupWindow.setClippingEnabled(clippingEnabled);

        if (callBack != null && popupView != null) {
            callBack.handle(popupWindow, popupView);
        }

        //设置PopupWindow消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //恢复背景
                setBackGroundLevel(1.0f);
                if (dismissCallBack != null) {
                    dismissCallBack.close();
                }
            }
        });
        measureWidthAndHeight(popupView);
        return popupWindow;
    }

    /**
     * 测量View的宽高
     *
     * @param view View
     */
    private void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 处理弹出事件
     */
    private CallBack callBack;

    public PopupUtils setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public interface CallBack {
        void handle(PopupWindow popupWindow, View view);
    }

    /**
     * 处理关闭
     */
    private DismissCallBack dismissCallBack;

    public PopupUtils setDismissCallBack(DismissCallBack dismissCallBack) {
        this.dismissCallBack = dismissCallBack;
        return this;
    }

    public interface DismissCallBack {
        void close();
    }

    /**
     * 控制位置
     */
    public enum Position {
        top,
        bottom,
        left,
        right,
        bottom_left,
        bootom_right,
        top_left,
        top_right
    }
}

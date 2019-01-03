package com.qishui.commontoolslibrary.notice.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.click.QiShuiClick;
import com.qishui.commontoolslibrary.core.LogUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * author： qishui
 * date: 2019/1/3  11:08
 * email: qihsuichixi@163.com
 * qq: 798150439
 * blog: http://zhouwenxi.top
 * desc: 列表
 */
public class ListDialog<T extends QiShuiDialogBean> extends BaseQishuiDialog {

    private TextView dialogTitle;
    private ListView dialogLv;
    private TextView dialogTV;

    private String mTitle;
    private String mTip;
    private List<T> mList = new ArrayList<T>();

    private Activity mActivity;

    private CallBack<T> callback;

    public interface CallBack<T> {
        void disPlay(T t, int position);
    }

    public ListDialog<T> setCallback(CallBack callback) {
        this.callback = callback;
        return this;
    }

    /**
     * 获取实例
     *
     * @param context
     * @return
     */
    public ListDialog(@NonNull Activity context) {
        this(context, 0);
    }

    public ListDialog(@NonNull Activity context, int themeResId) {
        super(context, themeResId);
        this.mActivity = context;
    }

    @Override
    protected int initLayout() {
        return R.layout.dialog_list_style01;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        bindView();
        setText();
        clickOut();
    }

    /**
     * 关闭外部
     */
    private void clickOut() {
        findViewById(R.id.dialog_list_ll).setOnClickListener(new QiShuiClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        }));
    }


    private void bindView() {
        dialogTitle = findViewById(R.id.dialog_list_title);
        dialogLv = findViewById(R.id.dialog_list_lv);
        dialogTV = findViewById(R.id.dialog_list_tv);
    }

    /**
     * 设置文本
     */
    private void setText() {

        getDialogTitle().setText(TextUtils.isEmpty(mTitle) ? "提示" : mTitle);
        getDialogNoDataTipTv().setText(TextUtils.isEmpty(mTip) ? "暂无数据" : mTip);
        if (mList == null || mList.size() == 0) {
            getDialogNoDataTipTv().setVisibility(View.VISIBLE);
        } else {
            getDialogNoDataTipTv().setVisibility(View.GONE);
        }
        getDialogLv().setAdapter(new MyAdapter<T>(mActivity, R.layout.dialog_item_style01, mList) {
            @Override
            protected void onclick(T t, int position) {

                if(callback!=null){
                    callback.disPlay(t,position);
                }
                dismissDialog();
            }
        });
    }

    /**
     * 获取标题控件
     *
     * @return
     */
    public TextView getDialogTitle() {
        return dialogTitle;
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public ListDialog<T> setDialogTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public ListView getDialogLv() {
        return dialogLv;
    }

    /**
     * 设置列表
     *
     * @param list
     * @return
     */
    public ListDialog<T> setDialogLv(List<T> list) {
        this.mList = list;
        return this;
    }

    /**
     * 获取文本提示框
     *
     * @return
     */
    public TextView getDialogNoDataTipTv() {
        return dialogTV;
    }

    /**
     * 设置提示文本
     *
     * @param tip
     * @return
     */
    public ListDialog<T> setDialogNoDataTip(String tip) {
        this.mTip = tip;
        return this;
    }

    /**
     * 适配器
     *
     * @param <T>
     */
    public static abstract class MyAdapter<T> extends BaseAdapter {

        private List<T> list;
        private int layoutID;
        private Context context;

        public MyAdapter(Context context, int layoutID, List<T> list) {
            this.list = list;
            this.layoutID = layoutID;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public T getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(layoutID, null);
                holder = new ViewHolder();
                holder.textView = convertView.findViewById(R.id.dialog_item_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            QiShuiDialogBean bean = (QiShuiDialogBean) getItem(position);
            holder.textView.setText(bean.getShowName());
            holder.textView.setOnClickListener(new QiShuiClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclick(getItem(position), position);
                }
            }));
            return convertView;
        }

        /**
         * 点击事件
         *
         * @param t
         * @param position
         */
        protected abstract void onclick(T t, int position);
    }

    public static class ViewHolder {
        public TextView textView;
    }
}

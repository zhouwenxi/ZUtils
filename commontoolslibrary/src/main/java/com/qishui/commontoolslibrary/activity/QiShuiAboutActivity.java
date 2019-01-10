package com.qishui.commontoolslibrary.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.view.HeadView;
import com.qishui.commontoolslibrary.view.ItemView1;

/**
* author： qishui
* date: 2019/1/10  16:38
* email: qihsuichixi@163.com
* qq: 798150439
* blog: http://zhouwenxi.top
* desc: 关于页面模板
*/
public abstract class QiShuiAboutActivity extends BaseQiShuiActivity {

    private com.qishui.commontoolslibrary.view.HeadView mQishui_about_hv;
    private ImageView mQishui_about_iv;
    private com.qishui.commontoolslibrary.view.ItemView1 mQishui_about_itemview1;
    private com.qishui.commontoolslibrary.view.ItemView1 mQishui_about_itemview2;
    private com.qishui.commontoolslibrary.view.ItemView1 mQishui_about_itemview3;
    private com.qishui.commontoolslibrary.view.ItemView1 mQishui_about_itemview4;
    private com.qishui.commontoolslibrary.view.ItemView1 mQishui_about_itemview5;

    private void bindViews() {

        mQishui_about_hv = (com.qishui.commontoolslibrary.view.HeadView) findViewById(R.id.qishui_about_hv);
        mQishui_about_iv = (ImageView) findViewById(R.id.qishui_about_iv);
        mQishui_about_itemview1 = (com.qishui.commontoolslibrary.view.ItemView1) findViewById(R.id.qishui_about_itemview1);
        mQishui_about_itemview2 = (com.qishui.commontoolslibrary.view.ItemView1) findViewById(R.id.qishui_about_itemview2);
        mQishui_about_itemview3 = (com.qishui.commontoolslibrary.view.ItemView1) findViewById(R.id.qishui_about_itemview3);
        mQishui_about_itemview4 = (com.qishui.commontoolslibrary.view.ItemView1) findViewById(R.id.qishui_about_itemview4);
        mQishui_about_itemview5 = (com.qishui.commontoolslibrary.view.ItemView1) findViewById(R.id.qishui_about_itemview5);
    }



    @Override
    protected int initLayout() {
        return R.layout.activity_qi_shui_about;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {
        bindViews();
        setEvent(savedInstanceState);
    }

    /**
     * 设置方法
     */
    protected abstract void setEvent(Bundle savedInstanceState);

    public HeadView getmQishui_about_hv() {
        return mQishui_about_hv;
    }

    public ImageView getmQishui_about_iv() {
        return mQishui_about_iv;
    }

    public ItemView1 getmQishui_about_itemview1() {
        return mQishui_about_itemview1;
    }

    public ItemView1 getmQishui_about_itemview2() {
        return mQishui_about_itemview2;
    }

    public ItemView1 getmQishui_about_itemview3() {
        return mQishui_about_itemview3;
    }

    public ItemView1 getmQishui_about_itemview4() {
        return mQishui_about_itemview4;
    }

    public ItemView1 getmQishui_about_itemview5() {
        return mQishui_about_itemview5;
    }
}

package com.qishui.zutils.fragment;

import android.view.View;
import android.widget.ListView;

import com.qishui.commontoolslibrary.activity.QiShuiSeeBigPictureActivity;
import com.qishui.commontoolslibrary.adapter.CommonLvAdapter;
import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.base.BaseQiShuiFragment;
import com.qishui.commontoolslibrary.bean.BigPictureBean;
import com.qishui.commontoolslibrary.bean.EventBean;
import com.qishui.commontoolslibrary.core.TakePictureUtils;
import com.qishui.commontoolslibrary.eventbus.EventBusManager;
import com.qishui.commontoolslibrary.notice.dialog.BottomDialogFragment;
import com.qishui.zutils.R;
import com.qishui.zutils.activity.MainActivity;
import com.qishui.zutils.sample.LinkageActivity;
import com.qishui.zutils.sample.TextBannerViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加人: add by qishui
 * 添加时间: 2019/3/14  17:18
 * 添加注释: 内在功能
 */
public class InFragment extends BaseQiShuiFragment {

    @QBindView(R.id.in_lv)
    ListView lv;

    @Override
    protected int initLayout() {
        return R.layout.fragment_in;
    }

    @Override
    protected void initEvent(View view) {

        List<String> list = new ArrayList<>();
        list.add("网络请求");
        list.add("banner处理");
        list.add("文字滚动处理");
        list.add("页面state处理");
        list.add("缓存处理");
        list.add("权限处理");
        list.add("图片放大处理");
        list.add("三级联动");
        list.add("照片选取|拍照");


        lv.setAdapter(new CommonLvAdapter<String>(getActivity(), list, R.layout.item_single_text) {
            @Override
            public void setData(BaseHolder holder, String item, final int position) {

                holder.setText(R.id.tv, item);

                holder.getView(R.id.ll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handle(position);
                    }
                });


            }
        });
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/20  16:47
     * 添加注释: 设置跳转
     */
    private void handle(int position) {

        switch (position) {
            case 2:
                startActivity(TextBannerViewActivity.class);
                break;
            case 6:
                postSticky(new EventBean(EventBusManager.KEY_BIG_PICTURE, new BigPictureBean("郭霖book", "http://guolin.tech/book.png")));
                startActivity(QiShuiSeeBigPictureActivity.class);
                break;
            case 7:
                startActivity(LinkageActivity.class);
                break;
            case 8:
                selectPicture();
                break;
            default:
                toast("wait to handle ...");
                break;
        }

    }

    /***
     * 选取照片
     */
    private void selectPicture() {

        final MainActivity mainActivity = (MainActivity) getActivity();

        BottomDialogFragment.with(R.layout.dialog_frgment_select_pic)
                .setCallBack(new BottomDialogFragment.CallBack() {
                    @Override
                    public void handle(View view) {

                        view.findViewById(R.id.take_pic).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                BottomDialogFragment.getInstance().dismissDialog();

                                TakePictureUtils.with(mainActivity).takePicture(new TakePictureUtils.TakePictureCallback() {
                                    @Override
                                    public void show(String path) {
                                        toast("拍照:" + path);
                                    }
                                });


                            }
                        });

                        view.findViewById(R.id.select_pic).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                BottomDialogFragment.getInstance().dismissDialog();

                                TakePictureUtils.with(mainActivity).selectPicture(new TakePictureUtils.SelectPictureCallBack() {
                                    @Override
                                    public void show(String path) {
                                        toast("选取相册:" + path);
                                    }
                                });

                            }
                        });

                        view.findViewById(R.id.select_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                BottomDialogFragment.getInstance().dismissDialog();
                                toast("取消");

                            }
                        });
                    }
                }).showDialog(mainActivity);

    }
}

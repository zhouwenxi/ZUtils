package com.qishui.zutils.fragment;

import android.Manifest;
import android.view.View;
import android.widget.ListView;

import com.qishui.commontoolslibrary.activity.QiShuiSeeBigPictureActivity;
import com.qishui.commontoolslibrary.adapter.CommonLvAdapter;
import com.qishui.commontoolslibrary.annotation.QBindView;
import com.qishui.commontoolslibrary.base.BaseQiShuiFragment;
import com.qishui.commontoolslibrary.bean.BigPictureBean;
import com.qishui.commontoolslibrary.bean.EventBean;
import com.qishui.commontoolslibrary.core.PermissionUtils;
import com.qishui.commontoolslibrary.core.TakePictureUtils;
import com.qishui.commontoolslibrary.core.UiUtils;
import com.qishui.commontoolslibrary.eventbus.EventBusManager;
import com.qishui.commontoolslibrary.notice.DIYToastUtils;
import com.qishui.commontoolslibrary.notice.dialog.BottomDialogFragment;
import com.qishui.commontoolslibrary.update.UpdateCheckUtils;
import com.qishui.commontoolslibrary.view.HeadView;
import com.qishui.zutils.R;
import com.qishui.zutils.activity.MainActivity;
import com.qishui.zutils.bean.FunctionBean;
import com.qishui.zutils.sample.FloatRvActivity;
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
    @QBindView(R.id.home_hv)
    HeadView home_hv;

    @Override
    protected int initLayout() {
        return R.layout.fragment_in;
    }

    @Override
    protected void initEvent(View view) {

        setTitle();

        showList();
    }

    private void showList() {
        List<FunctionBean> list = new ArrayList<FunctionBean>();
        list.add(new FunctionBean("网络请求", 1));
        list.add(new FunctionBean("文字滚动处理", 3));
        list.add(new FunctionBean("页面state处理", 4));
        list.add(new FunctionBean("缓存处理", 5));
        list.add(new FunctionBean("权限处理", 6));
        list.add(new FunctionBean("图片放大处理", 7));
        list.add(new FunctionBean("三级联动", 8));
        list.add(new FunctionBean("照片选取|拍照", 9));
        list.add(new FunctionBean("自定义toast", 10));
        list.add(new FunctionBean("悬浮置顶", 11));
        list.add(new FunctionBean("升级", 12));


        lv.setAdapter(new CommonLvAdapter<FunctionBean>(getActivity(), list, R.layout.item_single_text) {
            @Override
            public void setData(BaseHolder holder, final FunctionBean item, final int position) {

                holder.setText(R.id.tv, item.getName());

                holder.getView(R.id.ll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handle(item.getId());
                    }
                });


            }
        });
    }

    private void setTitle() {
        home_hv.getLeftTv().setVisibility(View.GONE);
        home_hv.getLeftIv().setVisibility(View.GONE);
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/20  16:47
     * 添加注释: 设置跳转
     */
    private void handle(int position) {

        switch (position) {
            case 3:
                startActivity(TextBannerViewActivity.class);
                break;
            case 6:
                permission();
                break;
            case 7:
                postSticky(new EventBean(EventBusManager.KEY_BIG_PICTURE, new BigPictureBean("郭霖book", "http://guolin.tech/book.png")));
                startActivity(QiShuiSeeBigPictureActivity.class);
                break;
            case 8:
                startActivity(LinkageActivity.class);
                break;
            case 9:
                selectPicture();
            case 10:
                DIYToastUtils.with(getActivity()).setText("HELLO").show();
                break;
            case 11:
                startActivity(FloatRvActivity.class);
                break;
            case 12:
                updateApp();
                break;
            default:
                // toast("wait to handle ...");
                DIYToastUtils toastUtils = DIYToastUtils.with(getActivity());
                toastUtils.getTV().setTextColor(UiUtils.getColor(R.color.colorRedaa));
                toastUtils.getLL().setBackgroundColor(UiUtils.getColor(R.color.colorMain));
                toastUtils.setText("wait to handle ...").show();
                break;
        }

    }

    /**
     * 权限处理
     */
    private void permission() {

        String[] permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (!PermissionUtils.hasAllPermission(getActivity(), permission)) {
            PermissionUtils.with(this)
                    .addPermissions(PermissionUtils.GROUP_STORAGE)
                    .addPermissions(PermissionUtils.GROUP_CAMERA)
                    .request();
        }

    }

    /**
     * 升级app
     */
    private void updateApp() {

        UpdateCheckUtils.with(this)
                .setUrl("http://118.24.148.250:8080/yk/update_signed.apk")
                .setMessage("1、修复未知bug")
                .setVersionName("2.0.0")
                .setMode(UpdateCheckUtils.VERSONNAME)
                .check();

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


    @Override
    public void onDestroy() {
        super.onDestroy();
        DIYToastUtils.reset();
    }
}

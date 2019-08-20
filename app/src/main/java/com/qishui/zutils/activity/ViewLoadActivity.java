package com.qishui.zutils.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qishui.commontoolslibrary.banner.BannerView;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.core.UiUtils;
import com.qishui.commontoolslibrary.image.GlideUtils;
import com.qishui.zutils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 加载view 处理事件
 */
public class ViewLoadActivity extends BaseQiShuiActivity {

    public static final String KEY_LAYOUT = "KEY_LAYOUT";
    public static final String KEY_LAYOUT_ID = "KEY_LAYOUT_ID";
    private int curId;

    @Override
    protected int initLayout() {
        curId = getIntent().getIntExtra(KEY_LAYOUT_ID, 0);
        return getIntent().getIntExtra(KEY_LAYOUT, R.layout.activity_view_load);
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        switch (curId) {

            case 0:
                break;
            case 3:
                banner1();
                banner2();
                break;

            default:
                break;
        }

    }


    /**
     * 轮播
     */
    private void banner1() {

        List<Integer> mList = new ArrayList<>();
        mList.add(R.layout.banner_view1);
        mList.add(R.layout.banner_view2);

        BannerView bannerView = findViewById(R.id.banner);
        bannerView.setUnSelectId(R.drawable.white_point)
                .setSelectId(R.drawable.red_point)
                .setLocationCenter()
                .setListResIds(mList)
                .setAutoPlay(false)
                .setImageLoader(new BannerView.ImageLoader() {
                    @Override
                    public void show(Context context, Object obj, ImageView iv) {
                        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        GlideUtils.display(ViewLoadActivity.this, obj, iv);
                    }
                }).setBannerClick(new BannerView.BannerCallBack() {
            @Override
            public void click(View view, final int position) {

                if (position == 0) {
                    TextView tv1 = findViewById(R.id.banner_menu_tv1);
                    tv1.setTextColor(UiUtils.getColor(R.color.colorBlack));
                    tv1.setTextSize(16);
                    tv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            toast("点击:" + position);
                        }
                    });

                }
                if (position == 1) {

                    TextView tv2 = findViewById(R.id.banner_menu_tv2);
                    tv2.setTextColor(UiUtils.getColor(R.color.colorMain));
                    tv2.setTextSize(16);
                    tv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            toast("点击:" + position);
                        }
                    });

                }


            }
        }).showView();
    }


    /**
     * 轮播二
     */
    private void banner2() {

        List<Object> mList = new ArrayList<>();
        mList.add("http://img.zuofan.cn/thumb/s/2018/5/1527487544832675,c_fill,h_180,w_240.jpg");
        //mList.add("https://maint.duofriend.com/upload/M00/02/56/CgIPQ11SHWeAcYh-AAdfQwLgeTY598.jpg");
        // mList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2121206715,2955288754&fm=26&gp=0.jpg");

        BannerView bannerView = findViewById(R.id.banner2);
        bannerView.setUnSelectId(R.drawable.white_point)
                .setSelectId(R.drawable.red_point)
                .setLocationCenter()
                .setListViews(mList)
                .setAutoPlay(false)
                .setImageLoader(new BannerView.ImageLoader() {
                    @Override
                    public void show(Context context, Object obj, ImageView iv) {
                        GlideUtils.display(ViewLoadActivity.this, obj, iv);
                    }
                }).showView();

    }

}

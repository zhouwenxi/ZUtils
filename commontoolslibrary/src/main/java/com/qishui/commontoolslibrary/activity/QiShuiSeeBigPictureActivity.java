package com.qishui.commontoolslibrary.activity;

import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.bean.BigPictureBean;
import com.qishui.commontoolslibrary.bean.EventBean;
import com.qishui.commontoolslibrary.eventbus.EventBusManager;
import com.qishui.commontoolslibrary.image.GlideUtils;
import com.qishui.commontoolslibrary.view.HeadView;

/**
 * 查看大图
 */
public class QiShuiSeeBigPictureActivity extends BaseQiShuiActivity {

    private PhotoView photoView;
    private HeadView headView;

    @Override
    protected int initLayout() {
        return R.layout.activity_qi_shui_see_big_picture;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        photoView = findViewById(R.id.big_pv);
        headView=findViewById(R.id.hv);

    }

    @Override
    public void uiEvent(EventBean eventBean) {

        if(eventBean.getType()== EventBusManager.KEY_BIG_PICTURE){
            BigPictureBean bean= (BigPictureBean) eventBean.getObject();
            headView.getMiddleTv().setText(bean.getTitle());
            GlideUtils.display(this,bean.getPath(),photoView);
        }



    }
}

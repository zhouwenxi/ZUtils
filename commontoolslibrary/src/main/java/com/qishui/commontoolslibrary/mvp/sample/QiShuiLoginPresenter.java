package com.qishui.commontoolslibrary.mvp.sample;

import android.os.Handler;
import android.text.TextUtils;

import com.qishui.commontoolslibrary.http.callback.StringCallBack;
import com.qishui.commontoolslibrary.mvp.MvpBasePresenter;

import java.util.HashMap;


public class QiShuiLoginPresenter extends MvpBasePresenter<QiShuiMVPContract.ILoginView,QiShuiLoginModel> implements QiShuiMVPContract.IPresenter{

    public QiShuiLoginPresenter(QiShuiLoginModel qiShuiLoginModel) {
        super(qiShuiLoginModel);
    }

    @Override
    public void login(final String userName, final String password) {

        if(isAttachView()){
            if(TextUtils.isEmpty(userName)){
                getView().show("用户名不能为空!");
                return;
            }
            if(TextUtils.isEmpty(password)){
                getView().show("密码不能为空!");
                return;
            }
            getView().loadingDialog("正在登录中...");
        }

        String url="https://www.baidu.com";
        HashMap<String, Object> map=new HashMap<>();
        map.put("username",userName);
        map.put("password",password);
        getModel().requestLogin(url, map, new StringCallBack() {
            @Override
            protected void onEasySuccess(String result) {

                //提示登录成功，及发生跳转
                if(isAttachView()){
                    getView().show(userName+"_"+result);
                    getView().nextPage();
                }

            }

            @Override
            protected void onEasyError(String message) {

                //提示错误信息
                if(isAttachView()){
                    getView().show(userName+"_"+message);
                }

            }

            @Override
            public void onLast() {
                //关闭进度条
                if(isAttachView()){
                    getView().dissmissDialog();
                }
            }
        });


    }
}

package com.qishui.commontoolslibrary.mvp.sample;

import com.qishui.commontoolslibrary.http.callback.ICallBack;
import com.qishui.commontoolslibrary.mvp.MvpBaseModel;
import com.qishui.commontoolslibrary.mvp.MvpBaseView;

import java.util.HashMap;

/**
 * MVP 所有要实现的UI及数据加载及逻辑约束控制类
 */
public class QiShuiMVPContract {

    interface ILoginView extends MvpBaseView{

        /**
         * 提示信息
         * @param message
         */
        void show(String message);

        /**
         * 设置加载进度条
         * @param text
         */
        void loadingDialog(String text);

        /**
         * 关闭进度条
         */
        void dissmissDialog();

        /**
         * 页面跳转
         */
        void nextPage();
    }

    // m p 一般是相对应的，排除同一个请求使用不同参数

    interface ILoginModel extends MvpBaseModel {

        /**
         * 进行联网操作 登录
         * @param url
         * @param map
         * @param callBack
         */
        void requestLogin(String url, HashMap<String,Object> map, ICallBack callBack);
    }

    interface IPresenter {

        /**
         * 请求登录
         * @param userName
         * @param password
         */
        void login(String userName,String password);

    }

}

package com.qishui.commontoolslibrary.state;

/**
* author： qishui
* date: 2019/1/7  15:16
* email: qihsuichixi@163.com
* qq: 798150439
* blog: http://zhouwenxi.top
* desc: 布局管理器接口管理
*/
public interface StateInterface {

    /**
     * 显示加载中页面
     */
    void showLoading();

    /**
     * 显示网络出错页面
     */
    void showNetworkError();

    /**
     * 显示数据加载出错
     */
    void showError();

    /**
     * 显示数据加载为空
     */
    void showEmpty();

    /**
     * 加载原始页面
     */
    void showContent();

}
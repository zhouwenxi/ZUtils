package com.qishui.commontoolslibrary.update;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.qishui.commontoolslibrary.core.AppUtils;
import com.qishui.commontoolslibrary.core.FileUtils;
import com.qishui.commontoolslibrary.core.LogUtils;
import com.qishui.commontoolslibrary.http.HttpManager;
import com.qishui.commontoolslibrary.http.callback.FileCallBack;
import com.qishui.commontoolslibrary.notice.ToastUtils;
import com.qishui.commontoolslibrary.notice.dialog.CommonDialog;

import java.io.File;

/**
 * 版本更新工具
 */
public class UpdateCheckUtils {

    //本地版本名
    public static String localVersonName = AppUtils.getAppVirsionName();
    //本地版本号
    public static int localVersonCode = AppUtils.getAppVirsionCode();
    //设置升级方式
    public static int VERSONNAME = 0;
    public static int VERSONCODE = 1;
    //默认
    private int mode = VERSONCODE;

    private AppCompatActivity mActivity;
    private String versionName;
    private int versionCode;
    private String url;
    private String message;

    //是否正在下載
    private static Boolean isDownLoad = false;

    /**
     * 获取对象
     *
     * @param activity
     * @return
     */
    public static UpdateCheckUtils with(AppCompatActivity activity) {
        return new UpdateCheckUtils(activity);
    }

    public static UpdateCheckUtils with(Fragment fragment) {
        return new UpdateCheckUtils((AppCompatActivity) fragment.getActivity());
    }

    private UpdateCheckUtils(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    public UpdateCheckUtils setMode(int mode) {
        this.mode = mode;
        return this;
    }

    public UpdateCheckUtils setVersionName(String versionName) {
        this.versionName = versionName;
        return this;
    }

    public UpdateCheckUtils setVersionCode(int versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    public UpdateCheckUtils setUrl(String url) {
        this.url = url;
        return this;
    }

    public UpdateCheckUtils setMessage(String message) {
        this.message = message;
        return this;
    }


    /**
     * 检查升级
     */
    public void check() {

        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (isDownLoad) {
            ToastUtils.show("正在下载...");
            return;
        }
        if (mode == VERSONNAME) {
            if (!TextUtils.equals(localVersonName, versionName)) {
                showDownDialog();
            }
        } else if (mode == VERSONCODE) {
            if (versionCode > localVersonCode) {
                showDownDialog();
            }
        }


    }


    /**
     * 显示下载提示框
     */
    private void showDownDialog() {
        if (TextUtils.isEmpty(message)) {
            message = "请升级到最新版本!";
        }
        CommonDialog.with(mActivity, CommonDialog.STYLE_IOS)
                .setDialogTitle("版本更新")
                .setDialogContent(message)
                .setDialogLeftText("不了")
                .setDialogRightText("好的")
                .setCallBack(new CommonDialog.CallBack() {
                    @Override
                    public void left() {
                        ToastUtils.show("取消升级...");
                    }

                    @Override
                    public void right() {

                        InstallUtil.checkPackageInstallsPermission(mActivity, new InstallUtil.CallBack() {
                            @Override
                            public void success() {
                                downApk();
                            }

                            @Override
                            public void fail() {
                                ToastUtils.show("取消授权...");
                            }
                        });

                    }
                }).showDialog();

    }

    /**
     * 开始下载
     */
    private void downApk() {

        HttpManager.with().getProxy().downloadFile(url, FileUtils.KEY_FILE_DOWNLOAD, "test.apk", new FileCallBack() {
            @Override
            protected void onEasyInProgress(float progress) {
                isDownLoad=true;
                LogUtils.e("下載进度：" + progress);
            }

            @Override
            protected void onEasySuccess(File file) {
                ToastUtils.show("下载成功!");
                isDownLoad=false;
                InstallUtil.install(mActivity, file.getAbsolutePath());
            }

            @Override
            protected void onEasyError(String message) {
                ToastUtils.show("下载失败..." + message);
                isDownLoad=false;
                LogUtils.e(message + "  " + url);
            }
        });
    }

}

package com.qishui.commontoolslibrary.update;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.core.AppUtils;
import com.qishui.commontoolslibrary.core.FileUtils;
import com.qishui.commontoolslibrary.core.LogUtils;
import com.qishui.commontoolslibrary.http.HttpManager;
import com.qishui.commontoolslibrary.http.callback.FileCallBack;
import com.qishui.commontoolslibrary.notice.ToastUtils;
import com.qishui.commontoolslibrary.notice.dialog.CommonDialog;
import com.qishui.commontoolslibrary.notice.dialog.LoadingDialog;

import java.io.File;

/**
 * 版本更新工具
 */
public class UpdateCheckUtils {

    //本地版本名
    public static String localVersonName = AppUtils.getAppVersionName();
    //本地版本号
    public static int localVersonCode = AppUtils.getAppVersionCode();
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
        if (mode == VERSONNAME) {
            if (!TextUtils.equals(localVersonName, versionName)) {
                showDownDialog();
            } else {
                ToastUtils.showToastOnUiThread(R.string.last_version);
            }
        } else if (mode == VERSONCODE) {
            if (versionCode > localVersonCode) {
                showDownDialog();
            } else {
                ToastUtils.showToastOnUiThread(R.string.last_version);
            }
        }


    }


    /**
     * 显示下载提示框
     */
    private void showDownDialog() {

        CommonDialog.with(mActivity, CommonDialog.STYLE_ANDROID)
                .setDialogTitle("版本更新")
                .setDialogContent(TextUtils.isEmpty(message) ? "请升级到最新版本" : message)
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

        //加载进度框
        final LoadingDialog loadingDialog = new LoadingDialog(mActivity);
        loadingDialog.showDialog();

        HttpManager.with().getProxy().downloadFile(url, FileUtils.KEY_FILE_DOWNLOAD, "test.apk", new FileCallBack() {
            @Override
            protected void onEasyInProgress(float progress) {

                loadingDialog.setText("下载中 " + progress + "%");
            }

            @Override
            protected void onEasySuccess(File file) {
                loadingDialog.dismissDialog();
                ToastUtils.show("下载成功!");
                InstallUtil.install(mActivity, file.getAbsolutePath());
            }

            @Override
            protected void onEasyError(String message) {
                loadingDialog.dismissDialog();
                ToastUtils.show("下载失败..." + message);
            }
        });
    }

}

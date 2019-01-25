package com.qishui.commontoolslibrary.update;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.qishui.commontoolslibrary.core.ActivtyResultUtils;
import com.qishui.commontoolslibrary.core.FileProvider7;
import com.qishui.commontoolslibrary.notice.dialog.CommonDialog;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/*
 * Code is far away from bug with the animal protecting
 * <p>
 * 创建作者: qishui
 * 联系邮箱: qishuichixi@126.com
 * 创建时间: 2018/7/2 10:59
 * 创建描述:
 */

public class InstallUtil {

    //
    public static int UNKNOWN_CODE = 0x2018;

    /**
     * @param activity
     * @param filePath 下载下来后文件的路径
     */
    public static void install(final AppCompatActivity activity, final String filePath) {

        checkPackageInstallsPermission(activity, new CallBack() {
            @Override
            public void success() {
                startInstall(activity, filePath);
            }

            @Override
            public void fail() {

            }
        });
    }

    /**
     * android7及以下
     */
    private static void startInstall(AppCompatActivity activity, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        FileProvider7.setIntentDataAndType(activity, intent, "application/vnd.android.package-archive", new File(filePath), true);
        activity.startActivity(intent);
        activity.finish();
    }


    public interface CallBack {
        void success();

        void fail();
    }

    /**
     * 检查是否具有安装权限
     *
     * @param activity
     * @param callBack
     */
    public static void checkPackageInstallsPermission(final AppCompatActivity activity, final CallBack callBack) {
        if (callBack == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            boolean isGranted = activity.getPackageManager().canRequestPackageInstalls();
            if (isGranted) {
                callBack.success();
            } else {
                CommonDialog.with(activity, CommonDialog.STYLE_IOS)
                        .setDialogTitle("请求权限")
                        .setDialogContent("安装应用需要打开未知来源权限，请去设置中开启权限?")
                        .setDialogLeftText("不了")
                        .setDialogRightText("好的")
                        .setCallBack(new CommonDialog.CallBack() {
                            @Override
                            public void left() {
                                callBack.fail();
                            }

                            @Override
                            public void right() {

                                //设置包名，可直接跳转当前软件的设置页面
                                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + activity.getPackageName()));
                                ActivtyResultUtils.with(activity).startForResult(intent, UNKNOWN_CODE, new ActivtyResultUtils.Callback() {
                                    @Override
                                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                                        if (resultCode == RESULT_OK) {
                                            callBack.success();
                                        } else {
                                            callBack.fail();
                                        }
                                    }
                                });
                            }
                        }).showDialog();
            }
        } else {
            callBack.success();
        }
    }
}

package com.qishui.commontoolslibrary.core;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhou on 2018/12/23.
 */

public class PermissionUtils {

    private static final String TAG = "PERMISSIONUTILS";
    private static PermissionFragment permissionFragment;
    //权限 与xml 对应一起
    public static final String[] GROUP_CALENDAR = {Manifest.permission.READ_CALENDAR, permission.WRITE_CALENDAR};
    public static final String[] GROUP_CAMERA = {permission.CAMERA};
    public static final String[] GROUP_CONTACTS = {permission.READ_CONTACTS, permission.WRITE_CONTACTS, permission.GET_ACCOUNTS};
    public static final String[] GROUP_LOCATION = {permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION};
    public static final String[] GROUP_MICROPHONE = {permission.RECORD_AUDIO};
    public static final String[] GROUP_PHONE = {permission.READ_PHONE_STATE, permission.READ_PHONE_NUMBERS, permission.CALL_PHONE, permission.READ_CALL_LOG, permission.WRITE_CALL_LOG, permission.ADD_VOICEMAIL, permission.USE_SIP, permission.PROCESS_OUTGOING_CALLS, permission.ANSWER_PHONE_CALLS};
    public static final String[] GROUP_PHONE_BELOW_O = Arrays.copyOf(GROUP_PHONE, GROUP_PHONE.length - 1);
    public static final String[] GROUP_SENSORS = {permission.BODY_SENSORS};
    public static final String[] GROUP_SMS = {permission.SEND_SMS, permission.RECEIVE_SMS, permission.READ_SMS, permission.RECEIVE_WAP_PUSH, permission.RECEIVE_MMS,};
    public static final String[] GROUP_STORAGE = {permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE};

    /**
     * fragment获取权限
     *
     * @param fragment
     */
    private PermissionUtils(Fragment fragment) {
        this(fragment.getActivity());
    }

    /**
     * activity获取权限
     *
     * @param activity
     */
    private PermissionUtils(FragmentActivity activity) {
        permissionFragment = getPermissionFragment(activity.getSupportFragmentManager());
    }

    /**
     * 构建对象
     *
     * @param activity
     * @return
     */
    public static PermissionUtils with(FragmentActivity activity) {
        return new PermissionUtils(activity);
    }

    /**
     * 构建对象
     *
     * @param fragment
     * @return
     */
    public static PermissionUtils with(Fragment fragment) {
        return new PermissionUtils(fragment);
    }

    /**
     * 添加请求权限
     *
     * @param permission
     * @return
     */
    public PermissionUtils addPermission(String permission) {
        if (runVersion()) {
            permissionFragment.addPermission(permission);
        }
        return this;
    }

    /**
     * 添加多个请求权限
     *
     * @param need
     * @return
     */
    public PermissionUtils addPermissions(String... need) {
        if (runVersion()) {
            permissionFragment.addPermissions(need);
        }
        return this;
    }

    /**
     * 去请求
     *
     * @return
     */
    public PermissionUtils request() {
        if (runVersion()) {
            permissionFragment.request();
        }
        return this;
    }

    /**
     * 是否需要
     *
     * @return
     */
    public static Boolean runVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    /**
     * 权限拒绝
     *
     * @param callback
     * @return
     */
    public PermissionUtils setCallback(Callback callback) {
        permissionFragment.setCallback(callback);
        return this;
    }

    /**
     * 获取权限列表
     *
     * @return
     */
    public static List<String> getPermission() {
        return permissionFragment.getPermission();
    }

    /**
     * 回调方法
     */
    public interface Callback {
        //拒绝
        void refuse();
    }


    /**
     * 获取fragment
     *
     * @param fragmentManager
     * @return
     */
    private PermissionFragment getPermissionFragment(@NonNull final FragmentManager fragmentManager) {
        PermissionFragment permissionsFragment = (PermissionFragment) fragmentManager.findFragmentByTag(TAG);
        boolean isNewInstance = permissionsFragment == null;
        if (isNewInstance) {
            permissionsFragment = new PermissionFragment();
            fragmentManager.beginTransaction().add(permissionsFragment, TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return permissionsFragment;
    }

    /**
     * 设置点击权限取消操作
     */
    public interface SetCallback {
        //拒绝
        void onclikCancle();
    }

    /**
     * 设置页面-->设置权限
     *
     * @param activity
     */
    public static void goSetInfo(final FragmentActivity activity, final List<String> permissions, final SetCallback setCallback) {

        String title = "赋予权限";
        String message = "由于您设置了不再询问请求权限，这会影响app运行,需要在应用信息详情页面赋予相关权限。";
        String neg = "取消";
        String set = "去设置";
        final int requestCode = 10086;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title).setMessage(message)
                .setNegativeButton(neg,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (setCallback != null) {
                                    setCallback.onclikCancle();
                                }
                            }
                        })
                .setPositiveButton(set,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                                ActivtyResultUtils.with(activity).startForResult(intent, requestCode, new ActivtyResultUtils.Callback() {
                                    @Override
                                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                                        //判断是否需要再次请求权限处理
                                        if (!hasAllPermission(activity, permissions)) {
                                            goSetInfo(activity, permissions, setCallback);
                                        }
                                    }
                                });
                            }
                        })
                .setCancelable(false)
                .show();
    }


    /**
     * 是否存在权限
     *
     * @param activity
     * @param pers
     * @return
     */
    public static Boolean hasAllPermission(Activity activity, String... pers) {

        if (pers == null) {
            return true;
        }

        if (runVersion()) {
            for (int i = 0; i < pers.length; i++) {
                //存在拒绝的
                if (activity.getApplicationContext().checkSelfPermission(pers[i]) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * 是否存在权限
     *
     * @param activity
     * @param pers
     * @return
     */
    public static Boolean hasAllPermission(Activity activity, List<String> pers) {

        if (pers == null) {
            return true;
        }

        if (runVersion()) {
            for (int i = 0; i < pers.size(); i++) {
                //存在拒绝的
                if (activity.getApplicationContext().checkSelfPermission(pers.get(i)) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }


    }

    /**
     * 权限请求
     */
    public static class PermissionFragment extends Fragment {

        private static final int PERMISSIONS_REQUEST_CODE = 0x123;
        private PermissionUtils.Callback mCallback;
        private List<String> permissionList = new ArrayList<>();

        public PermissionFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //屏幕旋转，不需要重建fragment
            setRetainInstance(true);
        }

        /**
         * 添加权限
         *
         * @param permission
         */
        public void addPermission(String permission) {
            if (!permissionList.contains(permission)) {
                permissionList.add(permission);
            }
        }

        /**
         * 一次添加多个权限
         *
         * @param permissions
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void addPermissions(String... permissions) {
            for (String permisson : permissions) {
                addPermission(permisson);
            }
        }

        /**
         * 获取权限
         *
         * @return
         */
        public List<String> getPermission() {
            return permissionList;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void request() {
            String[] mPermissions = new String[permissionList.size()];
            for (int i = 0; i < permissionList.size(); i++) {
                mPermissions[i] = permissionList.get(i);
            }
            super.requestPermissions(mPermissions, PERMISSIONS_REQUEST_CODE);
        }


        public void setCallback(PermissionUtils.Callback mCallback) {
            this.mCallback = mCallback;
        }

        @Override
        @TargetApi(Build.VERSION_CODES.M)
        public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode != PERMISSIONS_REQUEST_CODE) {
                return;
            }
            int size = permissions.length;
            for (int i = 0; i < size; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (mCallback != null) {
                        mCallback.refuse();
                    }
                    return;
                }
            }
        }

    }
}

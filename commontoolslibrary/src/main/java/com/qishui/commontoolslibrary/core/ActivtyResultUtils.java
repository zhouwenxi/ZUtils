package com.qishui.commontoolslibrary.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * author： qishui
 * date: 2018/12/25  11:13
 * email: qihsuichixi@163.com
 * qq: 798150439
 * blog: http://zhouwenxi.top
 * desc:  activity startForResult 工具类
 */
public class ActivtyResultUtils {

    private static final String TAG = "ACTIVTYRESULTUTILS";
    private ResultBackFragment mResultBackFragment;

    public ActivtyResultUtils(Fragment fragment) {
        this(fragment.getActivity());
    }

    public ActivtyResultUtils(FragmentActivity activity) {
        mResultBackFragment = getFragment(activity.getSupportFragmentManager());
    }

    /**
     * 获取fragment
     *
     * @param fragmentManager
     * @return
     */
    private ResultBackFragment getFragment(@NonNull final FragmentManager fragmentManager) {
        ResultBackFragment fragment = (ResultBackFragment) fragmentManager.findFragmentByTag(TAG);
        boolean isNewInstance = fragment == null;
        if (isNewInstance) {
            fragment = new ResultBackFragment();
            fragmentManager.beginTransaction().add(fragment, TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    public void startForResult(Class<?> clazz, int requestCode, Callback callback) {
        Intent intent = new Intent(mResultBackFragment.getActivity(), clazz);
        startForResult(intent, requestCode, callback);
    }

    public void startForResult(Intent intent, int requestCode, Callback callback) {
        mResultBackFragment.startForResult(intent, requestCode, callback);
    }

    public interface Callback {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    /**
     * 辅助
     */
    public static class ResultBackFragment extends Fragment {

        private Callback mCallback;
        private int mRequestCode;

        public ResultBackFragment() {
        }

        public void startForResult(Intent intent, int requestCode, ActivtyResultUtils.Callback callback) {
            mCallback = callback;
            mRequestCode = requestCode;
            startActivityForResult(intent, requestCode);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            //callback方式的处理
            if (mCallback != null && mRequestCode == requestCode) {
                mCallback.onActivityResult(requestCode, resultCode, data);
            }
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }
    }
}

package com.qishui.commontoolslibrary.core;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by zhou on 2018/12/23.
 */

public class FragmentUtils {

    /**
     * 替换加载fragment
     *
     * @param fragment
     * @param fraglayoutId
     */
    public static void replaceFragment(Fragment fragment, int fraglayoutId) {
        if (fragment == null) {
            return;
        }
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if (fragmentManager == null) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fraglayoutId, fragment);
        transaction.commitAllowingStateLoss();
    }

}

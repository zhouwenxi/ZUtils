package com.qishui.commontoolslibrary.core;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by zhou on 2018/12/23.
 */

public class NetworkUtils {

    /**
     * 未知网络
     */
    public static final int TYPE_UNKNOW = -1;
    /**
     * WIFI网络
     */
    public static final int TYPE_WIFI = 1;
    /**
     * 蜂窝网络
     */
    public static final int TYPE_MOBILE = 2;
    /**
     * 2G蜂窝网络
     */
    public static final int TYPE_MOBILE_2G = 3;
    /**
     * 3G蜂窝网络
     */
    public static final int TYPE_MOBILE_3G = 4;
    /**
     * 4G蜂窝网络
     */
    public static final int TYPE_MOBILE_4G = 5;

    /**
     * 打开网络设置界面
     *
     * @param context 上下文
     */
    public static void openWirelessSettings(Context context) {
        context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }


    /**
     * 网络是否可用
     * @return
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) BaseQiShuiApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            return ni.isAvailable();
        }
        return false;
    }

    /**
     * WIFI网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) BaseQiShuiApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (ni != null) {
            return ni.isAvailable();
        }
        return false;
    }

    /**
     * 获取网络类型(WIFI或者移动网络)
     *
     * @param context
     * @return
     */
    public static int getNetworkType(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
            return TYPE_UNKNOW;
        }
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isAvailable()) {
            if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            } else if (ni.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_UNKNOW;
    }

    /**
     * 获取精确网络类型
     * @return
     */
    public static int getExactNetworkType() {
        ConnectivityManager cm = (ConnectivityManager) BaseQiShuiApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
            return TYPE_UNKNOW;
        }
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isAvailable()) {
            if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            } else if (ni.getType() == ConnectivityManager.TYPE_MOBILE) {
                int subType = TYPE_MOBILE;
                switch (ni.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS: //联通2g
                    case TelephonyManager.NETWORK_TYPE_CDMA: //电信2g
                    case TelephonyManager.NETWORK_TYPE_EDGE: //移动2g
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        subType = TYPE_MOBILE_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3g
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        subType = TYPE_MOBILE_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        subType = TYPE_MOBILE_4G;
                        break;
                    default:
                        break;
                }
                return subType;
            }
        }
        return TYPE_UNKNOW;
    }


    /**
     * 获取手机mac
     *
     * @return
     */
    public static String getMacAddress() {
        String address = "02:00:00:00:00:00";
        try {
            // 把当前机器上的访问网络接口的存入 Enumeration集合中
            Enumeration<NetworkInterface> interfaces = null;
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netWork = interfaces.nextElement();
                // 如果存在硬件地址并可以使用给定的当前权限访问，则返回该硬件地址（通常是 MAC）。
                byte[] by = netWork.getHardwareAddress();
                if (by == null || by.length == 0) {
                    continue;
                }
                StringBuilder builder = new StringBuilder();
                for (byte b : by) {
                    builder.append(String.format("%02X:", b));
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                String mac = builder.toString();
                // 从路由器上在线设备的MAC地址列表，可以印证设备Wifi的 name 是 wlan0
                if ("wlan0".equals(netWork.getName())) {
                    address = mac;
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return address;
    }

}
